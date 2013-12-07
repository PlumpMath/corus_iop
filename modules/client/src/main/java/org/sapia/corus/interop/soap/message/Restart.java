package org.sapia.corus.interop.soap.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.RestartMessageCommand;


/**
 * This class represents the <code>Restart</code> Corus interop request. This request is generated
 * by a process managed by a Corus server when it want's the Corus server to shutdown the current
 * process and to restart a new one with the same arguments.
 *
 * @author jcdesrochers
 * @author yduchesne
 */
public class Restart extends Command implements RestartMessageCommand {
  
  static final long serialVersionUID = 1L;

  /**
   * Creates a new Restart instance.
   */
  public Restart() {
  }
  
  public static final class RestartBuilder implements Builder {
    private Restart restart = new Restart();
    
    @Override
    public Builder commandId(String id) {
      restart.setCommandId(id);
      return this;
    }
    
    @Override
    public RestartMessageCommand build() {
      InteropUtils.checkStateNotNullOrBlank(restart.getCommandId(), "Command ID not set on Restart command");
      Restart r = restart;
      restart = null;
      return r;
    }
  }
}
