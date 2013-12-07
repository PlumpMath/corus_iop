package org.sapia.corus.interop.helpers;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.sapia.corus.interop.InteropCodec;
import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.AckMessageCommand;
import org.sapia.corus.interop.api.message.ConfirmShutdownMessageCommand;
import org.sapia.corus.interop.api.message.FaultMessagePart;
import org.sapia.corus.interop.api.message.Message;
import org.sapia.corus.interop.api.message.MessageCommand;
import org.sapia.corus.interop.api.message.PollMessageCommand;
import org.sapia.corus.interop.api.message.ProcessMessageHeader;
import org.sapia.corus.interop.api.message.RestartMessageCommand;
import org.sapia.corus.interop.api.message.ServerMessageHeader;
import org.sapia.corus.interop.api.message.StatusMessageCommand;
import org.sapia.corus.interop.client.CyclicIdGenerator;


/**
 * This class can be reused in servers that receive requests from Corus clients.
 * This class supports the Corus Interoperability Protocol through stateless
 * stream request handling. This class can be used in conjonction with the
 * {@link ClientStatelessStreamHelper} class in client-server implementations.
 * <p>
 * An instance of this class is given a {@link RequestListener} instance at construction
 * time; the listener is in charge of handling the incoming commands.
 * <p>
 * Once an instance of this class is created, server implementations need only calling
 * its {@link #processRequest(InputStream, OutputStream)} method, passing to it latter the 
 * request's {@link InputStream}, and the {@link OutputStream} to which the response is
 * expected to be written.
 * 
 * @see org.sapia.corus.interop.helpers.RequestListener
 *
 * @author yduchesne
 */
public class ServerStatelessStreamHelper {

  private InteropCodec     _codec;
  private RequestListener  _listener;
  private String           _actor;

  /**
   * Creates an instance of this class.
   *
   * @param codec the {@link InteropCodec} to use internally.
   * @param listener the {@link RequestListener} to use for handling incoming requests.
   * @param actor a {@link String} that is used when sending back
   * fault messages to clients (fault messages can indeed contain "actor" information
   * to help identify the source of an error).
   */
  public ServerStatelessStreamHelper(InteropCodec codec, RequestListener listener, String actor) {
    _codec    = codec;
    _listener = listener;
    _actor    = actor;
  }
  
  /**
   * @return the {@link InteropCodec} instance being used.
   */
  public InteropCodec getCodec() {
    return _codec;
  }

  /**
   * Processes the given request stream, returning the response in the given output stream.
   *
   * @param request an {@link InputStream} expected to contain XML that complies with
   * the Corus interop spec.
   * @param response the {@link OutputStream} that will be used by this instance
   * to send the response back to the client.
   * @throws Exception if a problem occurs while processing the request, and if no SOAP
   * fault could be sent back to the client.
   */
  public void processRequest(InputStream request, OutputStream response) throws Exception {
    long start = System.currentTimeMillis();

    try {
      Message msg = _codec.decode(request);

      if ((msg.getHeader() == null) ||
            !(msg.getHeader() instanceof ProcessMessageHeader)) {
        sendFault(response,
                  new Exception("Process header missing"),
                  System.currentTimeMillis() - start);

        return;
      }

      ProcessMessageHeader procHeader = (ProcessMessageHeader) msg.getHeader();

      if (procHeader.getCorusPid() == null || procHeader.getCorusPid().isEmpty()) {
        sendFault(response,
                  new Exception("'corusPid' missing in process header"),
                  System.currentTimeMillis() - start);

        return;
      }

      if (procHeader.getRequestId() == null || procHeader.getRequestId().isEmpty()) {
        sendFault(response,
                  new Exception("'requestId' missing in process header"),
                  System.currentTimeMillis() - start);

        return;
      }

      if (msg.getCommands().size() == 0) {
        sendAck(response, System.currentTimeMillis() - start);
        return;
      }

      // Process the commands
      List<MessageCommand> commands = new ArrayList<MessageCommand>();
      for (MessageCommand cmd : msg.getCommands()) {

        if (cmd instanceof PollMessageCommand) {
          List<MessageCommand> pollCommands = _listener.onPoll(procHeader, (PollMessageCommand) cmd);
          commands.addAll(pollCommands);

        } else if (cmd instanceof StatusMessageCommand) {
          List<MessageCommand> statusCommands = _listener.onStatus(procHeader, (StatusMessageCommand) cmd);
          commands.addAll(statusCommands);

        } else if (cmd instanceof ConfirmShutdownMessageCommand) {
          _listener.onConfirmShutdown(procHeader, (ConfirmShutdownMessageCommand) cmd);

        } else if (cmd instanceof RestartMessageCommand) {
          _listener.onRestart(procHeader, (RestartMessageCommand) cmd);
        }
      }

      // Send the result
      if (commands.size() > 0) {
        sendMessage(response, createMessageBuilder(System.currentTimeMillis() - start).commands(commands).build());
      } else {
        sendAck(response, System.currentTimeMillis() - start);
      }
      
    } catch (Exception e) {
      sendFault(response, e, System.currentTimeMillis() - start);
      
    } finally {
      InteropUtils.closeSilently(request);
      InteropUtils.closeSilently(response);
    }
  }

  protected void sendFault(OutputStream out, Exception e, long processingTime) throws Exception {
    FaultMessagePart fault = _codec.getMessageBuilderFactory().newFaultMessageBuilder()
        .faultActor(_actor)
        .faultCode("500")
        .faultDetail(e)
        .faultString(e.getMessage())
        .build();
    sendMessage(out, createMessageBuilder(processingTime).error(fault).build());
  }

  protected void sendAck(OutputStream out, long processingTime) throws Exception {
    AckMessageCommand ack = _codec.getMessageBuilderFactory().newAckMessageBuilder()
        .commandId(CyclicIdGenerator.newCommandId())
        .build();
    sendMessage(out, createMessageBuilder(processingTime).command(ack).build());
  }

  protected void sendMessage(OutputStream out, Message toSend) throws Exception {
    _codec.encode(toSend, out);
    out.flush();
  }

  private Message.Builder createMessageBuilder(long processingTime) {
    ServerMessageHeader header = _codec.getMessageBuilderFactory().newServerMessageHeaderBuilder()
        .requestId(CyclicIdGenerator.newRequestId())
        .processingTime(processingTime)
        .build();
    return Message.Builder.newInstance().header(header);
  }
  
}
