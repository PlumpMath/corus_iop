package org.sapia.corus.interop.http;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sapia.corus.interop.InteropCodec;
import org.sapia.corus.interop.InteropCodecFactory;
import org.sapia.corus.interop.api.message.MessageCommand;
import org.sapia.corus.interop.api.message.StatusMessageCommand;
import org.sapia.corus.interop.client.FaultException;
import org.sapia.corus.interop.client.InteropClient;
import org.sapia.corus.interop.helpers.ClientStatelessStreamHelper;
import org.sapia.corus.interop.helpers.StreamConnection;



/**
 * Implements the <code>InteropProtocol</code> interface over HTTP. An instance of
 * this class expects the following system properties to be set upon instantiation:
 *
 * <ul>
 *  <li>corus.server.port
 *  <li>corus.server.host
 * </ul>
 *
 * <p>
 * A corus server is expected to be available at:
 * <p>
 * <code>http://<corus.server.host>:<corus.server.port>/interop/&lt;wire_format_name&gt;</code>
 *  
 * @see InteropCodec#getWireFormatName()
 *
 * @author yduchesne
 * @author jcdesrochers
 */
public class HttpProtocol extends ClientStatelessStreamHelper {
  private static final List<MessageCommand> EMPTY_LST  = Collections.unmodifiableList(new ArrayList<MessageCommand>(0));
  private URL _corusUrl;

  /**
   * Internally calls the {@link #HttpProtocol(InteropCodec)} constructor, selecting the proper {@link InteropCodec}
   * instance through the {@link InteropCodecFactory#getBySystemProperty()} method.
   * 
   * @throws MalformedURLException if a connection to Corus could not be made due to an invalid URL.
   */
  public HttpProtocol() throws MalformedURLException {
    this(InteropCodecFactory.getBySystemProperty());
  }
  
  /**
   * @param codec the {@link InteropCodec} to use.
   * @throws MalformedURLException if a connection to Corus could not be made due to an invalid URL.
   */
  public HttpProtocol(InteropCodec codec) throws MalformedURLException {
    super(codec, InteropClient.getInstance().getCorusPid());

    if (InteropClient.getInstance().isDynamic()) {
      int    port = InteropClient.getInstance().getCorusPort();
      String host = InteropClient.getInstance().getCorusHost();

      if (port == InteropClient.UNDEFINED_PORT) {
        throw new IllegalStateException("corus.server.port system property not specified");
      } else if (host == null) {
        throw new IllegalStateException("corus.server.host system property not specified");
      }

      _corusUrl = new URL("http://" + host + ":" + port + "/interop/" + codec.getWireFormat().type());
      _log.warn("HTTP protocol activated; corus server endpoint set to:  " +
                _corusUrl.toString());
    } else {
      _log.warn("JVM was not started dynamically; HTTP protocol will be disabled");
    }
  }

  /**
   * @see org.sapia.corus.interop.client.InteropProtocol#confirmShutdown()
   */
  public void confirmShutdown() throws FaultException, IOException {
    if (_corusUrl == null) {
      return;
    }

    super.doSendConfirmShutdown();
  }

  /**
   * @see org.sapia.corus.interop.client.InteropProtocol#poll()
   */
  public List<MessageCommand> poll() throws FaultException, IOException {
    if (_corusUrl == null) {
      return EMPTY_LST;
    }

    return super.doSendPoll();
  }

  /**
   * @see org.sapia.corus.interop.client.InteropProtocol#restart()
   */
  public void restart() throws FaultException, IOException {
    if (_corusUrl == null) {
      return;
    }

    super.doSendRestart();
  }

  @Override
  public List<MessageCommand> sendStatus(StatusMessageCommand stat) throws FaultException, IOException {
    if (_corusUrl == null) {
      return EMPTY_LST;
    }

    return super.doSendStatus(stat, false);
  }

  @Override
  public List<MessageCommand> pollAndSendStatus(StatusMessageCommand stat) throws FaultException, IOException {
    if (_corusUrl == null) {
      return EMPTY_LST;
    }

    return super.doSendStatus(stat, true);
  }
  
  /**
   * @see ClientStatelessStreamHelper#newStreamConnection()
   */
  protected StreamConnection newStreamConnection() throws IOException {
    if (_corusUrl == null) {
      throw new IOException("corus server URL not specified; JVM was probably not started dynamically");
    }

    return new HttpURLStreamConnection(_corusUrl);
  }
}
