package org.sapia.corus.interop.protobuf.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.PollMessageCommand;
import org.sapia.corus.interop.protobuf.CorusInteroperability;
import org.sapia.corus.interop.protobuf.CorusInteroperability.Command;
import org.sapia.corus.interop.protobuf.ProtobufEncoderHelper;

public class PollProtoAdapter implements PollMessageCommand, EncodableCommand {

  private CorusInteroperability.Poll delegate;
  
  public PollProtoAdapter(CorusInteroperability.Poll delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public String getCommandId() {
    return delegate.getCommandId();
  }

  public CorusInteroperability.Poll getDelegate() {
    return delegate;
  }
  
  @Override
  public Command encode() {
    return ProtobufEncoderHelper.wrapCommand(delegate);
  }
  
  public static class PollProtoAdapterBuilder implements Builder {
    
    private CorusInteroperability.Poll.Builder delegate = CorusInteroperability.Poll.newBuilder();
     
    @Override
    public Builder commandId(String id) {
      delegate.setCommandId(id);
      return this;
    }
    
    @Override
    public PollMessageCommand build() {
      CorusInteroperability.Poll poll = delegate.build();
      InteropUtils.checkStateNotNullOrBlank(poll.getCommandId(), "Command ID not set on Poll command");
      return new PollProtoAdapter(poll);
    }
    
  }

}
