package org.sapia.corus.interop.protobuf.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ShutdownMessageCommand;
import org.sapia.corus.interop.protobuf.CorusInteroperability;
import org.sapia.corus.interop.protobuf.CorusInteroperability.Command;
import org.sapia.corus.interop.protobuf.ProtobufEncoderHelper;

public class ShutdownProtoAdapter implements ShutdownMessageCommand, EncodableCommand {
  
  private CorusInteroperability.Shutdown delegate;
  
  public ShutdownProtoAdapter(CorusInteroperability.Shutdown delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public String getCommandId() {
    return delegate.getCommandId();
  }
  
  @Override
  public Requestor getRequestor() {
    return Requestor.valueOf(delegate.getRequestor().name());
  }
  
  public CorusInteroperability.Shutdown getDelegate() {
    return delegate;
  }
  
  @Override
  public Command encode() {
    return ProtobufEncoderHelper.wrapCommand(delegate);
  }
  
  public static class ShutdownProtoAdapterBuilder implements Builder {
    
    private CorusInteroperability.Shutdown.Builder delegate = CorusInteroperability.Shutdown.newBuilder();
    
    @Override
    public Builder commandId(String id) {
      delegate.setCommandId(id);
      return this;
    }
    
    @Override
    public Builder requestor(ShutdownMessageCommand.Requestor requestor) {
      delegate.setRequestor(CorusInteroperability.Shutdown.RequestorActor.valueOf(requestor.name()));
      return this;
    }
    
    @Override
    public ShutdownMessageCommand build() {
      CorusInteroperability.Shutdown shutdown = delegate.build();
      InteropUtils.checkStateNotNullOrBlank(shutdown.getCommandId(), "Command ID not set on Shutdown command");
      InteropUtils.checkStateNotNull(shutdown.getRequestor(), "Requestor not set on Shutdown command");
      return new ShutdownProtoAdapter(shutdown);
    }
    
  }

}
