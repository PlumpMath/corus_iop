package org.sapia.corus.interop.soap.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ServerMessageHeader;

public class ServerTest {

  private Server server;
  
  @Before
  public void setUp() throws Exception {
    server = new Server();
  }

  @Test
  public void testSetProcessingTime() {
    server.setProcessingTime(1000);
    assertEquals(1000, server.getProcessingTime());
  }

  @Test
  public void testSetRequestId() {
    server.setRequestId("123");
    assertEquals("123", server.getRequestId());
  }
  
  @Test
  public void testBuilder() {
    ServerMessageHeader header = new Server.ServerBuilder().requestId("123").processingTime(1000).build();
    assertEquals("123", header.getRequestId());
    assertEquals(1000, header.getProcessingTime());
  }
}
