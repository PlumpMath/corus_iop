package org.sapia.corus.interop.soap.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.PollMessageCommand;

public class PollTest {
  
  private Poll poll;

  @Before
  public void setUp() throws Exception {
    poll = new Poll();
  }

  @Test
  public void testSetCommandId() {
    poll.setCommandId("test");
    assertEquals("test", poll.getCommandId());
  }
  
  @Test
  public void testBuilder() {
    PollMessageCommand cmd = new Poll.PollBuilder().commandId("test").build();
    assertEquals("test", cmd.getCommandId());
  }

}
