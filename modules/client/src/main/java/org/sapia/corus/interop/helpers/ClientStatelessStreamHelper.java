package org.sapia.corus.interop.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.sapia.corus.interop.InteropCodec;
import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ConfirmShutdownMessageCommand;
import org.sapia.corus.interop.api.message.InteropMessageBuilderFactory;
import org.sapia.corus.interop.api.message.Message;
import org.sapia.corus.interop.api.message.MessageCommand;
import org.sapia.corus.interop.api.message.PollMessageCommand;
import org.sapia.corus.interop.api.message.ProcessMessageHeader;
import org.sapia.corus.interop.api.message.RestartMessageCommand;
import org.sapia.corus.interop.api.message.StatusMessageCommand;
import org.sapia.corus.interop.client.CyclicIdGenerator;
import org.sapia.corus.interop.client.FaultException;
import org.sapia.corus.interop.client.InteropProtocol;
import org.sapia.corus.interop.client.Log;
import org.sapia.corus.interop.client.StdoutLog;


/**
 * An instance of this class can be reused to implement the client-side
 * behavior of Corus's interoperability protocol. In fact, this class
 * implements the {@link InteropProtocol} interface.
 * <p>
 * This class implements stateless, stream-based communication (i.e.: connection closed after
 * request/response cycle). The connection implementations are given to an instance of
 * this class through the {@link #newStreamConnection() template method, that must be
 * implemented by inheriting classes.
 *
 * @author yduchesne
 * @author jcdesrochers
 */
public abstract class ClientStatelessStreamHelper implements InteropProtocol {

  private String           _corusPid;
  private InteropCodec     _codec; 
  protected Log            _log      = new StdoutLog();
  private byte[]           _buf      = new byte[2048];

  protected ClientStatelessStreamHelper(InteropCodec codec, String corusPid) {
    _codec    = codec;
    _corusPid = corusPid;
  }
  
  @Override
  public InteropMessageBuilderFactory getMessageBuilderFactory() {
    return _codec.getMessageBuilderFactory();
  }

  /**
   * @see InteropProtocol#setLog(Log)
   */
  public void setLog(Log log) {
    _log = log;
  }

  /**
   * This template method is called by an instance of this class
   * every time it needs to connect to its remote endpoint.
   *
   * @return a <code>StreamConnection</code>.
   * @throws IOException
   */
  protected abstract StreamConnection newStreamConnection() throws IOException;

  protected void doSendConfirmShutdown() throws FaultException, IOException {
    ConfirmShutdownMessageCommand confirm = _codec.getMessageBuilderFactory()
        .newConfirmShutdownMessageBuilder()
        .commandId(CyclicIdGenerator.newCommandId())
        .build();
    doSendRequest(new MessageCommand[] { confirm });
  }

  protected List<MessageCommand> doSendPoll() throws FaultException, IOException {
    PollMessageCommand poll = _codec.getMessageBuilderFactory()
        .newPollMessageBuilder()
        .commandId(CyclicIdGenerator.newCommandId())
        .build();
    return doSendRequest(new MessageCommand[] { poll });
  }

  protected void doSendRestart() throws FaultException, IOException {
    RestartMessageCommand res = _codec.getMessageBuilderFactory()
        .newRestartMessageBuilder()
        .commandId(CyclicIdGenerator.newCommandId()).build();
    doSendRequest(new MessageCommand[] { res });
  }

  protected List<MessageCommand> doSendStatus(StatusMessageCommand stat, boolean isPoll) throws FaultException, IOException {
    if (isPoll) {
      PollMessageCommand poll = _codec.getMessageBuilderFactory()
          .newPollMessageBuilder()
          .commandId(CyclicIdGenerator.newCommandId())
          .build();
      return doSendRequest(new MessageCommand[] { poll, stat } );

    } else {
      return doSendRequest(new MessageCommand[] { stat } );
    }
  }

  protected synchronized List<MessageCommand> doSendRequest(MessageCommand[] toSend) throws FaultException, IOException {
    Message.Builder msgBuilder = createMessageBuilder();
    for (int i = 0; i < toSend.length; i++) {
      _log.debug("Sending request to corus: " + toSend[i]);
      msgBuilder.command(toSend[i]);
    }

    StreamConnection conn         = newStreamConnection();
    OutputStream     os           = null;
    InputStream      is           = null;

    try {
      os = conn.getOutputStream();
      _codec.encode(msgBuilder.build(), os);
      os.flush();
      os.close();
      is           = conn.getInputStream();
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      int read = 0;
      while((read = is.read(_buf, 0, _buf.length)) > 0) {
        bos.write(_buf, 0, read); 
      }
      ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
      Message response = _codec.decode(bis);

      if (response.getError() != null) {
        throw new FaultException(response.getError());
      }
      
      List<MessageCommand> toReturn = new ArrayList<MessageCommand>();
      for (MessageCommand cmd : response.getCommands()) {
        toReturn.add(cmd);
      }

      return toReturn;
    } finally {
      InteropUtils.closeSilently(os);
      InteropUtils.closeSilently(is);
    }
  }

  private Message.Builder createMessageBuilder() {
    ProcessMessageHeader header = _codec.getMessageBuilderFactory().newProcessMessageHeaderBuilder()
        .corusPid(_corusPid)
        .requestId(CyclicIdGenerator.newRequestId())
        .build();
    
    return Message.Builder.newInstance().header(header);
  }
  
}
