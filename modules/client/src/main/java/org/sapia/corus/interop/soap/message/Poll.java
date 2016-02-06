package org.sapia.corus.interop.soap.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.PollMessageCommand;


/**
 * This class represents the <code>Poll</code> Corus interop request. This request is generated
 * by a process managed by a Corus server and it act as a heartbeat sent to the Corus
 * server.
 *
 * @author jcdesrochers
 * @author yduchesne
 */
public class Poll extends Command implements PollMessageCommand {
  
  static final long serialVersionUID = 1L;

  /**
   * Creates a new Poll instance.
   */
  public Poll() {
  }
  
  public static class PollBuilder implements Builder {
    
    private Poll poll = new Poll();

    @Override
    public Builder commandId(String id) {
      poll.setCommandId(id);
      return this;
    }

    @Override
    public PollMessageCommand build() {
      InteropUtils.checkStateNotNullOrBlank(poll.getCommandId(), "Command ID not set on Poll command");
      Poll p = poll;
      poll = null;
      return p;
    }
    
  }
}
