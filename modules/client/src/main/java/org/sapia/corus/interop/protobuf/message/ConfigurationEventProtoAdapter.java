package org.sapia.corus.interop.protobuf.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ConfigurationEventMessageCommand;
import org.sapia.corus.interop.api.message.ParamMessagePart;
import org.sapia.corus.interop.protobuf.CorusInteroperability;
import org.sapia.corus.interop.protobuf.CorusInteroperability.Command;
import org.sapia.corus.interop.protobuf.ProtobufEncoderHelper;

public class ConfigurationEventProtoAdapter implements ConfigurationEventMessageCommand, EncodableCommand {
  
  private CorusInteroperability.ConfigurationEvent delegate;
  
  public ConfigurationEventProtoAdapter(CorusInteroperability.ConfigurationEvent delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public String getCommandId() {
    return delegate.getCommandId();
  }
  
  @Override
  public List<ParamMessagePart> getParams() {
    List<ParamMessagePart> toReturn = new ArrayList<>(delegate.getParamsCount());
    for (CorusInteroperability.Param p : delegate.getParamsList()) {
      toReturn.add(new ParamMessagePartProtoAdapter(p));
    }
    return toReturn;
  }
  
  @Override
  public Map<String, String> toMap() {
    Map<String, String> toReturn = new HashMap<>();
    for (CorusInteroperability.Param p : delegate.getParamsList()) {
      toReturn.put(p.getName(), p.getValue());
    }
    return toReturn;
  }
  
  @Override
  public String getType() {
    return delegate.getEventType();
  }
  
  public CorusInteroperability.ConfigurationEvent getDelegate() {
    return delegate;
  }
  
  @Override
  public Command encode() {
    return ProtobufEncoderHelper.wrapCommand(delegate);
  }
  
  public static class ConfigurationEventProtoAdapterBuilder implements Builder {
    
    private CorusInteroperability.ConfigurationEvent.Builder delegate = CorusInteroperability.ConfigurationEvent.newBuilder();
    
    @Override
    public Builder commandId(String id) {
      delegate.setCommandId(id);
      return this;
    }
    
    @Override
    public Builder type(String type) {
      delegate.setEventType(type);
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
    public ConfigurationEventMessageCommand build() {
      CorusInteroperability.ConfigurationEvent evt = delegate.build();
      InteropUtils.checkStateNotNullOrBlank(evt.getCommandId(), "Command ID not set on ConfigurationEvent command");
      InteropUtils.checkStateNotNullOrBlank(evt.getEventType(), "Type not set on ConfigurationEvent command");
      return new ConfigurationEventProtoAdapter(evt);
    }
    
  }

}
