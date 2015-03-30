package org.sapia.corus.interop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Event that captures configuration changes that occured on the server side.
 *  
 * @author jcdesrochers
 */
public class ConfigurationEvent extends AbstractCommand {
  
  private static final long serialVersionUID = -6172931925676401430L;
  
  public static final String TYPE_UPDATE = "update";
  public static final String TYPE_DELETE = "delete";

  /**
   * @return A newly created builder instance.
   */
  public static ConfigurationEventBuilder builder() {
    return new ConfigurationEventBuilder();
  }
  
  private List<Param> params = new ArrayList<>();
  private String      type;

  /**
   * Creates a new {@link ConfigurationEvent} instance. 
   */
  public ConfigurationEvent() {
  }
  
  /**
   * @param aType an event type.
   */
  public void setType(String aType) {
    type = aType;
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
   * @param aParam a {@link Param}
   */
  public void addParam(Param aParam) {
    params.add(aParam);
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
  
  @Override
  public String toString() {
    return "ConfigurationEvent [params=" + params + ", type=" + type + ", commandId=" + super.getCommandId() + "]";
  }



  /**
   * Utility class to build {@link ConfigurationEvent} instances.
   * 
   * @author jcdesrochers
   */
  public static class ConfigurationEventBuilder {
    
    private ConfigurationEvent event;

    protected ConfigurationEventBuilder() {
      event = new ConfigurationEvent();
    }

    /**
     * @param type The type of the configuration event.
     * @return This builder instance.
     */
    public ConfigurationEventBuilder type(String type) {
      event.setType(type);
      return this;
    }

    /**
     * @param commandId The command id of the configuration event.
     * @return This builder instance.
     */
    public ConfigurationEventBuilder commandId(String commandId) {
      event.setCommandId(commandId);
      return this;
    }

    /**
     * @param name The name of the param to add.
     * @param value The value of the param to add
     * @return This builder instance.
     */
    public ConfigurationEventBuilder param(String name, String value) {
      event.addParam(new Param(name, value));
      return this;
    }
    
    /**
     * @param param The param to add.
     * @return This builder instance.
     */
    public ConfigurationEventBuilder param(Param param) {
      event.addParam(param);
      return this;
    }
    
    /**
     * @return The built {@link ConfigurationEvent} instance.
     */
    public ConfigurationEvent build() {
      return event;
    }
  }
  
}
