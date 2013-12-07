package org.sapia.corus.interop.protobuf;

import org.sapia.corus.interop.api.message.Message;
import org.sapia.corus.interop.protobuf.message.AckProtoAdapter;
import org.sapia.corus.interop.protobuf.message.ConfigurationEventProtoAdapter;
import org.sapia.corus.interop.protobuf.message.ConfirmShutdownProtoAdapter;
import org.sapia.corus.interop.protobuf.message.FaultProtoAdapter;
import org.sapia.corus.interop.protobuf.message.PollProtoAdapter;
import org.sapia.corus.interop.protobuf.message.ProcessEventProtoAdapter;
import org.sapia.corus.interop.protobuf.message.ProcessMessageHeaderProtoAdapter;
import org.sapia.corus.interop.protobuf.message.RestartProtoAdapter;
import org.sapia.corus.interop.protobuf.message.ServerMessageHeaderProtoAdapter;
import org.sapia.corus.interop.protobuf.message.ShutdownProtoAdapter;
import org.sapia.corus.interop.protobuf.message.StatusProtoAdapter;

/**
 * Handlers Protobuf message decoding.
 *  
 * @author yduchesne
 *
 */
public class ProtobufDecoderHelper {

  private ProtobufDecoderHelper() {
    
  }
  
  /**
   * @param decodedMsg a Protobuf-specific message.
   * @return the {@link Message} corresponding to the Protobuf-specific message.
   */
  public static Message decode(CorusInteroperability.Message decodedMsg) {
    Message.Builder msgBuilder = Message.Builder.newInstance();
    for (CorusInteroperability.Command cmd : decodedMsg.getCommandsList()) {
      switch (cmd.getType()) {
        case ACK:
          CorusInteroperability.Ack ack = cmd.getExtension(CorusInteroperability.Ack.command);
          msgBuilder.command(new AckProtoAdapter(ack));
          break;
        case CONFIGURATION_EVENT:
          CorusInteroperability.ConfigurationEvent confEvent = cmd.getExtension(CorusInteroperability.ConfigurationEvent.command);
          msgBuilder.command(new ConfigurationEventProtoAdapter(confEvent));
          break;
        case CONFIRM_SHUTDOWN:
          CorusInteroperability.ConfirmShutdown confirmShutdown = cmd.getExtension(CorusInteroperability.ConfirmShutdown.command);
          msgBuilder.command(new ConfirmShutdownProtoAdapter(confirmShutdown));
          break;
        case POLL:
          CorusInteroperability.Poll poll = cmd.getExtension(CorusInteroperability.Poll.command);
          msgBuilder.command(new PollProtoAdapter(poll));
          break;
        case PROCESS_EVENT:
          CorusInteroperability.ProcessEvent processEvent = cmd.getExtension(CorusInteroperability.ProcessEvent.command);
          msgBuilder.command(new ProcessEventProtoAdapter(processEvent));
          break;
        case RESTART:
          CorusInteroperability.Restart restart = cmd.getExtension(CorusInteroperability.Restart.command);
          msgBuilder.command(new RestartProtoAdapter(restart));
          break;
        case SHUTDOWN:
          CorusInteroperability.Shutdown shutdown = cmd.getExtension(CorusInteroperability.Shutdown.command);
          msgBuilder.command(new ShutdownProtoAdapter(shutdown));
          break;
        case STATUS:
          CorusInteroperability.Status status = cmd.getExtension(CorusInteroperability.Status.command);
          msgBuilder.command(new StatusProtoAdapter(status));
          break;
        default:
          throw new IllegalStateException("Unknown command type: " + cmd.getType().name());
      }
    }
    
    switch(decodedMsg.getHeader().getType()) {
      case PROCESS:
        CorusInteroperability.Process process = decodedMsg.getHeader().getExtension(CorusInteroperability.Process.header);
        msgBuilder.header(new ProcessMessageHeaderProtoAdapter(process));
        break;
      case SERVER:
        CorusInteroperability.Server server = decodedMsg.getHeader().getExtension(CorusInteroperability.Server.header);
        msgBuilder.header(new ServerMessageHeaderProtoAdapter(server));
        break;
      default:
        throw new IllegalStateException("Unknown header type: " + decodedMsg.getHeader().getType());
    }
    
    if (decodedMsg.hasError()) {
      msgBuilder.error(new FaultProtoAdapter(decodedMsg.getError()));
    }
    
    return msgBuilder.build();
  }

}
