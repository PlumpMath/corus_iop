package org.sapia.corus.interop.soap.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ConfigurationEventMessageCommand;
import org.sapia.corus.interop.api.message.ParamMessagePart;

/**
 * Event that captures configuration changes that occured on the server side.
 *  
 * @author jcdesrochers
 * @author yduchesne
 */
public class ConfigurationEvent extends Command implements ConfigurationEventMessageCommand {
  
  private static final long serialVersionUID = -6172931925676401430L;
  
  private List<ParamMessagePart> params = new ArrayList<>();
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
  
  @Override
  public String getType() {
    return type;
  }
  

  public void addParam(ParamMessagePart aParam) {
    params.add(aParam);
  }

  @Override
  public List<ParamMessagePart> getParams() {
    return params;
  }
  
  @Override
  public Map<String, String> toMap() {
    Map<String, String> toReturn = new HashMap<String, String>();
    for (ParamMessagePart p : params) {
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

  public static class ConfigurationEventBuilder implements Builder {
    
    private ConfigurationEvent event;

    public ConfigurationEventBuilder() {
      event = new ConfigurationEvent();
    }

    @Override
    public Builder type(String type) {
      event.setType(type);
      return this;
    }

    @Override
    public Builder commandId(String commandId) {
      event.setCommandId(commandId);
      return this;
    }

    @Override
    public Builder param(String name, String value) {
      event.addParam(new Param(name, value));
      return this;
    }
    
    @Override
    public Builder param(ParamMessagePart param) {
      event.addParam(param);
      return this;
    }
    
    @Override
    public ConfigurationEventMessageCommand build() {
      InteropUtils.checkStateNotNull(event.getCommandId(), "Command ID not set on ConfigurationEvent");
      InteropUtils.checkStateNotNull(event.getType(), "Type not set on ConfigurationEvent");
      InteropUtils.checkStateTrue(
          event.getType().equals(TYPE_DELETE) || event.getType().equals(TYPE_UPDATE), 
          "Type must be either %s or %s",
          TYPE_DELETE, TYPE_UPDATE
      );
      ConfigurationEvent c = event;
      event = null;
      return c;
    }
  }
  
}
