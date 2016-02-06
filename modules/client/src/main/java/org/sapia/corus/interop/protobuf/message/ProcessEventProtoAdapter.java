package org.sapia.corus.interop.protobuf.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ParamMessagePart;
import org.sapia.corus.interop.api.message.ProcessEventMessageCommand;
import org.sapia.corus.interop.protobuf.CorusInteroperability;
import org.sapia.corus.interop.protobuf.CorusInteroperability.Command;
import org.sapia.corus.interop.protobuf.CorusInteroperability.Param;
import org.sapia.corus.interop.protobuf.ProtobufEncoderHelper;

public class ProcessEventProtoAdapter implements ProcessEventMessageCommand, EncodableCommand {
  
  private CorusInteroperability.ProcessEvent delegate;
  
  public ProcessEventProtoAdapter(CorusInteroperability.ProcessEvent delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public String getCommandId() {
    return delegate.getCommandId();
  }
  
  @Override
  public String getType() {
    return delegate.getEventType();
  }
  
  @Override
  public Map<String, String> toMap() {
    Map<String, String> toReturn = new HashMap<>();
    for (Param p : delegate.getParamsList()) {
      toReturn.put(p.getName(), p.getValue());
    }
    return toReturn;
  }

  @Override
  public List<ParamMessagePart> getParams() {
    List<ParamMessagePart> toReturn = new ArrayList<>();
    for (Param p : delegate.getParamsList()) {
      toReturn.add(new ParamMessagePartProtoAdapter(p));
    }
    return toReturn;
  }
  
  public CorusInteroperability.ProcessEvent getDelegate() {
    return delegate;
  }
  
  @Override
  public Command encode() {
    return ProtobufEncoderHelper.wrapCommand(delegate);
  }
  
  public static class ProcessEventProtoAdapterBuilder implements Builder {
    
    private CorusInteroperability.ProcessEvent.Builder delegate = CorusInteroperability.ProcessEvent.newBuilder();
    
    @Override
    public Builder type(String type) {
      delegate.setEventType(type);
      return this;
    }
    
    @Override
    public Builder commandId(String id) {
      delegate.setCommandId(id);
      return this;
    }
    
    @Override
    public Builder param(String name, String value) {
      delegate.addParams(CorusInteroperability.Param.newBuilder().setName(name).setValue(value));
      return this;
    }
    
    @Override
    public Builder param(ParamMessagePart param) {
      delegate.addParams(CorusInteroperability.Param.newBuilder().setName(param.getName()).setValue(param.getValue()));
      return this;
    }
 
    @Override
    public ProcessEventMessageCommand build() {
      CorusInteroperability.ProcessEvent evt = delegate.build();
      InteropUtils.checkStateNotNullOrBlank(evt.getCommandId(), "Command ID not set on ProcessEvent command");
      InteropUtils.checkStateNotNullOrBlank(evt.getEventType(), "Type not set on ProcessEvent command");
      return new ProcessEventProtoAdapter(evt);
    }
    
    
  }
}
