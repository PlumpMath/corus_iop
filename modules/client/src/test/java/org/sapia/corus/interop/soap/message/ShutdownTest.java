package org.sapia.corus.interop.soap.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ShutdownMessageCommand;

public class ShutdownTest {
 
  private Shutdown shutdown;

  @Before
  public void setUp() throws Exception {
    shutdown = new Shutdown();
  }

  @Test
  public void testSetRequestor() {
    shutdown.setRequestor(ShutdownMessageCommand.Requestor.ADMIN.getType());
    assertEquals(ShutdownMessageCommand.Requestor.ADMIN, shutdown.getRequestor());
  }

  @Test
  public void testSetCommandId() {
    shutdown.setCommandId("test");
    assertEquals("test", shutdown.getCommandId());
  }
  
  @Test
  public void testBuilder() {
    ShutdownMessageCommand cmd = new Shutdown.ShutdownBuilder().commandId("test").requestor(ShutdownMessageCommand.Requestor.ADMIN).build();
    assertEquals("test", cmd.getCommandId());
    assertEquals(ShutdownMessageCommand.Requestor.ADMIN, cmd.getRequestor());
  }

}
