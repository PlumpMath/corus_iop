package org.sapia.corus.interop.api.message;

/**
 * Holds error details, if a message identifies that an error occurred.
 * 
 * @author yduchesne
 *
 */
public interface FaultMessagePart {
  
  /**
   * @return a fault code, or <code>null</code> if no such code was provided.
   */
  public String getFaultcode();

  /**
   * @return a {@link String} identifying the source of the error, or <code>null</code>
   * if the source is not identified.
   */
  public String getFaultactor();

  /**
   * @return the error message.
   */
  public String getFaultstring();

  /**
   * @return the full error details (i.e.: stack trace).
   */
  public String getDetail();

  
  // --------------------------------------------------------------------------
  // Builder interface
  
  interface Builder {
    
    Builder faultCode(String code);
    
    Builder faultActor(String actor);
    
    Builder faultString(String msg);
    
    Builder faultDetail(String detail);
    
    Builder faultDetail(Exception exception);
    
    FaultMessagePart build();
 
  }
}
