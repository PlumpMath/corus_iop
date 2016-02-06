package org.sapia.corus.interop.soap.message;


// Import of Sun's JDK classes
// ---------------------------
import java.util.ArrayList;
import java.util.List;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ContextMessagePart;
import org.sapia.corus.interop.api.message.StatusMessageCommand;


/**
 * @author jcdesrochers
 * @author yduchesne
 */
public class Status extends Command implements StatusMessageCommand {
  
  static final long serialVersionUID = 1L;
  
  /** The list of context of this status request. */
  private List<ContextMessagePart> _theContexts;  

  /**
   * Creates a new Status instance.
   */
  public Status() {
    _theContexts = new ArrayList<ContextMessagePart>();
  }

  @Override
  public List<ContextMessagePart> getContexts() {
    return _theContexts;
  }

  public void addContext(ContextMessagePart aContext) {
    _theContexts.add(aContext);
  }

  public String toString() {
    StringBuffer aBuffer = new StringBuffer(super.toString());
    aBuffer.append("[contexts=").append(_theContexts).append("]");

    return aBuffer.toString();
  }
  
  public static class StatusBuilder implements Builder {
    
    private Status status = new Status();
    
    @Override
    public Builder commandId(String id) {
      status.setCommandId(id);
      return this;
    }
    
    @Override
    public Builder context(ContextMessagePart context) {
      status.addContext(context);
      return this;
    }
    
    @Override
    public StatusMessageCommand build() {
      InteropUtils.checkStateNotNullOrBlank(status.getCommandId(), "Command ID not set on Status command");
      StatusMessageCommand s = status;
      status = null;
      return s;
    }
    
  }
}
