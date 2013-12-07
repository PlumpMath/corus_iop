package org.sapia.corus.interop.api.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Holds the different parts of a message.
 * 
 * @author yduchesne
 */
public class Message {
   
 private static final int DEFAULT_CAPACITY = 3;
  
  private HeaderMessagePart    header;
  private List<MessageCommand> commands = new ArrayList<>(DEFAULT_CAPACITY);
  private FaultMessagePart     error;
  
  private Message() {
  }
  
  public HeaderMessagePart getHeader() {
    return header;
  }
  
  public List<MessageCommand> getCommands() {
    return Collections.unmodifiableList(commands);
  }
  
  public FaultMessagePart getError() {
    return error;
  }
  
  // --------------------------------------------------------------------------
  // Builder
 
  public static final class Builder {

    private Message msg = new Message();
    
    public Builder header(HeaderMessagePart header) {
      msg.header = header;
      return this;
    }
    
    public Builder command(MessageCommand...commands) {
      msg.commands.addAll(Arrays.asList(commands));
      return this;
    }
    
    public Builder commands(Collection<MessageCommand> commands) {
      msg.commands.addAll(commands);
      return this;
    }
    
    public Builder error(FaultMessagePart error) {
      msg.error = error;
      return this;
    }
    
    public Message build() {
      Message m = msg;
      msg = null;
      return m;
    }
    
    public static final Builder newInstance() {
      return new Builder();
    }
    
  }
}
