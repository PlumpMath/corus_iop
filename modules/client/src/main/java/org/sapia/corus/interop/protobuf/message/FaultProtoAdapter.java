package org.sapia.corus.interop.protobuf.message;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.FaultMessagePart;
import org.sapia.corus.interop.protobuf.CorusInteroperability;

public class FaultProtoAdapter implements FaultMessagePart {

  private CorusInteroperability.Fault delegate;
  
  public FaultProtoAdapter(CorusInteroperability.Fault delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public String getFaultcode() {
    return delegate.getErrorCode();
  }
  
  @Override
  public String getFaultstring() {
    return delegate.getErrorMessage();
  }
  
  @Override
  public String getFaultactor() {
    return delegate.getSourceActor();
  }
 
  @Override
  public String getDetail() {
    return delegate.getErrorDetails();
  }
  
  public CorusInteroperability.Fault getDelegate() {
    return delegate;
  }
  
  public static class FaultProtoAdapterBuilder implements FaultMessagePart.Builder {
    
    CorusInteroperability.Fault.Builder delegate = CorusInteroperability.Fault.newBuilder();
    
    @Override
    public Builder faultActor(String actor) {
      delegate.setSourceActor(actor);
      return this;
    }
    
    @Override
    public Builder faultCode(String code) {
      delegate.setErrorCode(code);
      return this;
    }
    
    @Override
    public Builder faultString(String msg) {
      delegate.setErrorMessage(msg);
      return this;
    }
    
    @Override
    public Builder faultDetail(Exception exception) {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      PrintWriter pw = new PrintWriter(bos, true);
      exception.printStackTrace(pw);
      String stackTrace = new String(bos.toByteArray());
      delegate.setErrorDetails(stackTrace);
      return this;
    }
    
    @Override
    public Builder faultDetail(String detail) {
      delegate.setErrorDetails(detail);
      return this;
    }
    
    @Override
    public FaultMessagePart build() {
      CorusInteroperability.Fault fault = delegate.build();
      InteropUtils.checkStateNotNullOrBlank(fault.getErrorCode(), "Fault code not set");
      InteropUtils.checkStateNotNullOrBlank(fault.getErrorDetails(), "Fault details not set");
      InteropUtils.checkStateNotNullOrBlank(fault.getErrorMessage(), "Fault message not set");
      InteropUtils.checkStateNotNullOrBlank(fault.getSourceActor(), "Fault actor not set");
      return new FaultProtoAdapter(fault);
    }
    
  }
}
