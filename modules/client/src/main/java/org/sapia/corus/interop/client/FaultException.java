package org.sapia.corus.interop.client;

import org.sapia.corus.interop.api.message.FaultMessagePart;

/**
 * Wraps a {@link FaultMessagePart}, corresponding to an error that occurred on the server side.
 * 
 * @author yduchesne
 */
public class FaultException extends Exception {
  
  private static final long serialVersionUID = -6378918465068539068L;
  
  private FaultMessagePart _fault;

  public FaultException(FaultMessagePart f) {
    _fault = f;
  }

  public String getMessage() {
    return _fault.getFaultstring() + System.getProperty("line.separator") +
           _fault.getDetail();
  }

  public FaultMessagePart getFault() {
    return _fault;
  }
}
