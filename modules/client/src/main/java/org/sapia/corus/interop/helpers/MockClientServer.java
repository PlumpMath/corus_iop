package org.sapia.corus.interop.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.sapia.corus.interop.InteropCodec;
import org.sapia.corus.interop.client.InteropProtocol;

/**
 * Use an instance of this class to simulate client-server interaction.
 * This class implements the {@link InteropProtocol} insterface and allows
 * passing in a {@link RequestListener} that simulates the server.
 * <p>
 * To perform tests, implement your own {@link RequestListener}, and use
 * the client methods (from the {@link InteropProtocol} interface) to send
 * requests to the "server".
 * <p>
 * <pre>
 * InteropProtocol interop = new MockClientServer(new MyRequestListener());
 * interop.poll();
 * interop.confirmShutdown();
 * interop.restart();
 * Status stat = new Status();
 * interop.sendStatus(stat);
 * </pre>
 *
 * @author Yanick Duchesne
 */
public class MockClientServer extends MockProtocol implements MockStreamListener {
  ServerStatelessStreamHelper _server;

  public MockClientServer(InteropCodec codec, RequestListener listener) {
    super(codec, "testCorusPid", null);
    super._listener = this;
    _server         = new ServerStatelessStreamHelper(codec, listener, "mockServer");
  }

  public void onRequest(InputStream req, OutputStream res)
                 throws IOException {
    try {
      _server.processRequest(req, res);
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }

  protected StreamConnection newStreamConnection() throws IOException {
    return new MockStreamConnection(this);
  }
}
