package org.sapia.corus.interop.soap;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.FaultMessagePart;

/**
 *
 *
 * @author <a href="mailto:jc@sapia-oss.org">Jean-Cedric Desrochers</a>
 * <dl>
 * <dt><b>Copyright:</b><dd>Copyright &#169; 2002-2003 <a href="http://www.sapia-oss.org">
 *     Sapia Open Source Software</a>. All Rights Reserved.</dd></dt>
 * <dt><b>License:</b><dd>Read the license.txt file of the jar or visit the
 *     <a href="http://www.sapia-oss.org/license.html" target="sapia-license">license page</a>
 *     at the Sapia OSS web site</dd></dt>
 * </dl>
 */
public class Fault implements FaultMessagePart {
  
  /////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////  INSTANCE ATTRIBUTES  /////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////
  private String _theCode;
  private String _theActor;
  private String _theString;
  private String _theDetail;

  /////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////  CONSTRUCTORS  /////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Creates a new Fault instance.
   */
  public Fault() {
  }

  /////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////  ACCESSOR METHODS  ///////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public String getFaultcode() {
    return _theCode;
  }

  @Override
  public String getFaultactor() {
    return _theActor;
  }

  @Override
  public String getFaultstring() {
    return _theString;
  }

  @Override
  public String getDetail() {
    return _theDetail;
  }

  /////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////  MUTATOR METHODS  ///////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////
  public void setFaultcode(String aCode) {
    _theCode = aCode;
  }

  public void setFaultactor(String anActor) {
    _theActor = anActor;
  }

  public void setFaultstring(String aString) {
    _theString = aString;
  }

  public void setDetail(Exception aDetail) {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    PrintWriter pw = new PrintWriter(bos, true);
    aDetail.printStackTrace(pw);
    String stackTrace = new String(bos.toByteArray());
    setDetail(stackTrace);
  }
  
  public void setDetail(String aDetail) {
    _theDetail = aDetail;
  }


  /////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////  OVERRIDEN METHODS  //////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Returns a string representation of this fault.
   *
   * @return A string representation of this fault.
   */
  public String toString() {
    StringBuffer aBuffer = new StringBuffer(super.toString());
    aBuffer.append("[faultcode=").append(_theCode).append(" faultactor=")
           .append(_theActor).append(" faultstring=").append(_theString)
           .append(" detail=").append(_theDetail).append("]");

    return aBuffer.toString();
  }
  
  public static FaultMessagePart.Builder builder() {
    return new FaultBuilder();
  }
  
  public static class FaultBuilder implements FaultMessagePart.Builder {
    
    private Fault fault = new Fault();
    
    @Override
    public Builder faultActor(String actor) {
      fault.setFaultactor(actor);
      return this;
    }
    
    @Override
    public Builder faultCode(String code) {
      fault.setFaultcode(code);
      return this;
    }
    
    @Override
    public Builder faultString(String msg) {
      fault.setFaultstring(msg);
      return this;
    }
    
    @Override
    public Builder faultDetail(Exception exception) {
      fault.setDetail(exception);
      return this;
    }
    
    @Override
    public Builder faultDetail(String detail) {
      fault.setDetail(detail);
      return this;
    }
    
    @Override
    public FaultMessagePart build() {
      InteropUtils.checkStateNotNullOrBlank(fault.getFaultcode(), "Fault code not set");
      InteropUtils.checkStateNotNullOrBlank(fault.getDetail(), "Fault details not set");
      InteropUtils.checkStateNotNullOrBlank(fault.getFaultstring(), "Fault message not set");
      InteropUtils.checkStateNotNullOrBlank(fault.getFaultactor(), "Fault actor not set");
      Fault f = fault;
      fault = null;
      return f;
    }
    
    
  }
}
