package org.sapia.corus.interop.protobuf.message;

import java.util.ArrayList;
import java.util.List;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ContextMessagePart;
import org.sapia.corus.interop.api.message.ParamMessagePart;
import org.sapia.corus.interop.api.message.StatusMessageCommand;
import org.sapia.corus.interop.protobuf.CorusInteroperability;
import org.sapia.corus.interop.protobuf.CorusInteroperability.Command;
import org.sapia.corus.interop.protobuf.CorusInteroperability.Status.Context;
import org.sapia.corus.interop.protobuf.ProtobufEncoderHelper;

public class StatusProtoAdapter implements StatusMessageCommand, EncodableCommand {
  
  private CorusInteroperability.Status delegate;
  
  public StatusProtoAdapter(CorusInteroperability.Status delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public String getCommandId() {
    return delegate.getCommandId();
  }
  
  @Override
  public List<ContextMessagePart> getContexts() {
    List<ContextMessagePart> toReturn = new ArrayList<>(delegate.getContextsCount());
    for (Context c : delegate.getContextsList()) {
      toReturn.add(new ContextMessagePartProtoAdapter(c));
    }
    return toReturn;
  }
  
  public CorusInteroperability.Status getDelegate() {
    return delegate;
  }
  
  @Override
  public Command encode() {
     return ProtobufEncoderHelper.wrapCommand(delegate);
  }

  public static class StatusProtoAdapterBuilder implements Builder {
    
    private CorusInteroperability.Status.Builder delegate = CorusInteroperability.Status.newBuilder();
    
    @Override
    public Builder commandId(String id) {
      delegate.setCommandId(id);
      return this;
    }
    
    @Override
    public Builder context(ContextMessagePart context) {
      if (context instanceof ContextMessagePartProtoAdapter) {
       delegate.addContexts(((ContextMessagePartProtoAdapter) context).getDelegate());
      } else {
        CorusInteroperability.Status.Context.Builder protoContextBuider = 
            CorusInteroperability.Status.Context.newBuilder();
        protoContextBuider.setName(context.getName());
        for (ParamMessagePart p : context.getParams()) {
          protoContextBuider.addParams(CorusInteroperability.Param.newBuilder().setName(p.getName()).setValue(p.getValue()));
        }
        delegate.addContexts(protoContextBuider);
      }
      return this;
    }
    
    @Override
    public StatusMessageCommand build() {
      CorusInteroperability.Status status = delegate.build();
      InteropUtils.checkStateNotNullOrBlank(status.getCommandId(), "Command ID not set on Status command");
      return new StatusProtoAdapter(status);
    }
  }
}
