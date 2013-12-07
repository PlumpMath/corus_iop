package org.sapia.corus.interop.helpers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import org.mockito.ArgumentCaptor;
import org.sapia.corus.interop.InteropCodec;
import org.sapia.corus.interop.api.message.ConfirmShutdownMessageCommand;
import org.sapia.corus.interop.api.message.ContextMessagePart;
import org.sapia.corus.interop.api.message.ParamMessagePart;
import org.sapia.corus.interop.api.message.PollMessageCommand;
import org.sapia.corus.interop.api.message.ProcessMessageHeader;
import org.sapia.corus.interop.api.message.RestartMessageCommand;
import org.sapia.corus.interop.api.message.StatusMessageCommand;
import org.sapia.corus.interop.client.CyclicIdGenerator;

/**
 * Base class for implementing codec tests.
 * 
 * @author yduchesne
 */
public abstract class BaseCodecTestSupport {
  private RequestListener  _listener;
  private MockClientServer _mock;
  
  protected BaseCodecTestSupport(InteropCodec codec) {
    _listener = mock(RequestListener.class);
    _mock = new MockClientServer(codec, _listener);
  }
  
  protected void doSetUp() {
    reset(_listener);
  }

  protected void doTestPoll() throws Exception {
    _mock.poll();
    verify(_listener).onPoll(any(ProcessMessageHeader.class), any(PollMessageCommand.class));
  }

  protected void doTestStatus() throws Exception {
    StatusMessageCommand status = _mock.getMessageBuilderFactory().newStatusMessageBuilder()
      .commandId(CyclicIdGenerator.newCommandId())
      .context(_mock.getMessageBuilderFactory()
          .newContextBuilder().name("testContext").param("p1", "v1").build()
      )
      .build();
    
    _mock.sendStatus(status);
    
    ArgumentCaptor<ProcessMessageHeader> header = ArgumentCaptor.forClass(ProcessMessageHeader.class);
    ArgumentCaptor<StatusMessageCommand> stat = ArgumentCaptor.forClass(StatusMessageCommand.class);
    verify(_listener).onStatus(header.capture(), stat.capture());
    
    StatusMessageCommand receivedStatus = stat.getValue();
    
    assertEquals(1, receivedStatus.getContexts().size());
    ContextMessagePart receivedContext = receivedStatus.getContexts().get(0);
    assertEquals("testContext", receivedContext.getName());
    assertEquals(1, receivedContext.getParams().size());
    ParamMessagePart receiveParam = receivedContext.getParams().get(0);
    assertEquals("p1", receiveParam.getName());
    assertEquals("v1", receiveParam.getValue());
  }

  protected void doTestConfirmShutdown() throws Exception {
    _mock.confirmShutdown();
    
    verify(_listener).onConfirmShutdown(any(ProcessMessageHeader.class), any(ConfirmShutdownMessageCommand.class));
  }

  protected void doTestRestart() throws Exception {
    _mock.restart();
    
    verify(_listener).onRestart(any(ProcessMessageHeader.class), any(RestartMessageCommand.class));
  }

}
