package org.sapia.corus.interop.protobuf.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ServerMessageHeader;

public class ServerMessageHeaderProtoAdapterTest {
  
  private ServerMessageHeader header;

  @Before
  public void setUp() throws Exception {
    header = new ServerMessageHeaderProtoAdapter.ServerMessageHeaderProtoAdapterBuilder()
        .requestId("123")
        .processingTime(1000)
        .build();
  }

  @Test
  public void testGetProcessingTime() {
    assertEquals(1000, header.getProcessingTime());
  }

  @Test
  public void testGetRequestId() {
    assertEquals("123", header.getRequestId());
  }

}
