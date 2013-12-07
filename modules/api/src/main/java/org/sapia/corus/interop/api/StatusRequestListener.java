package org.sapia.corus.interop.api;

import org.sapia.corus.interop.api.message.ContextMessagePart;
import org.sapia.corus.interop.api.message.InteropMessageBuilderFactory;
import org.sapia.corus.interop.api.message.StatusMessageCommand;


/**
 * This interface can be implemented by application modules that
 * wish to provided status information to the corus server.
 *
 * @author Yanick Duchesne
 *
 * <dl>
 * <dt><b>Copyright:</b><dd>Copyright &#169; 2002-2003 <a href="http://www.sapia-oss.org">Sapia Open Source Software</a>. All Rights Reserved.</dd></dt>
 * <dt><b>License:</b><dd>Read the license.txt file of the jar or visit the
 *        <a href="http://www.sapia-oss.org/license.html">license page</a> at the Sapia OSS web site</dd></dt>
 * </dl>
 */
public interface StatusRequestListener {
  /**
   * This method is a callback that allows applications to publish
   * status information to their Corus server. Applications add
   * information to the given builder.
   *
   * @param statusBuilder a {@link StatusMessageCommand.Builder} instance.
   * @param factory the {@link InteropMessageBuilderFactory} to use for building the {@link ContextMessagePart}s
   * to add to the status builder.
   */
  public void onStatus(StatusMessageCommand.Builder statusBuilder, InteropMessageBuilderFactory factory);
}
