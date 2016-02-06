package org.sapia.corus.interop.api.message;

public interface PollMessageCommand extends MessageCommand {
  
  // --------------------------------------------------------------------------
  // Builder interface
  
  interface Builder {
    
    Builder commandId(String id);
    
    PollMessageCommand build();
    
  }

}