package org.sapia.corus.interop.api.message;

public interface ConfirmShutdownMessageCommand extends MessageCommand {

  // --------------------------------------------------------------------------
  // Builder interface
  
  interface Builder {
    
    Builder commandId(String id);
    
    ConfirmShutdownMessageCommand build();
 
  }
}