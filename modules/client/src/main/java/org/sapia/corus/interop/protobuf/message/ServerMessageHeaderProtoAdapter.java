package org.sapia.corus.interop.protobuf.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ServerMessageHeader;
import org.sapia.corus.interop.protobuf.CorusInteroperability;
import org.sapia.corus.interop.protobuf.CorusInteroperability.Header;
import org.sapia.corus.interop.protobuf.ProtobufEncoderHelper;

public class ServerMessageHeaderProtoAdapter implements ServerMessageHeader, EncodableHeader {
  
  private CorusInteroperability.Server delegate;
  
  public ServerMessageHeaderProtoAdapter(CorusInteroperability.Server delegate) {
    this.delegate = delegate;
  }
  
  @Override
  public long getProcessingTime() {
    return delegate.getProcessingTime();
  }
  
  @Override
  public String getRequestId() {
    return delegate.getRequestId();
  }
  
  public CorusInteroperability.Server getDelegate() {
    return delegate;
  }
  
  @Override
  public Header encode() {
    return ProtobufEncoderHelper.wrapHeader(delegate);
  }

  public static class ServerMessageHeaderProtoAdapterBuilder implements Builder {
    
    private CorusInteroperability.Server.Builder delegate = CorusInteroperability.Server.newBuilder();
    
    @Override
    public Builder processingTime(long processingTime) {
      delegate.setProcessingTime(processingTime);
      return this;
    }
    
    @Override
    public Builder requestId(String requestId) {
      delegate.setRequestId(requestId);
      return this;
    }
    
    @Override
    public ServerMessageHeader build() {
      CorusInteroperability.Server header = delegate.build();
      InteropUtils.checkStateTrue(header.getProcessingTime() >= 0 , "Processing time invalid on Server header: %s", header.getProcessingTime());
      InteropUtils.checkStateNotNullOrBlank(header.getRequestId(), "Corus request ID not set on Server header");
      return new ServerMessageHeaderProtoAdapter(header);
    }
    
  }
 
}
