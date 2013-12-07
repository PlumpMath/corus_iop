package org.sapia.corus.interop.protobuf.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.StatusMessageCommand;

public class StatusProtoAdapterTest {
  
  private StatusMessageCommand cmd;

  @Before
  public void setUp() throws Exception {
    cmd = new StatusProtoAdapter.StatusProtoAdapterBuilder()
        .commandId("test")
        .context(new ContextMessagePartProtoAdapter.ContextProtoAdapterBuilder().name("testContext").build())
        .build();
  }

  @Test
  public void testGetCommandId() {
    assertEquals("test", cmd.getCommandId());
  }

  @Test
  public void testGetContexts() {
    assertEquals(1, cmd.getContexts().size());
    assertEquals("testContext", cmd.getContexts().get(0).getName());
  }

}
