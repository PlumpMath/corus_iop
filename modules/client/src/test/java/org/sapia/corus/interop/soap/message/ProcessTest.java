package org.sapia.corus.interop.soap.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ProcessMessageHeader;

public class ProcessTest {

  private Process proc;
  
  @Before
  public void setUp() throws Exception {
    proc = new Process();
  }

  @Test
  public void testSetCorusPid() {
    proc.setCorusPid("test");
    assertEquals("test", proc.getCorusPid());
  }

  @Test
  public void testSetRequestId() {
    proc.setRequestId("test");
    assertEquals("test", proc.getRequestId());
  }
 
  @Test
  public void testBuilder() {
    ProcessMessageHeader header = new Process.ProcessBuilder().corusPid("123").requestId("456").build();
    assertEquals("123", header.getCorusPid());
    assertEquals("456", header.getRequestId());
  }
}
