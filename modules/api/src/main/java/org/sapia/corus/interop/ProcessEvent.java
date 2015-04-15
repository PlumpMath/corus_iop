package org.sapia.corus.interop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Corresponds to a generic process event of a given type.
 * 
 * @author jcdesrochers
 */
public class ProcessEvent extends AbstractCommand {
  
  private static final long serialVersionUID = -5228178554371418143L;

  /**
   * @return A newly created builder instance.
   */
  public static ProcessEventBuilder builder() {
    return new ProcessEventBuilder();
  }

  private List<Param> params = new ArrayList<>();
  private String      type;

  public ProcessEvent() {
  }
  
  /**
   * @param type an event type.
   */
  public void setType(String type) {
    this.type = type;
  }
  
  /**
   * @return this instance's event type.
   */
  public String getType() {
    return type;
  }
  
  /**
   * Adds a param to this instance.
   * 
   * @param param a {@link Param}
   */
  public void addParam(Param param) {
    params.add(param);
  }
   
  /**
   * @return this instance's {@link Param} params.
   */
  public List<Param> getParams() {
    return params;
  }
  
  /**
   * @return this instance's {@link Param} bindings, as a {@link Map}.
   */
  public Map<String, String> toMap() {
    Map<String, String> toReturn = new HashMap<String, String>();
    for (Param p : params) {
      if (p.getName() != null && p.getValue() != null) {
        toReturn.put(p.getName(), p.getValue());
      }
    }
    return toReturn;
  }


  
  /**
   * Utility class to build {@link ProcessEvent} instances.
   * 
   * @author jcdesrochers
   */
  public static class ProcessEventBuilder {
    
    private ProcessEvent event;

    protected ProcessEventBuilder() {
      event = new ProcessEvent();
    }

    /**
     * @param type The type of the process event.
     * @return This builder instance.
     */
    public ProcessEventBuilder type(String type) {
      event.setType(type);
      return this;
    }

    /**
     * @param commandId The command id of the process event.
     * @return This builder instance.
     */
    public ProcessEventBuilder commandId(String commandId) {
      event.setCommandId(commandId);
      return this;
    }

    /**
     * @param name The name of the param to add.
     * @param value The value of the param to add
     * @return This builder instance.
     */
    public ProcessEventBuilder param(String name, String value) {
      event.addParam(new Param(name, value));
      return this;
    }
    
    /**
     * @param param The param to add.
     * @return This builder instance.
     */
    public ProcessEventBuilder param(Param param) {
      event.addParam(param);
      return this;
    }
    
    /**
     * @return The built {@link ProcessEvent} instance.
     */
    public ProcessEvent build() {
      return event;
    }
  }

}
