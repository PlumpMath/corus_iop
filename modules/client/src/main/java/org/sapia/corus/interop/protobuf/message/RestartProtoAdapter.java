package org.sapia.corus.interop.protobuf.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.RestartMessageCommand;
import org.sapia.corus.interop.protobuf.CorusInteroperability;
import org.sapia.corus.interop.protobuf.CorusInteroperability.Command;
import org.sapia.corus.interop.protobuf.ProtobufEncoderHelper;

public class RestartProtoAdapter implements RestartMessageCommand, EncodableCommand {

  private CorusInteroperability.Restart delegate;
  
  public RestartProtoAdapter(CorusInteroperability.Restart delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public String getCommandId() {
    return delegate.getCommandId();
  }
  
  public CorusInteroperability.Restart getDelegate() {
    return delegate;
  }
  
  @Override
  public Command encode() {
    return ProtobufEncoderHelper.wrapCommand(delegate);
  }
  
  public static class RestartProtoAdapterBuilder implements Builder {
   
    private CorusInteroperability.Restart.Builder delegate = CorusInteroperability.Restart.newBuilder();
    
    @Override
    public Builder commandId(String id) {
      delegate.setCommandId(id);
      return this;
    }
    
    @Override
    public RestartMessageCommand build() {
      CorusInteroperability.Restart restart = delegate.build();
      InteropUtils.checkStateNotNullOrBlank(restart.getCommandId(), "Command ID not set on Restart command");
      return new RestartProtoAdapter(restart);
    }
    
  }
}
