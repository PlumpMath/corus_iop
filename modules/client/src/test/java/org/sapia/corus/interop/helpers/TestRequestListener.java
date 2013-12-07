package org.sapia.corus.interop.helpers;

import java.util.ArrayList;
import java.util.List;

import org.sapia.corus.interop.api.message.ConfirmShutdownMessageCommand;
import org.sapia.corus.interop.api.message.MessageCommand;
import org.sapia.corus.interop.api.message.PollMessageCommand;
import org.sapia.corus.interop.api.message.ProcessMessageHeader;
import org.sapia.corus.interop.api.message.RestartMessageCommand;
import org.sapia.corus.interop.api.message.StatusMessageCommand;


/**
 * @author yduchesne
 */
public class TestRequestListener implements RequestListener {
  boolean confirmShutdown;
  boolean confirmDump;
  boolean poll;
  boolean restart;
  boolean status;

  public void onConfirmShutdown(ProcessMessageHeader proc, ConfirmShutdownMessageCommand confirm) throws Exception {
    confirmShutdown = true;
  }

  public List<MessageCommand> onPoll(ProcessMessageHeader proc, PollMessageCommand poll) throws Exception {
    this.poll = true;

    return new ArrayList<MessageCommand>();
  }

  public void onRestart(ProcessMessageHeader proc, RestartMessageCommand res) throws Exception {
    this.restart = true;
  }

  public List<MessageCommand> onStatus(ProcessMessageHeader proc, StatusMessageCommand stat) throws Exception {
    this.status = true;

    return new ArrayList<MessageCommand>();
  }

}
