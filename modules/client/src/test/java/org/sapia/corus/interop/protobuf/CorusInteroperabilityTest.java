package org.sapia.corus.interop.protobuf;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ConfigurationEventMessageCommand;

import com.google.protobuf.ExtensionRegistry;


public class CorusInteroperabilityTest {
  
  private ExtensionRegistry registry;
  
  @Before
  public void setUp() throws Exception {
    registry = ExtensionRegistry.newInstance();
    CorusInteroperability.registerAllExtensions(registry);
  }
  
  protected CorusInteroperability.Message cloneWithSerialization(CorusInteroperability.Message aMessage) throws Exception {
    byte[] data = aMessage.toByteArray();
    
    CorusInteroperability.Message clone = CorusInteroperability.Message.newBuilder().
        mergeFrom(data, registry).
        build();
    
    return clone;
  }
  
  @Test
  public void testPollRequest() throws Exception {
    CorusInteroperability.Poll pollRequest = CorusInteroperability.Poll.newBuilder().
        setCommandId("675432").
        build();
    
    CorusInteroperability.Process process = CorusInteroperability.Process.newBuilder().
        setRequestId("113344").
        setCorusPid("2045").
        build();
    
    CorusInteroperability.Message message = CorusInteroperability.Message.newBuilder().
        setHeader(ProtobufEncoderHelper.wrapHeader(process)).
        addCommands(ProtobufEncoderHelper.wrapCommand(pollRequest)).
        build();
    
    CorusInteroperability.Message deserializedMessage = cloneWithSerialization(message);
    Assert.assertNotNull(deserializedMessage);
    
    assertProcessHeader("113344", "2045", deserializedMessage.getHeader());
    Assert.assertEquals(1, deserializedMessage.getCommandsCount());
    assertPollCommand("675432", deserializedMessage.getCommands(0));
  }
  
  @Test
  public void testStatusRequest_singleContext() throws Exception {
    CorusInteroperability.Status.Context context = CorusInteroperability.Status.Context.newBuilder().
        setName("root/sna/foo").
        addParams(CorusInteroperability.Param.newBuilder().setName("prop1").setValue("value1").build()).
        build();
    
    CorusInteroperability.Status statusRequest = CorusInteroperability.Status.newBuilder().
        setCommandId("445577").
        addContexts(context).
        build();
    
    CorusInteroperability.Process process = CorusInteroperability.Process.newBuilder().
        setRequestId("7897988").
        setCorusPid("568").
        build();
    
    CorusInteroperability.Message message = CorusInteroperability.Message.newBuilder().
        setHeader(ProtobufEncoderHelper.wrapHeader(process)).
        addCommands(ProtobufEncoderHelper.wrapCommand(statusRequest)).
        build();
    
    CorusInteroperability.Message deserializedMessage = cloneWithSerialization(message);
    Assert.assertNotNull(deserializedMessage);
    
    assertProcessHeader("7897988", "568", deserializedMessage.getHeader());
    Assert.assertEquals(1, deserializedMessage.getCommandsCount());
    assertStatusCommand("445577", new String[] { "root/sna/foo" }, new String[][] {{ "prop1", "value1" }}, deserializedMessage.getCommands(0));
  }
  
  @Test
  public void testStatusRequest_multipleContexts() throws Exception {
    CorusInteroperability.Status.Context context1 = CorusInteroperability.Status.Context.newBuilder().
        setName("root/sna/foo").
        addParams(CorusInteroperability.Param.newBuilder().setName("prop1").setValue("value1").build()).
        addParams(CorusInteroperability.Param.newBuilder().setName("prop2").setValue("value2").build()).
        build();

    CorusInteroperability.Status.Context context2 = CorusInteroperability.Status.Context.newBuilder().
        setName("jvm memory").
        addParams(CorusInteroperability.Param.newBuilder().setName("min").setValue("32").build()).
        addParams(CorusInteroperability.Param.newBuilder().setName("max").setValue("64").build()).
        build();
    
    CorusInteroperability.Status statusRequest = CorusInteroperability.Status.newBuilder().
        setCommandId("444898").
        addContexts(context1).
        addContexts(context2).
        build();
    
    CorusInteroperability.Process process = CorusInteroperability.Process.newBuilder().
        setRequestId("2114").
        setCorusPid("34879").
        build();
    
    CorusInteroperability.Message message = CorusInteroperability.Message.newBuilder().
        setHeader(ProtobufEncoderHelper.wrapHeader(process)).
        addCommands(ProtobufEncoderHelper.wrapCommand(statusRequest)).
        build();
    
    CorusInteroperability.Message deserializedMessage = cloneWithSerialization(message);
    Assert.assertNotNull(deserializedMessage);
    
    assertProcessHeader("2114", "34879", deserializedMessage.getHeader());
    Assert.assertEquals(1, deserializedMessage.getCommandsCount());
    assertStatusCommand("444898", new String[] { "root/sna/foo", "jvm memory" },
        new String[][] { { "prop1", "value1", "prop2", "value2" }, { "min", "32", "max", "64" }  }, deserializedMessage.getCommands(0));
  }
  
  @Test
  public void testRestartRequest() throws Exception {
    CorusInteroperability.Restart restartRequest = CorusInteroperability.Restart.newBuilder().
        setCommandId("46532").
        build();
    
    CorusInteroperability.Process process = CorusInteroperability.Process.newBuilder().
        setRequestId("124578").
        setCorusPid("11255").
        build();
    
    CorusInteroperability.Message message = CorusInteroperability.Message.newBuilder().
        setHeader(ProtobufEncoderHelper.wrapHeader(process)).
        addCommands(ProtobufEncoderHelper.wrapCommand(restartRequest)).
        build();
    
    CorusInteroperability.Message deserializedMessage = cloneWithSerialization(message);
    Assert.assertNotNull(deserializedMessage);
    
    assertProcessHeader("124578", "11255", deserializedMessage.getHeader());
    Assert.assertEquals(1, deserializedMessage.getCommandsCount());
    assertRestartCommand("46532", deserializedMessage.getCommands(0));
  }
  
  @Test
  public void testConfirmShutdownRequest() throws Exception {
    CorusInteroperability.ConfirmShutdown confirmRequest = CorusInteroperability.ConfirmShutdown.newBuilder().
        setCommandId("46535").
        build();
    
    CorusInteroperability.Process process = CorusInteroperability.Process.newBuilder().
        setRequestId("124579").
        setCorusPid("11255").
        build();
    
    CorusInteroperability.Message message = CorusInteroperability.Message.newBuilder().
        setHeader(ProtobufEncoderHelper.wrapHeader(process)).
        addCommands(ProtobufEncoderHelper.wrapCommand(confirmRequest)).
        build();
    
    CorusInteroperability.Message deserializedMessage = cloneWithSerialization(message);
    Assert.assertNotNull(deserializedMessage);
    
    assertProcessHeader("124579", "11255", deserializedMessage.getHeader());
    Assert.assertEquals(1, deserializedMessage.getCommandsCount());
    assertConfirmShutdownCommand("46535", deserializedMessage.getCommands(0));
  }
  
  @Test
  public void testProcessEventCommand() throws Exception {
    CorusInteroperability.ProcessEvent processEventCommand = CorusInteroperability.ProcessEvent.newBuilder().
            setCommandId("11487").
            setEventType("customAppEvent").
            addParams(CorusInteroperability.Param.newBuilder().setName("sna1").setValue("foo").build()).
            addParams(CorusInteroperability.Param.newBuilder().setName("sna2").setValue("bar").build()).
            build();

    CorusInteroperability.Server server = CorusInteroperability.Server.newBuilder().
        setRequestId("88471").
        setProcessingTime(5).
        build();

    CorusInteroperability.Message message = CorusInteroperability.Message.newBuilder().
            setHeader(ProtobufEncoderHelper.wrapHeader(server)).
            addCommands(ProtobufEncoderHelper.wrapCommand(processEventCommand)).
            build();

    CorusInteroperability.Message deserializedMessage = cloneWithSerialization(message);
    Assert.assertNotNull(deserializedMessage);
    
    assertServerHeader("88471", 5, deserializedMessage.getHeader());
    Assert.assertEquals(1, deserializedMessage.getCommandsCount());
    assertProcessEventCommand("11487", "customAppEvent", new String[] {"sna1", "foo", "sna2", "bar"}, deserializedMessage.getCommands(0));
  }
  
  @Test
  public void testConfigurationEventCommand() throws Exception {
    CorusInteroperability.ConfigurationEvent configEvent = CorusInteroperability.ConfigurationEvent.newBuilder()
            .setCommandId("84304880")
            .setEventType(ConfigurationEventMessageCommand.TYPE_UPDATE)
            .addParams(CorusInteroperability.Param.newBuilder().setName("sna").setValue("foo").build())
            .addParams(CorusInteroperability.Param.newBuilder().setName("foo2").setValue("dew").build())
            .build();

    CorusInteroperability.Server server = CorusInteroperability.Server.newBuilder().
        setRequestId("91567").
        setProcessingTime(4).
        build();

    CorusInteroperability.Message message = CorusInteroperability.Message.newBuilder().
            setHeader(ProtobufEncoderHelper.wrapHeader(server)).
            addCommands(ProtobufEncoderHelper.wrapCommand(configEvent)).
            build();

    CorusInteroperability.Message deserializedMessage = cloneWithSerialization(message);
    Assert.assertNotNull(deserializedMessage);
    
    assertServerHeader("91567", 4, deserializedMessage.getHeader());
    Assert.assertEquals(1, deserializedMessage.getCommandsCount());
    
    assertConfigurationEventCommand("84304880", "update",
        new CorusInteroperability.Param[] {
            CorusInteroperability.Param.newBuilder().setName("sna").setValue("foo").build(), 
            CorusInteroperability.Param.newBuilder().setName("foo2").setValue("dew").build()
        }, deserializedMessage.getCommands(0));
  }
  
  @Test
  public void testAckResponse() throws Exception {
    CorusInteroperability.Ack ackResponse = CorusInteroperability.Ack.newBuilder().
        setCommandId("889988").
        build();
    
    CorusInteroperability.Server server = CorusInteroperability.Server.newBuilder().
        setRequestId("88456").
        setProcessingTime(221).
        build();
    
    CorusInteroperability.Message message = CorusInteroperability.Message.newBuilder().
        setHeader(ProtobufEncoderHelper.wrapHeader(server)).
        addCommands(ProtobufEncoderHelper.wrapCommand(ackResponse)).
        build();
    
    CorusInteroperability.Message deserializedMessage = cloneWithSerialization(message);
    Assert.assertNotNull(deserializedMessage);
    
    assertServerHeader("88456", 221, deserializedMessage.getHeader());
    Assert.assertEquals(1, deserializedMessage.getCommandsCount());
    assertAckCommand("889988", deserializedMessage.getCommands(0));
  }
  
  @Test
  public void testShutdownRequest() throws Exception {
    CorusInteroperability.Shutdown shutdownRequest = CorusInteroperability.Shutdown.newBuilder().
        setCommandId("55655").
        setRequestor(CorusInteroperability.Shutdown.RequestorActor.ADMIN).
        build();
    
    CorusInteroperability.Server server = CorusInteroperability.Server.newBuilder().
        setRequestId("88477").
        setProcessingTime(0).
        build();
    
    CorusInteroperability.Message message = CorusInteroperability.Message.newBuilder().
        setHeader(ProtobufEncoderHelper.wrapHeader(server)).
        addCommands(ProtobufEncoderHelper.wrapCommand(shutdownRequest)).
        build();
    
    CorusInteroperability.Message deserializedMessage = cloneWithSerialization(message);
    Assert.assertNotNull(deserializedMessage);
    
    assertServerHeader("88477", 0, deserializedMessage.getHeader());
    Assert.assertEquals(1, deserializedMessage.getCommandsCount());
    assertShutdownCommand("55655", CorusInteroperability.Shutdown.RequestorActor.ADMIN, deserializedMessage.getCommands(0));
  }
  
  @Test
  public void testErrorResponse() throws Exception {
    CorusInteroperability.Server server = CorusInteroperability.Server.newBuilder().
        setRequestId("1").
        setProcessingTime(10).
        build();
    
    CorusInteroperability.Fault error = CorusInteroperability.Fault.newBuilder().
            setErrorCode("1009").
            setSourceActor("client").
            setErrorMessage("Some error occured").
            setErrorDetails("... some detailed error message ...").
            build();
    
    CorusInteroperability.Message message = CorusInteroperability.Message.newBuilder().
        setHeader(ProtobufEncoderHelper.wrapHeader(server)).
        setError(error).
        build();
    
    CorusInteroperability.Message deserializedMessage = cloneWithSerialization(message);
    Assert.assertNotNull(deserializedMessage);
    
    assertServerHeader("1", 10, deserializedMessage.getHeader());
    Assert.assertEquals(0, deserializedMessage.getCommandsCount());
    Assert.assertEquals("1009", deserializedMessage.getError().getErrorCode());
    Assert.assertEquals("client", deserializedMessage.getError().getSourceActor());
    Assert.assertEquals("Some error occured", deserializedMessage.getError().getErrorMessage());
    Assert.assertEquals("... some detailed error message ...", deserializedMessage.getError().getErrorDetails());
  }
  
  public static void assertProcessHeader(String eRequestId, String eCorusPid, CorusInteroperability.Header actual) {
    Assert.assertNotNull(actual);
    Assert.assertEquals(CorusInteroperability.Header.HeaderType.PROCESS, actual.getType());

    CorusInteroperability.Process dProcess = actual.getExtension(CorusInteroperability.Process.header);
    Assert.assertNotNull(dProcess);
    Assert.assertEquals(eRequestId, dProcess.getRequestId());
    Assert.assertEquals(eCorusPid, dProcess.getCorusPid());
  }
  
  public static void assertServerHeader(String eRequestId, int eProcessingTime, CorusInteroperability.Header actual) {
    Assert.assertNotNull(actual);
    Assert.assertEquals(CorusInteroperability.Header.HeaderType.SERVER, actual.getType());

    CorusInteroperability.Server dServer = actual.getExtension(CorusInteroperability.Server.header);
    Assert.assertNotNull(dServer);
    Assert.assertEquals(eRequestId, dServer.getRequestId());
    Assert.assertEquals(eProcessingTime, dServer.getProcessingTime());
  }
  
  public static void assertPollCommand(String eCommandId, CorusInteroperability.Command actual) {
    Assert.assertNotNull(actual);
    Assert.assertEquals(CorusInteroperability.Command.CommandType.POLL, actual.getType());

    CorusInteroperability.Poll aCommand = actual.getExtension(CorusInteroperability.Poll.command);
    Assert.assertNotNull(aCommand);
    Assert.assertEquals(eCommandId, aCommand.getCommandId());
  }
  
  public static void assertStatusCommand(String eCommandId, String[] eContextNames, String[][] eParams, CorusInteroperability.Command actual) {
    Assert.assertNotNull(actual);
    Assert.assertEquals(CorusInteroperability.Command.CommandType.STATUS, actual.getType());

    CorusInteroperability.Status aCommand = actual.getExtension(CorusInteroperability.Status.command);
    Assert.assertNotNull(aCommand);
    Assert.assertEquals(eCommandId, aCommand.getCommandId());
    Assert.assertEquals(eContextNames.length, aCommand.getContextsCount());

    for (int i = 0; i < eContextNames.length; i++) {
      CorusInteroperability.Status.Context aContext = aCommand.getContexts(i);
      Assert.assertNotNull(aContext);
      Assert.assertEquals(eContextNames[i], aContext.getName());
      Assert.assertEquals(eParams[i].length/2, aContext.getParamsCount());

      for (int j = 0; j < eParams[i].length/2; j++) {
        CorusInteroperability.Param aParam = aContext.getParams(j);
        Assert.assertNotNull(aParam);
        Assert.assertEquals(eParams[i][j*2], aParam.getName());
        Assert.assertEquals(eParams[i][j*2+1], aParam.getValue());
      }
    }
  }
  
  public static void assertRestartCommand(String eCommandId, CorusInteroperability.Command actual) {
    Assert.assertNotNull(actual);
    Assert.assertEquals(CorusInteroperability.Command.CommandType.RESTART, actual.getType());

    CorusInteroperability.Restart aCommand = actual.getExtension(CorusInteroperability.Restart.command);
    Assert.assertNotNull(aCommand);
    Assert.assertEquals(eCommandId, aCommand.getCommandId());
  }
  
  public static void assertConfirmShutdownCommand(String eCommandId, CorusInteroperability.Command actual) {
    Assert.assertNotNull(actual);
    Assert.assertEquals(CorusInteroperability.Command.CommandType.CONFIRM_SHUTDOWN, actual.getType());

    CorusInteroperability.ConfirmShutdown aCommand = actual.getExtension(CorusInteroperability.ConfirmShutdown.command);
    Assert.assertNotNull(aCommand);
    Assert.assertEquals(eCommandId, aCommand.getCommandId());
  }
  
  public static void assertAckCommand(String eCommandId, CorusInteroperability.Command actual) {
    Assert.assertNotNull(actual);
    Assert.assertEquals(CorusInteroperability.Command.CommandType.ACK, actual.getType());

    CorusInteroperability.Ack aCommand = actual.getExtension(CorusInteroperability.Ack.command);
    Assert.assertNotNull(aCommand);
    Assert.assertEquals(eCommandId, aCommand.getCommandId());
  }
  
  public static void assertShutdownCommand(String eCommandId, CorusInteroperability.Shutdown.RequestorActor eRequestor,
      CorusInteroperability.Command actual) {
    Assert.assertNotNull(actual);
    Assert.assertEquals(CorusInteroperability.Command.CommandType.SHUTDOWN, actual.getType());

    CorusInteroperability.Shutdown aCommand = actual.getExtension(CorusInteroperability.Shutdown.command);
    Assert.assertNotNull(aCommand);
    Assert.assertEquals(eCommandId, aCommand.getCommandId());
    Assert.assertEquals(eRequestor, aCommand.getRequestor());
  }
  
  public static void assertProcessEventCommand(String eCommandId, String eEventType, String[] eParams, CorusInteroperability.Command actual) {
    Assert.assertNotNull(actual);
    Assert.assertEquals(CorusInteroperability.Command.CommandType.PROCESS_EVENT, actual.getType());

    CorusInteroperability.ProcessEvent aCommand = actual.getExtension(CorusInteroperability.ProcessEvent.command);
    Assert.assertNotNull(aCommand);
    Assert.assertEquals(eCommandId, aCommand.getCommandId());
    Assert.assertEquals(eEventType, aCommand.getEventType());
    
    Assert.assertEquals(eParams.length/2, aCommand.getParamsCount());
    for (int i = 0; i < eParams.length/2; i++) {
      CorusInteroperability.Param aParam = aCommand.getParams(i);
      Assert.assertNotNull(aParam);
      Assert.assertEquals(eParams[i*2], aParam.getName());
      Assert.assertEquals(eParams[i*2+1], aParam.getValue());
    }
  }
  
  public static void assertConfigurationEventCommand(String eCommandId, String eEventType, CorusInteroperability.Param[] eParams, CorusInteroperability.Command actual) {
    Assert.assertNotNull(actual);
    Assert.assertEquals(CorusInteroperability.Command.CommandType.CONFIGURATION_EVENT, actual.getType());

    CorusInteroperability.ConfigurationEvent aCommand = actual.getExtension(CorusInteroperability.ConfigurationEvent.command);
    Assert.assertNotNull(aCommand);
    Assert.assertEquals(eCommandId, aCommand.getCommandId());
    Assert.assertEquals(eEventType, aCommand.getEventType());
    
    Assertions.assertThat(aCommand.getParamsList()).containsOnly(eParams);
  }
    
}
