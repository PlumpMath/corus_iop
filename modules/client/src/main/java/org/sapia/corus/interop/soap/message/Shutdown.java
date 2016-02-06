package org.sapia.corus.interop.soap.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ShutdownMessageCommand;

/**
 * @author jcdesrochers
 * @author yduchesne
 */
public class Shutdown extends Command implements ShutdownMessageCommand {
  
  static final long serialVersionUID = 1L;
  
  /** Defines the entity that requested the shutdown. */
  private String _theRequestor;

  /**
   * Creates a new Shutdown instance.
   */
  public Shutdown() {
  }

  @Override
  public ShutdownMessageCommand.Requestor getRequestor() {
    return ShutdownMessageCommand.Requestor.fromType(_theRequestor);
  }

  public void setRequestor(String aRequestor) {
    _theRequestor = ShutdownMessageCommand.Requestor.fromType(aRequestor).getType();
  }

  /**
   * Returns a string representation of this shutdown command.
   *
   * @return A string representation of this shutdown command.
   */
  public String toString() {
    StringBuffer aBuffer = new StringBuffer(super.toString());
    aBuffer.append("[requestor=").append(_theRequestor).append("]");

    return aBuffer.toString();
  }
  
  public static class ShutdownBuilder implements Builder {
    
    private Shutdown shutdown = new Shutdown();
    
    @Override
    public Builder commandId(String id) {
      shutdown.setCommandId(id);
      return this;
    }
    
    @Override
    public Builder requestor(ShutdownMessageCommand.Requestor requestor) {
      shutdown.setRequestor(requestor.getType());
      return this;
    }
    
    @Override
    public Shutdown build() {
      InteropUtils.checkStateNotNullOrBlank(shutdown.getCommandId(), "Command ID not set on Shutdown command");
      InteropUtils.checkStateNotNull(shutdown._theRequestor, "Requestor not set on Shutdown command");
      Shutdown s = shutdown;
      shutdown = null;
      return s;
    }
    
  }
  
}
