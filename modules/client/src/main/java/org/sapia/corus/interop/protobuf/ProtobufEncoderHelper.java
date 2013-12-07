package org.sapia.corus.interop.protobuf;

/**
 * Handles Protobuf encoding.
 * 
 * @author jcdesrochers
 *
 */
public class ProtobufEncoderHelper {
  
  private ProtobufEncoderHelper() {
  }

  public static CorusInteroperability.Header wrapHeader(CorusInteroperability.Process aHeader) {
    CorusInteroperability.Header created = CorusInteroperability.Header.newBuilder()
        .setType(CorusInteroperability.Header.HeaderType.PROCESS)
        .setExtension(CorusInteroperability.Process.header, aHeader).build();

    return created;
  }

  public static CorusInteroperability.Header wrapHeader(CorusInteroperability.Server aHeader) {
    CorusInteroperability.Header created = CorusInteroperability.Header.newBuilder()
        .setType(CorusInteroperability.Header.HeaderType.SERVER)
        .setExtension(CorusInteroperability.Server.header, aHeader).build();

    return created;
  }

  public static CorusInteroperability.Command wrapCommand(CorusInteroperability.Poll aCommand) {
    CorusInteroperability.Command created = CorusInteroperability.Command.newBuilder()
        .setType(CorusInteroperability.Command.CommandType.POLL)
        .setExtension(CorusInteroperability.Poll.command, aCommand).build();

    return created;
  }

  public static CorusInteroperability.Command wrapCommand(CorusInteroperability.Status aCommand) {
    CorusInteroperability.Command created = CorusInteroperability.Command.newBuilder()
        .setType(CorusInteroperability.Command.CommandType.STATUS)
        .setExtension(CorusInteroperability.Status.command, aCommand).build();

    return created;
  }

  public static CorusInteroperability.Command wrapCommand(CorusInteroperability.Restart aCommand) {
    CorusInteroperability.Command created = CorusInteroperability.Command.newBuilder()
        .setType(CorusInteroperability.Command.CommandType.RESTART)
        .setExtension(CorusInteroperability.Restart.command, aCommand).build();

    return created;
  }

  public static CorusInteroperability.Command wrapCommand(CorusInteroperability.ConfirmShutdown aCommand) {
    CorusInteroperability.Command created = CorusInteroperability.Command.newBuilder()
        .setType(CorusInteroperability.Command.CommandType.CONFIRM_SHUTDOWN)
        .setExtension(CorusInteroperability.ConfirmShutdown.command, aCommand).build();

    return created;
  }

  public static CorusInteroperability.Command wrapCommand(CorusInteroperability.Ack aCommand) {
    CorusInteroperability.Command created = CorusInteroperability.Command.newBuilder()
        .setType(CorusInteroperability.Command.CommandType.ACK)
        .setExtension(CorusInteroperability.Ack.command, aCommand).build();

    return created;
  }

  public static CorusInteroperability.Command wrapCommand(CorusInteroperability.Shutdown aCommand) {
    CorusInteroperability.Command created = CorusInteroperability.Command.newBuilder()
        .setType(CorusInteroperability.Command.CommandType.SHUTDOWN)
        .setExtension(CorusInteroperability.Shutdown.command, aCommand).build();

    return created;
  }

  public static CorusInteroperability.Command wrapCommand(CorusInteroperability.ProcessEvent aCommand) {
    CorusInteroperability.Command created = CorusInteroperability.Command.newBuilder()
        .setType(CorusInteroperability.Command.CommandType.PROCESS_EVENT)
        .setExtension(CorusInteroperability.ProcessEvent.command, aCommand).build();

    return created;
  }

  public static CorusInteroperability.Command wrapCommand(CorusInteroperability.ConfigurationEvent aCommand) {
    CorusInteroperability.Command created = CorusInteroperability.Command.newBuilder()
        .setType(CorusInteroperability.Command.CommandType.CONFIGURATION_EVENT)
        .setExtension(CorusInteroperability.ConfigurationEvent.command, aCommand).build();

    return created;
  }
}
