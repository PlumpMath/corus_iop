package org.sapia.corus.interop.api.message;

public interface RestartMessageCommand extends MessageCommand {
  
  // --------------------------------------------------------------------------
  // Builder interface
  
  interface Builder {
    
    Builder commandId(String id);
    
    public RestartMessageCommand build();
    
  }

}