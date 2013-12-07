package org.sapia.corus.interop.soap.message;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ProcessMessageHeader;

/**
 * @author jcdesrochers
 * @author yduchesne
 */
public class Process extends BaseHeader implements ProcessMessageHeader {

  /** The corus process identifier associated with this process header. */
  private String _theCorusPid;

  /**
   * Creates a new Process instance.
   */
  public Process() {
  }

  @Override
  public String getCorusPid() {
    return _theCorusPid;
  }

  public void setCorusPid(String aCorusPid) {
    _theCorusPid = aCorusPid;
  }

  /**
   * @return A string representation of this process header.
   */
  public String toString() {
    StringBuffer aBuffer = new StringBuffer(super.toString());
    aBuffer.append("[corusPid=").append(_theCorusPid).append("]");

    return aBuffer.toString();
  }
  
  public static class ProcessBuilder implements Builder {
    
    private Process proc = new Process();
    
    @Override
    public Builder corusPid(String pid) {
      proc.setCorusPid(pid);
      return this;
    }
    
    @Override
    public Builder requestId(String requestId) {
      proc.setRequestId(requestId);
      return this;
    }
    
    @Override
    public ProcessMessageHeader build() {
      InteropUtils.checkStateNotNullOrBlank(proc.getCorusPid(), "Process ID not set on Process header");
      InteropUtils.checkStateNotNullOrBlank(proc.getRequestId(), "Request ID not set on Process header");
      Process p = proc;
      proc = null;
      return p;
    }
    
  }
}
