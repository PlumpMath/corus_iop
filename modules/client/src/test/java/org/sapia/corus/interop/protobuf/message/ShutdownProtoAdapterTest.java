package org.sapia.corus.interop.protobuf.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ShutdownMessageCommand;

public class ShutdownProtoAdapterTest {
  
  private ShutdownMessageCommand cmd;

  @Before
  public void setUp() throws Exception {
    cmd = new ShutdownProtoAdapter.ShutdownProtoAdapterBuilder().commandId("123").requestor(ShutdownMessageCommand.Requestor.ADMIN).build();
  }

  @Test
  public void testGetCommandId() {
    assertEquals("123", cmd.getCommandId());
  }

  @Test
  public void testGetRequestor() {
    assertEquals(ShutdownMessageCommand.Requestor.ADMIN, cmd.getRequestor());
  }

}
