package org.sapia.corus.interop.soap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.junit.Test;
import org.sapia.corus.interop.api.message.ContextMessagePart;
import org.sapia.corus.interop.api.message.ParamMessagePart;
import org.sapia.corus.interop.api.message.ShutdownMessageCommand.Requestor;
import org.sapia.corus.interop.soap.message.Ack;
import org.sapia.corus.interop.soap.message.ConfigurationEvent;
import org.sapia.corus.interop.soap.message.ConfirmShutdown;
import org.sapia.corus.interop.soap.message.Poll;
import org.sapia.corus.interop.soap.message.Process;
import org.sapia.corus.interop.soap.message.ProcessEvent;
import org.sapia.corus.interop.soap.message.Restart;
import org.sapia.corus.interop.soap.message.Server;
import org.sapia.corus.interop.soap.message.Shutdown;
import org.sapia.corus.interop.soap.message.Status;

public class SoapCodecDeserializationTest {
  static {
    org.apache.log4j.BasicConfigurator.configure();
  }

  private SoapInteropCodec codec = new SoapInteropCodec();


  @Test
  public void testPollRequest() throws Exception {
    String anXmlBody =
      "<CORUS-IOP:Poll xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
      " commandId=\"675432\" />";
    String aPollRequest = createSoapRequest(anXmlBody);

    Object aResult = codec.deserialize(new ByteArrayInputStream(aPollRequest.getBytes()));
    assertNotNull("The result object should not be null", aResult);
    assertTrue("The result object is not an Envelope",
               aResult instanceof Envelope);

    Envelope anEnvelope = (Envelope) aResult;
    assertRequest(anEnvelope);

    Body aBody = anEnvelope.getBody();
    assertEquals("The size of the object list of the body is invalid", 1,
                 aBody.getObjects().size());
    assertTrue("The object of the body is not a Poll",
               aBody.getObjects().get(0) instanceof Poll);

    Poll aPoll = (Poll) aBody.getObjects().get(0);
    assertEquals("The command id of the poll is invalid", "675432",
                 aPoll.getCommandId());
  }

  @Test
  public void testStatusRequest() throws Exception {
    String anXmlBody =
      "<CORUS-IOP:Status xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
            " commandId=\"675433\">" + "<CORUS-IOP:Context name=\"someContext\">" +
            "<CORUS-IOP:Param name=\"param1\" value=\"param1_value\" />" +
            "<CORUS-IOP:Param name=\"param2\" value=\"param2_value\" />" +
            "</CORUS-IOP:Context>" + "<CORUS-IOP:Context name=\"someOtherContext\">" +
            "<CORUS-IOP:Param name=\"param1\" value=\"param1_value\" />" +
            "<CORUS-IOP:Param name=\"param2\" value=\"param2_value\" />" +
            "</CORUS-IOP:Context>" + "</CORUS-IOP:Status>";
    String aPollRequest = createSoapRequest(anXmlBody);

    Object aResult = codec.deserialize(new ByteArrayInputStream(aPollRequest.getBytes()));
    assertNotNull("The result object should not be null", aResult);
    assertTrue("The result object is not an Envelope",
               aResult instanceof Envelope);

    Envelope anEnvelope = (Envelope) aResult;
    assertRequest(anEnvelope);

    Body aBody = anEnvelope.getBody();
    assertEquals("The size of the object list of the body is invalid", 1,
                 aBody.getObjects().size());
    assertTrue("The object of the body is not a Poll",
               aBody.getObjects().get(0) instanceof Status);

    Status aStatus = (Status) aBody.getObjects().get(0);
    assertEquals("The command id of the status is invalid", "675433",
                 aStatus.getCommandId());
    assertEquals("The list of context has an invalid size", 2,
                 aStatus.getContexts().size());

    ContextMessagePart aContext = (ContextMessagePart) aStatus.getContexts().get(0);
    assertEquals("The name of the context is invalid", "someContext",
                 aContext.getName());
    assertEquals("The list of params has an invalid size", 2,
                 aContext.getParams().size());

    ParamMessagePart anParam = (ParamMessagePart) aContext.getParams().get(0);
    assertEquals("", "param1", anParam.getName());
    assertEquals("", "param1_value", anParam.getValue());
    anParam = (ParamMessagePart) aContext.getParams().get(1);
    assertEquals("", "param2", anParam.getName());
    assertEquals("", "param2_value", anParam.getValue());

    ContextMessagePart anotherContext = (ContextMessagePart) aStatus.getContexts().get(1);
    assertEquals("The name of the context is invalid", "someOtherContext",
                 anotherContext.getName());
    assertEquals("The list of params has an invalid size", 2,
                 anotherContext.getParams().size());

    ParamMessagePart anotherParam = (ParamMessagePart) anotherContext.getParams().get(0);
    assertEquals("", "param1", anotherParam.getName());
    assertEquals("", "param1_value", anotherParam.getValue());
    anotherParam = (ParamMessagePart) aContext.getParams().get(1);
    assertEquals("", "param2", anotherParam.getName());
    assertEquals("", "param2_value", anotherParam.getValue());
  }


  @Test
  public void testRestartRequest() throws Exception {
    String anXmlBody =
            "<CORUS-IOP:Restart xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
            " commandId=\"6754335\" />";
    String aRestartRequest = createSoapRequest(anXmlBody);

    System.out.print(aRestartRequest);
    Object aResult = codec.deserialize(new ByteArrayInputStream(aRestartRequest.getBytes()));
    assertNotNull("The result object should not be null", aResult);
    assertTrue("The result object is not an Envelope",
               aResult instanceof Envelope);

    Envelope anEnvelope = (Envelope) aResult;
    assertRequest(anEnvelope);

    Body aBody = anEnvelope.getBody();
    assertEquals("The size of the object list of the body is invalid", 1,
                 aBody.getObjects().size());
    assertTrue("The object of the body is not a Poll",
               aBody.getObjects().get(0) instanceof Restart);

    Restart aRestart = (Restart) aBody.getObjects().get(0);
    assertEquals("The command id of the restart is invalid", "6754335",
                 aRestart.getCommandId());
  }

  
  @Test
  public void testConfirmShutdownRequest() throws Exception {
    String anXmlBody =
            "<CORUS-IOP:ConfirmShutdown xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
            " commandId=\"675434\" />";
    String aConfirmShutdownRequest = createSoapRequest(anXmlBody);

    Object aResult = codec.deserialize(new ByteArrayInputStream(aConfirmShutdownRequest.getBytes()));
    assertNotNull("The result object should not be null", aResult);
    assertTrue("The result object is not an Envelope",
               aResult instanceof Envelope);

    Envelope anEnvelope = (Envelope) aResult;
    assertRequest(anEnvelope);

    Body aBody = anEnvelope.getBody();
    assertEquals("The size of the object list of the body is invalid", 1,
                 aBody.getObjects().size());
    assertTrue("The object of the body is not a ConfirmShutdown",
               aBody.getObjects().get(0) instanceof ConfirmShutdown);

    ConfirmShutdown aConfirmShutdown = (ConfirmShutdown) aBody.getObjects().get(0);
    assertEquals("The command id of the shutdown request is invalid", "675434",
                 aConfirmShutdown.getCommandId());
  }

  @Test
  public void testAckResponse() throws Exception {
    String anXmlBody =
            "<CORUS-IOP:Ack xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
            " commandId=\"675435\" />";
    String anAckResponse = createSoapResponse(anXmlBody);

    Object aResult = codec.deserialize(new ByteArrayInputStream(anAckResponse.getBytes()));
    assertNotNull("The result object should not be null", aResult);
    assertTrue("The result object is not an Envelope",
               aResult instanceof Envelope);

    Envelope anEnvelope = (Envelope) aResult;
    assertResponse(anEnvelope);

    Body aBody = anEnvelope.getBody();
    assertEquals("The size of the object list of the body is invalid", 1,
                 aBody.getObjects().size());
    assertTrue("The object of the body is not an Ack",
               aBody.getObjects().get(0) instanceof Ack);

    Ack anAck = (Ack) aBody.getObjects().get(0);
    assertEquals("The command id of the ack response is invalid", "675435",
                 anAck.getCommandId());
  }

  @Test
  public void testShutdownResponse() throws Exception {
    String anXmlBody =
            "<CORUS-IOP:Shutdown xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
            " commandId=\"1234\"" +
            " requestor=\"corus.admin\" />";
    String aShutdownResponse = createSoapResponse(anXmlBody);

    Object aResult = codec.deserialize(new ByteArrayInputStream(aShutdownResponse.getBytes()));
    assertNotNull("The result object should not be null", aResult);
    assertTrue("The result object is not an Envelope",
               aResult instanceof Envelope);

    Envelope anEnvelope = (Envelope) aResult;
    assertResponse(anEnvelope);

    Body aBody = anEnvelope.getBody();
    assertEquals("The size of the object list of the body is invalid", 1,
                 aBody.getObjects().size());
    assertTrue("The object of the body is not a Shutdown",
               aBody.getObjects().get(0) instanceof Shutdown);

    Shutdown aShutdown = (Shutdown) aBody.getObjects().get(0);
    assertEquals("The command id of the shutdown response is invalid", "1234",
                 aShutdown.getCommandId());
    assertEquals("The requestor of the shutdown command is invalid",
                 Requestor.ADMIN, aShutdown.getRequestor());
  }
  
  @Test
  public void testProcessEventResponse() throws Exception {
    String anXmlBody =
            "<CORUS-IOP:ProcessEvent xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
            " commandId=\"1234\"" +
            " type=\"InteropDeserializationTest\">" + 
            "   <CORUS-IOP:Param name=\"name1\" value=\"val1\"/>" + 
            "   <CORUS-IOP:Param name=\"name2\" value=\"val2\"/>" + 
            "</CORUS-IOP:ProcessEvent>";
    String processEventResponse = createSoapResponse(anXmlBody);

    Object aResult = codec.deserialize(new ByteArrayInputStream(processEventResponse.getBytes()));
    assertNotNull("The result object should not be null", aResult);
    assertTrue("The result object is not an Envelope",
               aResult instanceof Envelope);

    Envelope anEnvelope = (Envelope) aResult;
    assertResponse(anEnvelope);

    Body aBody = anEnvelope.getBody();
    assertEquals("The size of the object list of the body is invalid", 1,
                 aBody.getObjects().size());
    assertTrue("The object of the body is not a ProcessEvent",
               aBody.getObjects().get(0) instanceof ProcessEvent);

    ProcessEvent event = (ProcessEvent) aBody.getObjects().get(0);
    assertEquals("The command id of the process event response is invalid", "1234",
                 event.getCommandId());
    assertEquals("The type of the process event command is invalid",
                 "InteropDeserializationTest", event.getType());
    
    Map<String, String> params = event.toMap();
    
    assertEquals("val1", params.get("name1"));
    assertEquals("val2", params.get("name2"));
  }
  
  @Test
  public void testConfigurationEvent() throws Exception {
    String anXmlBody =
            "<CORUS-IOP:ConfigurationEvent xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
            " commandId=\"56789\"" +
            " type=\"update\">" + 
            "   <CORUS-IOP:Param name=\"name3\" value=\"val3\"/>" + 
            "   <CORUS-IOP:Param name=\"name4\" value=\"val4\"/>" + 
            "</CORUS-IOP:ConfigurationEvent>";
    String processEventResponse = createSoapResponse(anXmlBody);

    Object aResult = codec.deserialize(new ByteArrayInputStream(processEventResponse.getBytes()));
    assertNotNull("The result object should not be null", aResult);
    assertTrue("The result object is not an Envelope",
               aResult instanceof Envelope);

    Envelope anEnvelope = (Envelope) aResult;
    assertResponse(anEnvelope);

    Body aBody = anEnvelope.getBody();
    assertEquals("The size of the object list of the body is invalid", 1,
                 aBody.getObjects().size());
    assertTrue("The object of the body is not a ConfigurationEvent",
               aBody.getObjects().get(0) instanceof ConfigurationEvent);

    ConfigurationEvent event = (ConfigurationEvent) aBody.getObjects().get(0);
    assertEquals("The command id of the process event response is invalid", "56789",
                 event.getCommandId());
    assertEquals("The type of the process event command is invalid",
                 "update", event.getType());
    
    Map<String, String> params = event.toMap();
    
    assertEquals(2, params.size());
    assertEquals("val3", params.get("name3"));
    assertEquals("val4", params.get("name4"));
  }

  @Test
  public void testSOAPFaultResponse() throws Exception {
    String anXmlBody = "<SOAP-ENV:Fault>" +
                       "<faultcode>... some code ...</faultcode>" +
                       "<faultactor>... some actor ...</faultactor>" +
                       "<faultstring>... some message ...</faultstring>" +
                       "<detail>... some details ...</detail>" +
                       "</SOAP-ENV:Fault>";
    String aFaultResponse = createSoapResponse(anXmlBody);

    Object aResult = codec.deserialize(new ByteArrayInputStream(aFaultResponse.getBytes()));
    assertNotNull("The result object should not be null", aResult);
    assertTrue("The result object is not an Envelope",
               aResult instanceof Envelope);

    Envelope anEnvelope = (Envelope) aResult;
    assertResponse(anEnvelope);

    Body aBody = anEnvelope.getBody();
    assertEquals("The size of the object list of the body is invalid", 1,
                 aBody.getObjects().size());
    assertTrue("The object of the body is not a Fault",
               aBody.getObjects().get(0) instanceof Fault);

    Fault aFault = (Fault) aBody.getObjects().get(0);
    assertEquals("The code of the fault response is invalid",
                 "... some code ...", aFault.getFaultcode());
    assertEquals("The actor of the fault response is invalid",
                 "... some actor ...", aFault.getFaultactor());
    assertEquals("The string of the fault response is invalid",
                 "... some message ...", aFault.getFaultstring());
    assertEquals("The details of the fault response is invalid",
                 "... some details ...", aFault.getDetail());
  }

  private String createSoapRequest(String aBody) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
           "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
           "<SOAP-ENV:Header>" +
           "<CORUS-IOP:Process xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
           " corusPid=\"2045\"" + " requestId=\"134\" />" +
           "</SOAP-ENV:Header>" + "<SOAP-ENV:Body>" + aBody +
           "</SOAP-ENV:Body>" + "</SOAP-ENV:Envelope>";
  }

  private String createSoapResponse(String aBody) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
           "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
           "<SOAP-ENV:Header>" +
           "<CORUS-IOP:Server xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
           " requestId=\"134\"" + " processingTime=\"250\" />" +
           "</SOAP-ENV:Header>" + "<SOAP-ENV:Body>" + aBody +
           "</SOAP-ENV:Body>" + "</SOAP-ENV:Envelope>";
  }

  private void assertRequest(Envelope anEnvelope) {
    assertNotNull("The header of the envelope should not be null",
                  anEnvelope.getHeader());
    assertNotNull("The body of the envelope should not be null",
                  anEnvelope.getBody());

    Header aHeader = anEnvelope.getHeader();
    assertEquals("The size of the object list of the header is invalid", 1,
                 aHeader.getObjects().size());
    assertTrue("The object of the header is not a Process",
               aHeader.getObjects().get(0) instanceof Process);

    Process aProcess = (Process) aHeader.getObjects().get(0);
    assertEquals("The corus pid of the process is invalid", "2045",
                 aProcess.getCorusPid());
    assertEquals("The request id of the process is invalid", "134",
                 aProcess.getRequestId());
  }

  private void assertResponse(Envelope anEnvelope) {
    assertNotNull("The header of the envelope should not be null",
                  anEnvelope.getHeader());
    assertNotNull("The body of the envelope should not be null",
                  anEnvelope.getBody());

    Header aHeader = anEnvelope.getHeader();
    assertEquals("The size of the object list of the header is invalid", 1,
                 aHeader.getObjects().size());
    assertTrue("The object of the header is not a Server",
               aHeader.getObjects().get(0) instanceof Server);

    Server aServer = (Server) aHeader.getObjects().get(0);
    assertEquals("The processing time of the server header is invalid", 250,
                 aServer.getProcessingTime());
    assertEquals("The request id of the server header is invalid", "134",
                 aServer.getRequestId());
  }
}
