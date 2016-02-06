package org.sapia.corus.interop.soap.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ProcessEventMessageCommand;

public class ProcessEventTest {
  
  private ProcessEvent evt;

  @Before
  public void setUp() throws Exception {
    evt = new ProcessEvent();
  }

  @Test
  public void testGetType() {
    evt.setType("test");
    assertEquals("test", evt.getType());
  }

  @Test
  public void testAddParams() {
    evt.addParam(new Param("test", "testValue"));
    assertEquals(1, evt.getParams().size());
  }

  @Test
  public void testToMap() {
    evt.addParam(new Param("test", "testValue"));
    assertEquals("testValue", evt.toMap().get("test"));
  }

  @Test
  public void testGetCommandId() {
    evt.setCommandId("test");
    assertEquals("test", evt.getCommandId());
  }
  
  @Test
  public void testBuilder() {
    ProcessEventMessageCommand cmd = new ProcessEvent.ProcessEventBuilder()
        .commandId("123")
        .type("someEvent")
        .param("test", "testValue")
        .build();
    
    assertEquals("123", cmd.getCommandId());
    assertEquals("someEvent", cmd.getType());
  }

}
