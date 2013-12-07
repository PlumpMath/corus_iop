package org.sapia.corus.interop.helpers;

import java.io.IOException;
import java.util.List;

import org.sapia.corus.interop.InteropCodec;
import org.sapia.corus.interop.api.message.MessageCommand;
import org.sapia.corus.interop.api.message.StatusMessageCommand;
import org.sapia.corus.interop.client.FaultException;
import org.sapia.corus.interop.client.InteropProtocol;


/**
 * Implements the {@link InteropProtocol} interface, by extending the {@link ClientStatelessStreamHelper} class.
 * 
 * @author Yanick Duchesne
 */
class MockProtocol extends ClientStatelessStreamHelper {
  
  protected MockStreamListener _listener;

  protected MockProtocol(InteropCodec codec, String corusPid, MockStreamListener listener) {
    super(codec, corusPid);
    _listener = listener;
  }
  
    protected StreamConnection newStreamConnection() throws IOException {
    return new MockStreamConnection(_listener);
  }

  public void confirmShutdown() throws FaultException, IOException {
    super.doSendConfirmShutdown();
  }

  public List<MessageCommand> poll() throws FaultException, IOException {
    return super.doSendPoll();
  }

  public void restart() throws FaultException, IOException {
    super.doSendRestart();
  }

  public List<MessageCommand> sendStatus(StatusMessageCommand stat) throws FaultException, IOException {
    return super.doSendStatus(stat, false);
  }

  public List<MessageCommand> pollAndSendStatus(StatusMessageCommand stat) throws FaultException, IOException {
    return super.doSendStatus(stat, true);
  }

}
