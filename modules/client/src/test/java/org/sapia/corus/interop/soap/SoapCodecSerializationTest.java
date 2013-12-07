package org.sapia.corus.interop.soap;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Test;
import org.sapia.corus.interop.api.message.ConfigurationEventMessageCommand;
import org.sapia.corus.interop.soap.message.Ack;
import org.sapia.corus.interop.soap.message.ConfigurationEvent;
import org.sapia.corus.interop.soap.message.ConfirmShutdown;
import org.sapia.corus.interop.soap.message.Context;
import org.sapia.corus.interop.soap.message.Param;
import org.sapia.corus.interop.soap.message.Poll;
import org.sapia.corus.interop.soap.message.Process;
import org.sapia.corus.interop.soap.message.ProcessEvent;
import org.sapia.corus.interop.soap.message.Restart;
import org.sapia.corus.interop.soap.message.Server;
import org.sapia.corus.interop.soap.message.Shutdown;
import org.sapia.corus.interop.soap.message.Status;


public class SoapCodecSerializationTest {
  static {
    org.apache.log4j.BasicConfigurator.configure();
  }

  private static SoapInteropCodec _theProcessor = new SoapInteropCodec();

  @Test
  public void testPollRequest() throws Exception {
    // Create the poll command
    Poll aPollCommand = new Poll();
    aPollCommand.setCommandId("675432");

    // Create the SOAP envelope
    Envelope anEnveloppe = createSoapRequest();
    anEnveloppe.getBody().addObject(aPollCommand);

    // Serialize the envelope with the idefix processor
    ByteArrayOutputStream anOutput = new ByteArrayOutputStream();
    _theProcessor.serialize(anEnveloppe, anOutput);

    String aResult = anOutput.toString("UTF-8");

    String aSoapBody =
      "<CORUS-IOP:Poll xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
      " commandId=\"675432\" />";

    assertRequest(aSoapBody, aResult);
  }

  @Test
  public void testStatusRequest() throws Exception {
    // Create the status command
    Status aStatusCommand = new Status();
    aStatusCommand.setCommandId("675433");

    Context aContext = new Context("someContext");
    aContext.addParam(new Param("param1", "param1_value"));
    aContext.addParam(new Param("param2", "param2_value"));
    aStatusCommand.addContext(aContext);

    aContext = new Context("someOtherContext");
    aContext.addParam(new Param("param3", "param3_value"));
    aContext.addParam(new Param("param4", "param4_value"));
    aStatusCommand.addContext(aContext);

    // Create the SOAP envelope
    Envelope anEnveloppe = createSoapRequest();
    anEnveloppe.getBody().addObject(aStatusCommand);

    // Serialize the envelope with the idefix processor
    ByteArrayOutputStream anOutput = new ByteArrayOutputStream();
    _theProcessor.serialize(anEnveloppe, anOutput);

    String aResult = anOutput.toString("UTF-8");

    String aSoapBody =
      "<CORUS-IOP:Status xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
            " commandId=\"675433\">" + "<CORUS-IOP:Context name=\"someContext\">" +
            "<CORUS-IOP:Param name=\"param1\" value=\"param1_value\" />" +
            "<CORUS-IOP:Param name=\"param2\" value=\"param2_value\" />" +
            "</CORUS-IOP:Context>" + "<CORUS-IOP:Context name=\"someOtherContext\">" +
            "<CORUS-IOP:Param name=\"param3\" value=\"param3_value\" />" +
            "<CORUS-IOP:Param name=\"param4\" value=\"param4_value\" />" +
            "</CORUS-IOP:Context>" + "</CORUS-IOP:Status>";

    assertRequest(aSoapBody, aResult);
  }

  @Test
  public void testRestartRequest() throws Exception {
    Restart aRestartCommand = new Restart();
    aRestartCommand.setCommandId("6754335");

    // Create the SOAP envelope
    Envelope anEnveloppe = createSoapRequest();
    anEnveloppe.getBody().addObject(aRestartCommand);

    // Serialize the envelope with the idefix processor
    ByteArrayOutputStream anOutput = new ByteArrayOutputStream();
    _theProcessor.serialize(anEnveloppe, anOutput);

    String aResult = anOutput.toString("UTF-8");

    String aSoapBody =
            "<CORUS-IOP:Restart xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
            " commandId=\"6754335\" />";

    assertRequest(aSoapBody, aResult);
  }

  @Test
  public void testConfirmShutdownRequest() throws Exception {
    ConfirmShutdown aConfirmCommand = new ConfirmShutdown();
    aConfirmCommand.setCommandId("675434");

    // Create the SOAP envelope
    Envelope anEnveloppe = createSoapRequest();
    anEnveloppe.getBody().addObject(aConfirmCommand);

    // Serialize the envelope with the idefix processor
    ByteArrayOutputStream anOutput = new ByteArrayOutputStream();
    _theProcessor.serialize(anEnveloppe, anOutput);

    String aResult = anOutput.toString("UTF-8");

    String aSoapBody =
            "<CORUS-IOP:ConfirmShutdown xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
            " commandId=\"675434\" />";

    assertRequest(aSoapBody, aResult);
  }

  @Test
  public void testAckResponse() throws Exception {
    Ack anAckResponse = new Ack();
    anAckResponse.setCommandId("675435");

    // Create the SOAP envelope
    Envelope anEnveloppe = createSoapResponse();
    anEnveloppe.getBody().addObject(anAckResponse);

    // Serialize the envelope with the idefix processor
    ByteArrayOutputStream anOutput = new ByteArrayOutputStream();
    _theProcessor.serialize(anEnveloppe, anOutput);

    String aResult = anOutput.toString("UTF-8");

    String aSoapBody =
            "<CORUS-IOP:Ack xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
            " commandId=\"675435\" />";

    assertResponse(aSoapBody, aResult);
  }

  @Test
  public void testShutdownResponse() throws Exception {
    Shutdown aShutdownResponse = new Shutdown();
    aShutdownResponse.setCommandId("1234");
    aShutdownResponse.setRequestor("corus.admin");

    // Create the SOAP envelope
    Envelope anEnveloppe = createSoapResponse();
    anEnveloppe.getBody().addObject(aShutdownResponse);

    // Serialize the envelope with the idefix processor
    ByteArrayOutputStream anOutput = new ByteArrayOutputStream();
    _theProcessor.serialize(anEnveloppe, anOutput);

    String aResult = anOutput.toString("UTF-8");

    String aSoapBody =
            "<CORUS-IOP:Shutdown xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
            " requestor=\"corus.admin\"" +
            " commandId=\"1234\" />";

    assertResponse(aSoapBody, aResult);
  }
  
  @Test
  public void testProcessEventResponse() throws Exception {
    ProcessEvent event = new ProcessEvent();
    event.setCommandId("1234");
    event.setType("InteropSerializationTest");
    Param p1 = new Param("name1", "val1");
    event.addParam(p1);
    Param p2 = new Param("name2", "val2");
    event.addParam(p2);

    // Create the SOAP envelope
    Envelope anEnveloppe = createSoapResponse();
    anEnveloppe.getBody().addObject(event);

    // Serialize the envelope with the idefix processor
    ByteArrayOutputStream anOutput = new ByteArrayOutputStream();
    _theProcessor.serialize(anEnveloppe, anOutput);

    String aResult = anOutput.toString("UTF-8");

    String aSoapBody =
        "<CORUS-IOP:ProcessEvent xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
        " type=\"InteropSerializationTest\"" +
        " commandId=\"1234\">" + 
        "<CORUS-IOP:Param name=\"name1\" value=\"val1\" />" + 
        "<CORUS-IOP:Param name=\"name2\" value=\"val2\" />" + 
        "</CORUS-IOP:ProcessEvent>";
    
    assertResponse(aSoapBody, aResult);
  }
  
  @Test
  public void testConfigurationEvent() throws Exception {
    ConfigurationEventMessageCommand event = new ConfigurationEvent.ConfigurationEventBuilder()
        .type(ConfigurationEventMessageCommand.TYPE_UPDATE)
        .commandId("84304880")
        .param("sna", "foo")
        .param("foo2", "dew")
        .build();
    
    // Create the SOAP envelope
    Envelope anEnveloppe = createSoapResponse();
    anEnveloppe.getBody().addObject(event);

    // Serialize the envelope with the idefix processor
    ByteArrayOutputStream anOutput = new ByteArrayOutputStream();
    _theProcessor.serialize(anEnveloppe, anOutput);

    String aResult = anOutput.toString("UTF-8");

    String aSoapBody =
        "<CORUS-IOP:ConfigurationEvent xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
        " type=\"update\"" +
        " commandId=\"84304880\">" + 
        "<CORUS-IOP:Param name=\"sna\" value=\"foo\" />" + 
        "<CORUS-IOP:Param name=\"foo2\" value=\"dew\" />" + 
        "</CORUS-IOP:ConfigurationEvent>";
    
    assertResponse(aSoapBody, aResult);
  }

  @Test
  public void testSOAPFaultResponse() throws Exception {
    Fault aFault = new Fault();
    aFault.setFaultcode("... some code ...");
    aFault.setFaultactor("... some actor ...");
    aFault.setFaultstring("... some message ...");
    aFault.setDetail("... some details ...");

    // Create the SOAP envelope
    Envelope anEnveloppe = createSoapResponse();
    anEnveloppe.getBody().addObject(aFault);

    // Serialize the envelope with the idefix processor
    ByteArrayOutputStream anOutput = new ByteArrayOutputStream();
    _theProcessor.serialize(anEnveloppe, anOutput);

    String aResult = anOutput.toString("UTF-8");

    String aSoapBody = "<SOAP-ENV:Fault>" +
                       "<faultcode>... some code ...</faultcode>" +
                       "<faultactor>... some actor ...</faultactor>" +
                       "<faultstring>... some message ...</faultstring>" +
                       "<detail>... some details ...</detail>" +
                       "</SOAP-ENV:Fault>";

    assertResponse(aSoapBody, aResult);
  }

  private Envelope createSoapRequest() {
    Process aProcess = new Process();
    aProcess.setCorusPid("2045");
    aProcess.setRequestId("134");

    Header aHeader = new Header();
    aHeader.addObject(aProcess);

    Envelope anEnveloppe = new Envelope();
    anEnveloppe.setHeader(aHeader);
    anEnveloppe.setBody(new Body());

    return anEnveloppe;
  }

  private void assertRequest(String aRequest, String aResult) {
    String anExpectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                              "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                              "<SOAP-ENV:Header>" +
                              "<CORUS-IOP:Process xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
                              " corusPid=\"2045\"" + " requestId=\"134\" />" +
                              "</SOAP-ENV:Header>" + "<SOAP-ENV:Body>" +
                              aRequest + "</SOAP-ENV:Body>" +
                              "</SOAP-ENV:Envelope>";

    assertEquals(anExpectedResult, aResult);
  }

  private Envelope createSoapResponse() {
    Server aServer = new Server();
    aServer.setRequestId("134");
    aServer.setProcessingTime(250);

    Header aHeader = new Header();
    aHeader.addObject(aServer);

    Envelope anEnveloppe = new Envelope();
    anEnveloppe.setHeader(aHeader);
    anEnveloppe.setBody(new Body());

    return anEnveloppe;
  }

  private void assertResponse(String aRequest, String aResult) {
    String anExpectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                              "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                              "<SOAP-ENV:Header>" +
                              "<CORUS-IOP:Server xmlns:CORUS-IOP=\"http://schemas.sapia-oss.org/corus/interoperability/\"" +
                              " processingTime=\"250\"" +
                              " requestId=\"134\" />" + "</SOAP-ENV:Header>" +
                              "<SOAP-ENV:Body>" + aRequest +
                              "</SOAP-ENV:Body>" + "</SOAP-ENV:Envelope>";

    assertEquals(anExpectedResult, aResult);
  }
}
