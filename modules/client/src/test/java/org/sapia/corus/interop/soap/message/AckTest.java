package org.sapia.corus.interop.soap.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.AckMessageCommand;

public class AckTest {
  
  private Ack ack;

  @Before
  public void setUp() throws Exception {
    ack = new Ack();
  }

  @Test
  public void testGetCommandId() {
    ack.setCommandId("test");
    assertEquals("test", ack.getCommandId());
  }
  
  @Test
  public void testBuilder() {
    AckMessageCommand cmd = new Ack.AckBuilder().commandId("test").build();
    assertEquals("test", cmd.getCommandId());
  }

}
