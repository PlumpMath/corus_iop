package org.sapia.corus.interop.api;

import org.sapia.corus.interop.api.message.ConfigurationEventMessageCommand;

/**
 * An instance of this interface is notified upon new process configuration change (add, update or removal).
 * 
 * @author jcdesrochers
 */
public interface ConfigurationChangeListener {
  
  /**
   * Invoked when a new process configuration change event has been received.
   * Implementations should avoid blocking in this method, and should therefore delegate
   * any time-consuming logic to a separate thread.
   * 
   * @param event a {@link ConfigurationEventSoapMessage}.
   */
  public void onConfigurationChange(ConfigurationEventMessageCommand event);

}
