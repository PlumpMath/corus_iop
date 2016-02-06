package org.sapia.corus.interop.api.message;

import java.util.Comparator;
import java.util.List;

public interface ContextMessagePart extends Comparator<ParamMessagePart> {

  String getName();

  List<ParamMessagePart> getParams();

  int compare(ParamMessagePart param1, ParamMessagePart param2);
  
  // --------------------------------------------------------------------------
  // Builder interface
  
  interface Builder {
    
    Builder name(String name);
    
    Builder param(String name, String value);

    Builder param(ParamMessagePart param);
    
    ContextMessagePart build();
    
  }

}