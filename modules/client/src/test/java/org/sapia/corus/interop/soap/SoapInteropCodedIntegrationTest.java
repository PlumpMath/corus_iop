package org.sapia.corus.interop.soap;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.helpers.BaseCodecTestSupport;

public class SoapInteropCodedIntegrationTest extends BaseCodecTestSupport {

  public SoapInteropCodedIntegrationTest() {
    super(new SoapInteropCodec());
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
