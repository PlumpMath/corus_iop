package org.sapia.corus.interop.protobuf.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ParamMessagePart;
import org.sapia.corus.interop.protobuf.CorusInteroperability;

public class ParamMessagePartProtoAdapter implements ParamMessagePart {
  
  private CorusInteroperability.Param delegate;
  
  public ParamMessagePartProtoAdapter(CorusInteroperability.Param delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public String getName() {
    return delegate.getName();
  }
  
  @Override
  public String getValue() {
    return delegate.getValue();
  }

  public CorusInteroperability.Param getDelegate() {
    return delegate;
  }
  
  public static class ParamProtobufAdapterBuilder implements Builder {
    
    private CorusInteroperability.Param.Builder delegate = CorusInteroperability.Param.newBuilder();
    
    @Override
    public Builder name(String name) {
      delegate.setName(name);
      return this;
    }
    
    @Override
    public Builder value(String value) {
      delegate.setValue(value);
      return this;
    }
    
    @Override
    public ParamMessagePart build() {
      CorusInteroperability.Param param = delegate.build();
      InteropUtils.checkStateNotNullOrBlank(param.getName(), "Name not set on Param message part");
      InteropUtils.checkStateNotNull(param.getValue(), "Value not set on Param message part");
      return new ParamMessagePartProtoAdapter(delegate.build());
    }
  }

}
