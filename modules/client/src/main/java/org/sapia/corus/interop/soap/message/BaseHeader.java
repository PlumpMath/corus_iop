package org.sapia.corus.interop.soap.message;

import org.sapia.corus.interop.api.message.HeaderMessagePart;

/**
 * This class represents an interop header. It contains the request
 * identifiers that is assigned to each requests and responses exchanged
 * between a Corus server and the processes that ir manages.
 *
 * @author jcdesrochers
 * @author yduchesne
 */
public abstract class BaseHeader implements HeaderMessagePart {

  /** The request identifier of this abstract header. */
  private String _theRequestId;

  /**
   * Creates a new AbstractHeader instance.
   */
  protected BaseHeader() {
  }

  @Override
  public String getRequestId() {
    return _theRequestId;
  }

  public void setRequestId(String aRequestId) {
    _theRequestId = aRequestId;
  }

  /**
   * Returns a string representation of this process header.
   *
   * @return A string representation of this process header.
   */
  public String toString() {
    StringBuffer aBuffer = new StringBuffer(super.toString());
    aBuffer.append("[requestId=").append(_theRequestId).append("]");

    return aBuffer.toString();
  }
}
