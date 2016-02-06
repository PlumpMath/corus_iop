package org.sapia.corus.interop.protobuf.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.AckMessageCommand;
import org.sapia.corus.interop.protobuf.CorusInteroperability;
import org.sapia.corus.interop.protobuf.CorusInteroperability.Command;
import org.sapia.corus.interop.protobuf.ProtobufEncoderHelper;

public class AckProtoAdapter implements AckMessageCommand, EncodableCommand{
 
  private CorusInteroperability.Ack delegate;
  
  public AckProtoAdapter(CorusInteroperability.Ack delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public String getCommandId() {
    return delegate.getCommandId();
  }
  
  public CorusInteroperability.Ack getDelegate() {
    return delegate;
  }
  
  @Override
  public Command encode() {
    return ProtobufEncoderHelper.wrapCommand(delegate);
  }
  
  public static class AckProtoAdapterBuilder implements Builder {
    
    private CorusInteroperability.Ack.Builder delegate = CorusInteroperability.Ack.newBuilder();
    
    @Override
    public Builder commandId(String id) {
      delegate.setCommandId(id);
      return this;
    }
    
    @Override
    public AckMessageCommand build() {
      InteropUtils.checkStateNotNullOrBlank(delegate.getCommandId(), "Command ID not set on Ack command");
      return new AckProtoAdapter(delegate.build());
    }
  }

}
