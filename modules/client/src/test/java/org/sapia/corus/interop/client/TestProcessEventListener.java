package org.sapia.corus.interop.client;

import org.sapia.corus.interop.api.ProcessEventListener;
import org.sapia.corus.interop.api.message.ProcessEventMessageCommand;

public class TestProcessEventListener implements ProcessEventListener {
  
  boolean called;
  
  @Override
  public void onProcessEvent(ProcessEventMessageCommand evt) {
    this.called = true;
  }

}
