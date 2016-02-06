package org.sapia.corus.interop.api.message;

import java.util.List;

public interface StatusMessageCommand extends MessageCommand {

  List<ContextMessagePart> getContexts();

  // --------------------------------------------------------------------------
  // Builder interface

  interface Builder {
    
    Builder commandId(String id);
  
    Builder context(ContextMessagePart context);
    
    StatusMessageCommand build();
  }
}