package org.sapia.corus.interop.api.message;

public interface AckMessageCommand extends MessageCommand {

  // --------------------------------------------------------------------------
  // Builder interface
  
  interface Builder {
    
    Builder commandId(String id);
    
    AckMessageCommand build();
  
  }
 
}