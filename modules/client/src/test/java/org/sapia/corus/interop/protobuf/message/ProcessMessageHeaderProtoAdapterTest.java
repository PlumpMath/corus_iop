package org.sapia.corus.interop.protobuf.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ProcessMessageHeader;

public class ProcessMessageHeaderProtoAdapterTest {
  
  private ProcessMessageHeader header;

  @Before
  public void setUp() throws Exception {
    header = new ProcessMessageHeaderProtoAdapter.ProcessMessageHeaderProtoAdapterBuilder()
        .corusPid("123")
        .requestId("456")
        .build();
  }

  @Test
  public void testGetCorusPid() {
    assertEquals("123", header.getCorusPid());
  }

  @Test
  public void testGetRequestId() {
    assertEquals("456", header.getRequestId());
  }

}
