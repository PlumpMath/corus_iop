package org.sapia.corus.interop.soap.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.StatusMessageCommand;

public class StatusTest {

  private Status status;
  
  @Before
  public void setUp() throws Exception {
    status = new Status();
  }

  @Test
  public void testAddContext() {
    Context c = new Context("test");
    status.addContext(c);
    assertEquals(1, status.getContexts().size());
  }

  @Test
  public void testSetCommandId() {
    status.setCommandId("test");
    assertEquals("test", status.getCommandId());
  }
  
  @Test
  public void testBuilder() {
    StatusMessageCommand cmd = new Status.StatusBuilder()
        .commandId("test")
        .context(new Context.ContextBuilder().name("testContext").build())
        .build();
    
    assertEquals("test", cmd.getCommandId());
    assertEquals(1, cmd.getContexts().size());
  }

}
