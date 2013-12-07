package org.sapia.corus.interop.protobuf.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.FaultMessagePart;

public class FaultProtoAdapterTest {
  
  private FaultMessagePart fault;

  @Before
  public void setUp() throws Exception {
    fault = new FaultProtoAdapter.FaultProtoAdapterBuilder()
        .faultActor("actor")
        .faultCode("code")
        .faultDetail("detail")
        .faultString("message")
        .build();
  }

  @Test
  public void testGetFaultcode() {
    assertEquals("code", fault.getFaultcode());
  }

  @Test
  public void testGetFaultstring() {
    assertEquals("message", fault.getFaultstring());
  }

  @Test
  public void testGetFaultactor() {
    assertEquals("actor", fault.getFaultactor());
  }

  @Test
  public void testGetDetail() {
    assertEquals("detail", fault.getDetail());
  }

}
