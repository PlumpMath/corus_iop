package org.sapia.corus.interop.protobuf.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ConfirmShutdownMessageCommand;
import org.sapia.corus.interop.protobuf.CorusInteroperability;
import org.sapia.corus.interop.protobuf.CorusInteroperability.Command;
import org.sapia.corus.interop.protobuf.ProtobufEncoderHelper;

public class ConfirmShutdownProtoAdapter implements ConfirmShutdownMessageCommand, EncodableCommand {
  
  private CorusInteroperability.ConfirmShutdown delegate;
  
  public ConfirmShutdownProtoAdapter(CorusInteroperability.ConfirmShutdown delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public String getCommandId() {
    return delegate.getCommandId();
  }
  
  public CorusInteroperability.ConfirmShutdown getDelegate() {
    return delegate;
  }
  
  @Override
  public Command encode() {
    return ProtobufEncoderHelper.wrapCommand(delegate);
  }

  public static class ConfirmShutdownProtoAdapterBuilder implements Builder {
    
    private CorusInteroperability.ConfirmShutdown.Builder delegate = CorusInteroperability.ConfirmShutdown.newBuilder();
    
    @Override
    public Builder commandId(String id) {
      delegate.setCommandId(id);
      return this;
    }
    
    @Override
    public ConfirmShutdownMessageCommand build() {
      InteropUtils.checkStateNotNullOrBlank(delegate.getCommandId(), "Command ID not set on ConfirmShutdown command");
      return new ConfirmShutdownProtoAdapter(delegate.build());
    }
    
  }
}
