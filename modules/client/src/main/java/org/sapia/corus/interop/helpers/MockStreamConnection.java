package org.sapia.corus.interop.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Implements an in-memory {@link StreamConnection}. 
 * 
 * @author Yanick Duchesne
 */
class MockStreamConnection implements StreamConnection {
  ByteArrayOutputStream _response;
  MockStreamListener    _listener;

  public MockStreamConnection(MockStreamListener listener) {
    _listener = listener;
  }

  public void close() throws IOException {
  }

  public InputStream getInputStream() throws IOException {
    if (_response == null) {
      throw new IOException("getOutputStream() must be called prior to getInputStream()");
    }

    ByteArrayInputStream resp = new ByteArrayInputStream(_response.toByteArray());
    _response = null;

    return resp;
  }

  public OutputStream getOutputStream() throws IOException {
    return new MockOutputStream(this);
  }

  static final class MockOutputStream extends ByteArrayOutputStream {
   
    private MockStreamConnection _owner;
    private boolean              _closed;

    MockOutputStream(MockStreamConnection owner) {
      _owner = owner;
    }

    public void close() throws IOException {
      if (!_closed) {
        super.close();
        _owner._response = new ByteArrayOutputStream();
        _owner._listener.onRequest(new ByteArrayInputStream(toByteArray()), _owner._response);
        _closed = true;
      }
    }
  }
}
