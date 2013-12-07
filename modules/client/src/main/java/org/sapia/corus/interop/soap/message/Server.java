package org.sapia.corus.interop.soap.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ServerMessageHeader;

/**
 * @author jcdesrochers
 * @author yduchesne
 */
public class Server extends BaseHeader implements ServerMessageHeader {

  /** The processing time in milliseconds of this server header. */
  private long _theProcessingTime;

  /**
   * Creates a new Server instance.
   */
  public Server() {
  }

  @Override
  public long getProcessingTime() {
    return _theProcessingTime;
  }

  public void setProcessingTime(long aProcessingTime) {
    _theProcessingTime = aProcessingTime;
  }

  public String toString() {
    StringBuffer aBuffer = new StringBuffer(super.toString());
    aBuffer.append("[processingTime=").append(_theProcessingTime).append("]");

    return aBuffer.toString();
  }
  
  public static class ServerBuilder implements Builder {
    
    private Server server = new Server();
    
    @Override
    public Builder requestId(String requestId) {
      server.setRequestId(requestId);
      return this;
    }
    
    @Override
    public Builder processingTime(long processingTime) {
      server.setProcessingTime(processingTime);
      return this;
    }
    
    @Override
    public ServerMessageHeader build() {
      InteropUtils.checkStateNotNullOrBlank(server.getRequestId(), "Request ID not set on Server header");
      InteropUtils.checkStateTrue(server.getProcessingTime() >= 0, "Processing time invalid on Server header: %s", server.getProcessingTime());
      Server s = server;
      server = null;
      return s;
    }
    
  }
}
