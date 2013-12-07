package org.sapia.corus.interop.protobuf;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.helpers.BaseCodecTestSupport;

public class ProtobufInteropCodedIntegrationTest extends BaseCodecTestSupport {

  public ProtobufInteropCodedIntegrationTest() {
    super(new ProtobufInteropCodec());
  }
  
  @Before
  public void setUp() {
    doSetUp();
  }
  
  @Test
  public void testPoll() throws Exception {
    doTestPoll();
  }
  
  @Test
  public void testConfirmShutdown() throws Exception {
    doTestConfirmShutdown();
  }
  
  @Test
  public void testRestart() throws Exception {
    doTestRestart();
  }
  
  @Test
  public void testStatus() throws Exception {
    doTestStatus();
  }
}
