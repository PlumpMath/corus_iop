package org.sapia.corus.interop.protobuf.message;

import org.sapia.corus.interop.protobuf.CorusInteroperability;

public interface EncodableCommand {
  
  public CorusInteroperability.Command encode();

}
