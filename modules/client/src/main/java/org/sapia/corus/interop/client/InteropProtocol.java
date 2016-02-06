package org.sapia.corus.interop.client;

import java.io.IOException;
import java.util.List;

import org.sapia.corus.interop.api.message.InteropMessageBuilderFactory;
import org.sapia.corus.interop.api.message.MessageCommand;
import org.sapia.corus.interop.api.message.StatusMessageCommand;


/**
 * This interface specifies a facade intended to hide the details of the
 * communication between a dynamic VM and its corus server.
 *
 * @author Yanick Duchesne
 * @author Jean-Cedric Desrochers
 *
 * <dl>
 * <dt><b>Copyright:</b><dd>Copyright &#169; 2002-2003 <a href="http://www.sapia-oss.org">Sapia Open Source Software</a>. All Rights Reserved.</dd></dt>
 * <dt><b>License:</b><dd>Read the license.txt file of the jar or visit the
 *        <a href="http://www.sapia-oss.org/license.html">license page</a> at the Sapia OSS web site</dd></dt>
 * </dl>
 */
public interface InteropProtocol {
  
  /**
   * @return the {@link InteropMessageBuilderFactory} to use for building Interop messages.
   */
  public InteropMessageBuilderFactory getMessageBuilderFactory();
  
  /**
   * Sets this instance's <code>Log</code>.
   *
   * @param log a  <code>Log</code> instance.
   */
  public void setLog(Log log);

  /**
   * Polls the corus server to which this instance connects.
   *
   * @return A {@link List} of {@link MessageCommand}s returned by the Corus server.
   * @throws FaultException if the Corus server a generated a SOAP fault.
   * @throws IOException if a problem occurs while internally sending the request to
   * the Corus server.
   */
  public List<MessageCommand> poll() throws FaultException, IOException;

  /**
   * Sends the given status to the corus server to which this instance connects.
   *
   * @param stat A {@link StatusMessageCommand} object.
   * @return A {@link List} of {@link MessageCommand}s returned by the Corus server.
   * @throws FaultException if the Corus server a generated a SOAP fault.
   * @throws IOException if a problem occurs while internally sending the request to
   * the Corus server.
   */
  public List<MessageCommand> sendStatus(StatusMessageCommand stat) throws FaultException, IOException;

  /**
   * Polls the corus server and sends the given status to the corus
   * server to which this instance connects.
   *
   * @param stat A {@link StatusMessageCommand} object.
   * @return A {@link List} of {@link MessageCommand}s returned by the Corus server.
   * @throws FaultException if the Corus server a generated a SOAP fault.
   * @throws IOException if a problem occurs while internally sending the request to
   * the Corus server.
   */
  public List<MessageCommand> pollAndSendStatus(StatusMessageCommand stat) throws FaultException, IOException;

  /**
   * Sends a restart request to the Corus server.
   *
   * @throws FaultException if the Corus server a generated a SOAP fault.
   * @throws IOException if a problem occurs while internally sending the request to
   * the Corus server.
   */
  public void restart() throws FaultException, IOException;

  /**
   * Sends a shutdown confirmation to the Corus server.
   *
   * @throws FaultException if the Corus server a generated a SOAP fault.
   * @throws IOException if a problem occurs while internally sending the request to
   * the Corus server.
   */
  public void confirmShutdown() throws FaultException, IOException;
  
}
