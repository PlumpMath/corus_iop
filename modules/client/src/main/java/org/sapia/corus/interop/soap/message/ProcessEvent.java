package org.sapia.corus.interop.soap.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ParamMessagePart;
import org.sapia.corus.interop.api.message.ProcessEventMessageCommand;


/**
 * Corresponds to a generic process event of a given type.
 * 
 * @author jcdesrochers
 * @author yduchesne
 */
public class ProcessEvent extends Command implements ProcessEventMessageCommand {
  
  private static final long serialVersionUID = -5228178554371418143L;

  /**
   * @return A newly created builder instance.
   */
  public static ProcessEventBuilder builder() {
    return new ProcessEventBuilder();
  }

  private List<ParamMessagePart> params = new ArrayList<>();
  private String      type;

  public ProcessEvent() {
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  @Override
  public String getType() {
    return type;
  }

  public void addParam(ParamMessagePart param) {
    params.add(param);
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

  public static class ProcessEventBuilder implements Builder {
    
    private ProcessEvent event;

    protected ProcessEventBuilder() {
      event = new ProcessEvent();
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
    public ProcessEventBuilder param(ParamMessagePart param) {
      event.addParam(param);
      return this;
    }
 
    @Override
    public ProcessEventMessageCommand build() {
      InteropUtils.checkStateNotNullOrBlank(event.getCommandId(), "Command ID not set on ProcessEvent command");
      InteropUtils.checkStateNotNullOrBlank(event.getType(), "Type not set on ProcessEvent command");
      ProcessEventMessageCommand p = event;
      event = null;
      return p;
    }
  }

}
