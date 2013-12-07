package org.sapia.corus.interop.soap.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.RestartMessageCommand;

public class RestartTest {

  private Restart restart;
  
  @Before
  public void setUp() throws Exception {
    restart = new Restart();
  }

  @Test
  public void testSetCommandId() {
    restart.setCommandId("test");
    assertEquals("test", restart.getCommandId());
  }
  
  @Test
  public void testBuilder() {
    RestartMessageCommand cmd = new Restart.RestartBuilder().commandId("test").build();
    assertEquals("test", cmd.getCommandId());
  }

}
