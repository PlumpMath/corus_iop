package org.sapia.corus.interop.protobuf.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ConfirmShutdownMessageCommand;

public class ConfirmShutdownProtoAdapterTest {
  
  private ConfirmShutdownMessageCommand cmd;

  @Before
  public void setUp() throws Exception {
    cmd = new ConfirmShutdownProtoAdapter.ConfirmShutdownProtoAdapterBuilder().commandId("test").build();
  }

  @Test
  public void testGetCommandId() {
    assertEquals("test", cmd.getCommandId());
  }

}
