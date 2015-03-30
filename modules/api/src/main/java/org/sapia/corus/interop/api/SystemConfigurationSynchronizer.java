package org.sapia.corus.interop.api;

import org.sapia.corus.interop.ConfigurationEvent;
import org.sapia.corus.interop.Param;

/**
 * Utility class that provides an easy to automatically synchornize the configuration changes published
 * through {@link ConfigurationEvent}s with the system properties of the current JVM.
 * 
 * @author jcdesrochers
 */
public class SystemConfigurationSynchronizer implements ConfigurationChangeListener {

  /* (non-Javadoc)
   * @see org.sapia.corus.interop.api.ConfigurationChangeListener#onConfigurationChange(org.sapia.corus.interop.ConfigurationEvent)
   */
  @Override
  public void onConfigurationChange(ConfigurationEvent event) {
    if (ConfigurationEvent.TYPE_UPDATE.equals(event.getType())) {
      performConfigUpdate(event.getParams());
    } else if (ConfigurationEvent.TYPE_DELETE.equals(event.getType())) {
      performConfigDelete(event.getParams());
    }
  }
  
  /**
   * Internal method that performs system property updates with the passed in configuration.
   *  
   * @param params The {@link Param} to update.
   */
  protected void performConfigUpdate(Iterable<Param> params) {
    for (Param param: params) {
      if (param.getName() != null) {
        System.setProperty(param.getName(), param.getValue());
      }
    }
  }

  /**
   * Internal method that performs system property deletion of the passed in configuration.
   *  
   * @param params The {@link Param} to delete.
   */
  protected void performConfigDelete(Iterable<Param> params) {
    for (Param param: params) {
      if (param.getName() != null) {
        System.clearProperty(param.getName());
      }
    }
  }

}
