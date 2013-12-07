package org.sapia.corus.interop.protobuf.message;

import org.sapia.corus.interop.api.message.AckMessageCommand;
import org.sapia.corus.interop.api.message.ConfigurationEventMessageCommand;
import org.sapia.corus.interop.api.message.ConfirmShutdownMessageCommand;
import org.sapia.corus.interop.api.message.ContextMessagePart;
import org.sapia.corus.interop.api.message.FaultMessagePart.Builder;
import org.sapia.corus.interop.api.message.InteropMessageBuilderFactory;
import org.sapia.corus.interop.api.message.ParamMessagePart;
import org.sapia.corus.interop.api.message.PollMessageCommand;
import org.sapia.corus.interop.api.message.ProcessEventMessageCommand;
import org.sapia.corus.interop.api.message.ProcessMessageHeader;
import org.sapia.corus.interop.api.message.RestartMessageCommand;
import org.sapia.corus.interop.api.message.ServerMessageHeader;
import org.sapia.corus.interop.api.message.ShutdownMessageCommand;
import org.sapia.corus.interop.api.message.StatusMessageCommand;

public class ProtobufInteropMessageBuilderFactory implements InteropMessageBuilderFactory {
  
  
  @Override
  public ParamMessagePart.Builder newParamBuilder() {
    return new ParamMessagePartProtoAdapter.ParamProtobufAdapterBuilder();
  }
  
  @Override
  public ContextMessagePart.Builder newContextBuilder() {
    return new ContextMessagePartProtoAdapter.ContextProtoAdapterBuilder();
  }
  
  @Override
  public AckMessageCommand.Builder newAckMessageBuilder() {
    return new AckProtoAdapter.AckProtoAdapterBuilder();
  }
  
  @Override
  public PollMessageCommand.Builder newPollMessageBuilder() {
    return new PollProtoAdapter.PollProtoAdapterBuilder();
  }
  
  @Override
  public ProcessEventMessageCommand.Builder newProcessEventMessageBuilder() {
    return new ProcessEventProtoAdapter.ProcessEventProtoAdapterBuilder();
  }
  
  @Override
  public ProcessMessageHeader.Builder newProcessMessageHeaderBuilder() {
    return new ProcessMessageHeaderProtoAdapter.ProcessMessageHeaderProtoAdapterBuilder();
  }
  
  @Override
  public RestartMessageCommand.Builder newRestartMessageBuilder() {
    return new RestartProtoAdapter.RestartProtoAdapterBuilder();
  }
  
  @Override
  public ServerMessageHeader.Builder newServerMessageHeaderBuilder() {
    return new ServerMessageHeaderProtoAdapter.ServerMessageHeaderProtoAdapterBuilder();
  }
  
  @Override
  public ShutdownMessageCommand.Builder newShutdownMessageBuilder() {
    return new ShutdownProtoAdapter.ShutdownProtoAdapterBuilder();
  }
  
  @Override
  public StatusMessageCommand.Builder newStatusMessageBuilder() {
    return new StatusProtoAdapter.StatusProtoAdapterBuilder();
  }

  @Override
  public ConfigurationEventMessageCommand.Builder newConfigurationEventMessageBuilder() {
    return new ConfigurationEventProtoAdapter.ConfigurationEventProtoAdapterBuilder();
  }
  
  @Override
  public ConfirmShutdownMessageCommand.Builder newConfirmShutdownMessageBuilder() {
    return new ConfirmShutdownProtoAdapter.ConfirmShutdownProtoAdapterBuilder();
  }
  
  @Override
  public Builder newFaultMessageBuilder() {
    return new FaultProtoAdapter.FaultProtoAdapterBuilder();
  }
  
}
