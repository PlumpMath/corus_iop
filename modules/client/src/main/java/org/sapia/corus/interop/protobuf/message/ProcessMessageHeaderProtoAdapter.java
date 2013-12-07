package org.sapia.corus.interop.protobuf.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ProcessMessageHeader;
import org.sapia.corus.interop.protobuf.CorusInteroperability;
import org.sapia.corus.interop.protobuf.CorusInteroperability.Header;
import org.sapia.corus.interop.protobuf.ProtobufEncoderHelper;

public class ProcessMessageHeaderProtoAdapter implements ProcessMessageHeader, EncodableHeader {
  
  private CorusInteroperability.Process delegate;
  
  public ProcessMessageHeaderProtoAdapter(CorusInteroperability.Process delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public String getCorusPid() {
    return delegate.getCorusPid();
  }
  
  @Override
  public String getRequestId() {
    return delegate.getRequestId();
  }
  
  public CorusInteroperability.Process getDelegate() {
    return delegate;
  }
  
  @Override
  public Header encode() {
    return ProtobufEncoderHelper.wrapHeader(delegate);
  }
  
  public static class ProcessMessageHeaderProtoAdapterBuilder implements Builder {
    
    private CorusInteroperability.Process.Builder delegate = CorusInteroperability.Process.newBuilder();
    
    @Override
    public Builder corusPid(String pid) {
      delegate.setCorusPid(pid);
      return this;
    }
    
    @Override
    public Builder requestId(String requestId) {
      delegate.setRequestId(requestId);
      return this;
    }
    
    @Override
    public ProcessMessageHeader build() {
      CorusInteroperability.Process header = delegate.build();
      InteropUtils.checkStateNotNullOrBlank(header.getCorusPid(), "Corus process ID not set on Process header");
      InteropUtils.checkStateNotNullOrBlank(header.getRequestId(), "Corus request ID not set on Process header");
      return new ProcessMessageHeaderProtoAdapter(header);
    }
    
  }

}
