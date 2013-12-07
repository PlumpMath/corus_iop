package org.sapia.corus.interop.protobuf.message;

import java.util.ArrayList;
import java.util.List;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ContextMessagePart;
import org.sapia.corus.interop.api.message.ParamMessagePart;
import org.sapia.corus.interop.protobuf.CorusInteroperability;

public class ContextMessagePartProtoAdapter implements ContextMessagePart {
  
  private CorusInteroperability.Status.Context delegate;
  
  public ContextMessagePartProtoAdapter(CorusInteroperability.Status.Context delegate) {
    this.delegate = delegate;
  }
 
  @Override
  public String getName() {
    return delegate.getName();
  }
  
  @Override
  public List<ParamMessagePart> getParams() {
    List<ParamMessagePart> toReturn = new ArrayList<>();
    for (CorusInteroperability.Param p : delegate.getParamsList()) {
      toReturn.add(new ParamMessagePartProtoAdapter(p));
    }
    return toReturn;
  }
  
  @Override
  public int compare(ParamMessagePart param1, ParamMessagePart param2) {
    return param1.getName().compareTo(param2.getName());
  }
  
  public CorusInteroperability.Status.Context getDelegate() {
    return delegate;
  }
  
  public static class ContextProtoAdapterBuilder implements Builder {
    
    private CorusInteroperability.Status.Context.Builder delegate = CorusInteroperability.Status.Context.newBuilder();
    
    @Override
    public Builder name(String name) {
      delegate.setName(name);
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
    public ContextMessagePart build() {
      CorusInteroperability.Status.Context ctx = delegate.build();
      InteropUtils.checkStateNotNullOrBlank(ctx.getName(), "Name not set on Context message part");
      return new ContextMessagePartProtoAdapter(ctx);
    }
  }

}
