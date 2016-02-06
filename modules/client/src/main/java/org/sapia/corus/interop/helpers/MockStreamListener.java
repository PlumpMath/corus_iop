package org.sapia.corus.interop.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An instance of this insterface is used in conjunction with the {@link MockClientServer} class.
 * 
 * @author Yanick Duchesne
 */
public interface MockStreamListener {
  
  /**
   * @param req the {@link InputStream} holding a request's payload.
   * @param res the {@link OutputStream} to which to write response data.
   * @throws IOException if an I/O error occurs while handling the request.
   */
  public void onRequest(InputStream req, OutputStream res) throws IOException;
}
