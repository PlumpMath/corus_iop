package org.sapia.corus.interop.soap.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ConfirmShutdownMessageCommand;

public class ConfirmShutdownTest {
  
  private ConfirmShutdown cfrm;

  @Before
  public void setUp() throws Exception {
    cfrm = new ConfirmShutdown();
  }

  @Test
  public void testSetCommandId() {
    cfrm.setCommandId("test");
    assertEquals("test", cfrm.getCommandId());
  }
  
  @Test
  public void testBuilder() {
    ConfirmShutdownMessageCommand cmd = new ConfirmShutdown.ConfirmShutdownBuilder()
        .commandId("test")
        .build();
    
    assertEquals("test", cmd.getCommandId());
  }

}
