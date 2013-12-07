package org.sapia.corus.interop.protobuf.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.AckMessageCommand;

public class AckProtoAdapterTest {
  
  private AckMessageCommand cmd;

  @Before
  public void setUp() throws Exception {
    cmd = new AckProtoAdapter.AckProtoAdapterBuilder().commandId("123").build();
  }

  @Test
  public void testBuilder() {
    assertEquals("123", cmd.getCommandId());
  }

}
