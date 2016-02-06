package org.sapia.corus.interop.soap.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ConfirmShutdownMessageCommand;


/**
 * @author jcdesrochers
 * @author yduchesne
 */
public class ConfirmShutdown extends Command implements ConfirmShutdownMessageCommand {
  
  static final long serialVersionUID = 1L;

  /**
   * Creates a new ConfirmShutdown instance.
   */
  public ConfirmShutdown() {
  }
  
  public static class ConfirmShutdownBuilder implements Builder {
    
    private ConfirmShutdown command = new ConfirmShutdown();
    
    @Override
    public Builder commandId(String id) {
      command.setCommandId(id);
      return this;
    }
    
    @Override
    public ConfirmShutdownMessageCommand build() {
      InteropUtils.checkStateNotNullOrBlank(command.getCommandId(), "Command ID not set on ConfirmShutdown command");
      ConfirmShutdown c = command;
      command = null;
      return c;
    }
  }
}
