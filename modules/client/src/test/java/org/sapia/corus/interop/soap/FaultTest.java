package org.sapia.corus.interop.soap;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.FaultMessagePart;

public class FaultTest {

  private Fault fault;
  
  @Before
  public void setUp() throws Exception {
    fault = new Fault();
  }

  @Test
  public void testSetFaultcode() {
    fault.setFaultcode("code");
    assertEquals("code", fault.getFaultcode());
  }

  @Test
  public void testSetFaultactor() {
    fault.setFaultactor("actor");
    assertEquals("actor", fault.getFaultactor());
  }

  @Test
  public void testSetFaultstring() {
    fault.setFaultstring("message");
    assertEquals("message", fault.getFaultstring());
  }

  @Test
  public void testSetDetail() {
    fault.setDetail("details");
    assertEquals("details", fault.getDetail());
  }

  @Test
  public void testBuilder() {
    FaultMessagePart fault = new Fault.FaultBuilder()
        .faultActor("actor")
        .faultCode("code")
        .faultDetail("details")
        .faultString("message")
        .build();
    
    assertEquals("code", fault.getFaultcode());
    assertEquals("actor", fault.getFaultactor());
    assertEquals("message", fault.getFaultstring());
    assertEquals("details", fault.getDetail());
  }

}
