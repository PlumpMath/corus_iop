package org.sapia.corus.interop.client;

import org.sapia.corus.interop.api.StatusRequestListener;
import org.sapia.corus.interop.api.message.ContextMessagePart;
import org.sapia.corus.interop.api.message.InteropMessageBuilderFactory;
import org.sapia.corus.interop.api.message.StatusMessageCommand.Builder;


/**
 * @author Yanick Duchesne
 *
 * <dl>
 * <dt><b>Copyright:</b><dd>Copyright &#169; 2002-2003 <a href="http://www.sapia-oss.org">Sapia Open Source Software</a>. All Rights Reserved.</dd></dt>
 * <dt><b>License:</b><dd>Read the license.txt file of the jar or visit the
 *        <a href="http://www.sapia-oss.org/license.html">license page</a> at the Sapia OSS web site</dd></dt>
 * </dl>
 */
public class TestStatusListener implements StatusRequestListener {
  boolean called;

  @Override
  public void onStatus(Builder statusBuilder, InteropMessageBuilderFactory factory) {
    ContextMessagePart c = factory.newContextBuilder().name("TestContext").param("testName", "testValue").build();
    statusBuilder.context(c);
    called = true;
  }
}
