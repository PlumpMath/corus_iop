package org.sapia.corus.interop.protobuf.message;

import org.sapia.corus.interop.protobuf.CorusInteroperability;

public interface EncodableHeader {
  
  public CorusInteroperability.Header encode();

}
