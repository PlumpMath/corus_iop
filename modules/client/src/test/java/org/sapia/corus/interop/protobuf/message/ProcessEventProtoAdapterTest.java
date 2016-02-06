package org.sapia.corus.interop.protobuf.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ProcessEventMessageCommand;

public class ProcessEventProtoAdapterTest {
  
  private ProcessEventMessageCommand cmd;

  @Before
  public void setUp() throws Exception {
    cmd = new ProcessEventProtoAdapter.ProcessEventProtoAdapterBuilder()
        .commandId("123")
        .type("testEvent")
        .param("test", "testValue")
        .build();
  }

  @Test
  public void testGetCommandId() {
    assertEquals("123", cmd.getCommandId());
  }

  @Test
  public void testGetType() {
    assertEquals("testEvent", cmd.getType());
  }

  @Test
  public void testToMap() {
    assertEquals("testValue", cmd.toMap().get("test"));
  }

  @Test
  public void testGetParams() {
    assertEquals(1, cmd.getParams().size());
  }

}
