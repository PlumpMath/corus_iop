package org.sapia.corus.interop.api.message;

public interface ServerMessageHeader extends HeaderMessagePart {

  long getProcessingTime();

  // --------------------------------------------------------------------------
  // Builder interface
  
  interface Builder {
    
    Builder requestId(String requestId);
    
    Builder processingTime(long processingTime);
    
    ServerMessageHeader build();
  }
}