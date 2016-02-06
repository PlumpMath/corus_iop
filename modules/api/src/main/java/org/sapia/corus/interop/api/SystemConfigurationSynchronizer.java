package org.sapia.corus.interop.api;

import org.sapia.corus.interop.api.message.ConfigurationEventMessageCommand;
import org.sapia.corus.interop.api.message.ParamMessagePart;

/**
 * Utility class that provides an easy to automatically synchornize the configuration changes published
 * through {@link ConfigurationEventSoapMessage}s with the system properties of the current JVM.
 * 
 * @author jcdesrochers
 */
public class SystemConfigurationSynchronizer implements ConfigurationChangeListener {

  /* (non-Javadoc)
   * @see org.sapia.corus.interop.api.ConfigurationChangeListener#onConfigurationChange(org.sapia.corus.interop.ConfigurationEvent)
   */
  @Override
  public void onConfigurationChange(ConfigurationEventMessageCommand event) {
    if (ConfigurationEventMessageCommand.TYPE_UPDATE.equals(event.getType())) {
      performConfigUpdate(event.getParams());
    } else if (ConfigurationEventMessageCommand.TYPE_DELETE.equals(event.getType())) {
      performConfigDelete(event.getParams());
    }
  }
  
  /**
   * Internal method that performs system property updates with the passed in configuration.
   *  
   * @param params The {@link SoapParam} to update.
   */
  protected void performConfigUpdate(Iterable<ParamMessagePart> params) {
    for (ParamMessagePart param: params) {
      if (param.getName() != null) {
        System.setProperty(param.getName(), param.getValue());
      }
    }
  }

  /**
   * Internal method that performs system property deletion of the passed in configuration.
   *  
   * @param params The {@link SoapParam} to delete.
   */
  protected void performConfigDelete(Iterable<ParamMessagePart> params) {
    for (ParamMessagePart param: params) {
      if (param.getName() != null) {
        System.clearProperty(param.getName());
      }
    }
  }

}
