package org.sapia.corus.interop.helpers;

import java.util.List;

import org.sapia.corus.interop.api.message.ConfirmShutdownMessageCommand;
import org.sapia.corus.interop.api.message.MessageCommand;
import org.sapia.corus.interop.api.message.PollMessageCommand;
import org.sapia.corus.interop.api.message.ProcessMessageHeader;
import org.sapia.corus.interop.api.message.RestartMessageCommand;
import org.sapia.corus.interop.api.message.StatusMessageCommand;
import org.sapia.corus.interop.protobuf.CorusInteroperability.Status;


/**
 * This insterface can conveniently implemented by servers that handle
 * Corus's Interoperability Protocol. The interface specifies callbacks that
 * are called for each possible request that can be send by Corus clients.
 *
 * @see ServerStatelessStreamHelper
 *
 * @author yduchesne
 */
public interface RequestListener {
  
  /**
   * Called when a dynamic process confirms that it has proceeded to its own shutdown.
   *
   * @param proc a {@link ProcessMessageHeader} object, encapsulating the corus process ID of the request's originator, and
   * a request identifier.
   * @param confirm a {@link ConfirmShutdownMessageCommand} instance.
   * @throws Exception if an error occurs when processing the given command.
   */
  public void onConfirmShutdown(ProcessMessageHeader proc, ConfirmShutdownMessageCommand confirm) throws Exception;

  /**
   * Called when a dynamic process notifies its corus server about its status.
   *
   * @param proc a {@link ProcessMessageHeader} object, encapsulating the corus process ID of the request's originator, and
   * a request identifier.
   * @param stat a {@link Status} instance.
   * @throws Exception if an error occurs when processing the given command.
   * @return the {@link List} of commands that were pending in the process queue, within the
   * Corus server.
   */
  public List<MessageCommand> onStatus(ProcessMessageHeader proc, StatusMessageCommand stat) throws Exception;

  /**
   * Called when a dynamic process polls its corus server.
   *
   * @param proc a {@link ProcessMessageHeader} object, encapsulating the corus process ID of the request's originator, and
   * a request identifier.
   * @param poll a {@link PollMessageCommand} instance.
   * @throws Exception if an error occurs when processing the given command.
   * @return the {@link List} of commands that were pending in the process queue, within the
   * Corus server.
   */
  public List<MessageCommand> onPoll(ProcessMessageHeader proc, PollMessageCommand poll) throws Exception;

  /**
   * Called when a dynamic process notifies its corus server that it wishes to be restarted.
   *
   * @param proc a {@link ProcessMessageHeader} object, encapsulating the corus process ID of the request's originator, and
   * a request identifier.
   * @param res a {@link RestartMessageCommand} instance.
   * @throws Exception if an error occurs when processing the given command.
   */
  public void onRestart(ProcessMessageHeader proc, RestartMessageCommand res) throws Exception;
  
}
