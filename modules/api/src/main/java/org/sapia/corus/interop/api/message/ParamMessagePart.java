package org.sapia.corus.interop.api.message;

public interface ParamMessagePart {

  String getName();

  String getValue();

  // --------------------------------------------------------------------------
  // Builder interface
  
  interface Builder {
    
    Builder name(String name);
  
    Builder value(String value);
    
    ParamMessagePart build();
  }
}