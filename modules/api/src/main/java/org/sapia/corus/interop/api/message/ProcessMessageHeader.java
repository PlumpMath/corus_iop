package org.sapia.corus.interop.api.message;

public interface ProcessMessageHeader extends HeaderMessagePart {

  String getCorusPid();

  // --------------------------------------------------------------------------
  // Builder interface
  
  interface Builder {
    
    Builder requestId(String requestId);
    
    Builder corusPid(String pid);
    
    ProcessMessageHeader build();
    
  }
}