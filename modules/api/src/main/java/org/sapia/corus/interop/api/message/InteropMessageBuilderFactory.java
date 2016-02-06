package org.sapia.corus.interop.api.message;

public interface InteropMessageBuilderFactory {
  
  public AckMessageCommand.Builder newAckMessageBuilder();
  
  public ConfigurationEventMessageCommand.Builder newConfigurationEventMessageBuilder();
  
  public ConfirmShutdownMessageCommand.Builder newConfirmShutdownMessageBuilder();
  
  public PollMessageCommand.Builder newPollMessageBuilder();
  
  public ProcessEventMessageCommand.Builder newProcessEventMessageBuilder();
  
  public ProcessMessageHeader.Builder newProcessMessageHeaderBuilder();
  
  public RestartMessageCommand.Builder newRestartMessageBuilder();
  
  public ServerMessageHeader.Builder newServerMessageHeaderBuilder();
  
  public ShutdownMessageCommand.Builder newShutdownMessageBuilder();
  
  public StatusMessageCommand.Builder newStatusMessageBuilder();
  
  public ContextMessagePart.Builder newContextBuilder();
  
  public ParamMessagePart.Builder newParamBuilder();
  
  public FaultMessagePart.Builder newFaultMessageBuilder();
}
