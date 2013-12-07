package org.sapia.corus.interop.soap.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.AckMessageCommand;

/**
 * @author jcdesrochers
 * @author yduchesne
 */
public class Ack extends Command implements AckMessageCommand {
  
  static final long serialVersionUID = 1L;

  /**
   * Creates a new Ack instance.
   */
  public Ack() {
  }
  
  public static class AckBuilder implements AckMessageCommand.Builder {
    
    private Ack ack = new Ack();
    
    @Override
    public Builder commandId(String id) {
      ack.setCommandId(id);
      return this;
    }
    
    @Override
    public AckMessageCommand build() {
      InteropUtils.checkStateNotNullOrBlank(ack.getCommandId(), "Command ID not set on Ack command");
      Ack a = ack;
      ack = null;
      return a;
    }
  }
}

