package org.sapia.corus.interop.api.message;

import java.util.List;
import java.util.Map;

public interface ProcessEventMessageCommand extends MessageCommand {

  /**
   * @return this instance's event type.
   */
  String getType();

  /**
   * @return this instance's {@link ParamMessagePart} params.
   */
  List<ParamMessagePart> getParams();

  /**
   * @return this instance's {@link ParamMessagePart} bindings, as a {@link Map}.
   */
  Map<String, String> toMap();

  // --------------------------------------------------------------------------
  // Builder interface
  
  interface Builder {
    
    Builder commandId(String id);
    
    Builder type(String type);
    
    Builder param(ParamMessagePart param);
    
    Builder param(String name, String value);
    
    ProcessEventMessageCommand build();
    
  }
}