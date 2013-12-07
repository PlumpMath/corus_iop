package org.sapia.corus.interop.soap.message;

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
import org.sapia.corus.interop.soap.Fault;

public class SoapInteropMessageBuilderFactory implements InteropMessageBuilderFactory {
  
  @Override
  public AckMessageCommand.Builder newAckMessageBuilder() {
    return new Ack.AckBuilder();
  }
  
  @Override
  public ConfigurationEventMessageCommand.Builder newConfigurationEventMessageBuilder() {
    return new ConfigurationEvent.ConfigurationEventBuilder();
  }
  
  @Override
  public ConfirmShutdownMessageCommand.Builder newConfirmShutdownMessageBuilder() {
    return new ConfirmShutdown.ConfirmShutdownBuilder();
  }
  
  @Override
  public ContextMessagePart.Builder newContextBuilder() {
    return new Context.ContextBuilder();
  }
  
  @Override
  public ParamMessagePart.Builder newParamBuilder() {
    return new Param.ParamBuilder();
  }
  
  @Override
  public PollMessageCommand.Builder newPollMessageBuilder() {
    return new Poll.PollBuilder();
  }
  
  @Override
  public ProcessEventMessageCommand.Builder newProcessEventMessageBuilder() {
    return new ProcessEvent.ProcessEventBuilder();
  }
  
  @Override
  public ProcessMessageHeader.Builder newProcessMessageHeaderBuilder() {
    return new Process.ProcessBuilder();
  }
  
  @Override
  public RestartMessageCommand.Builder newRestartMessageBuilder() {
    return new Restart.RestartBuilder();
  }
  
  @Override
  public ServerMessageHeader.Builder newServerMessageHeaderBuilder() {
    return new Server.ServerBuilder();
  }
  
  @Override
  public ShutdownMessageCommand.Builder newShutdownMessageBuilder() {
    return new Shutdown.ShutdownBuilder();
  }
  
  @Override
  public StatusMessageCommand.Builder newStatusMessageBuilder() {
    return new Status.StatusBuilder();
  }

  @Override
  public Builder newFaultMessageBuilder() {
    return new Fault.FaultBuilder();
  }
}
