package org.sapia.corus.interop;

import java.util.Properties;

import org.junit.Test;
import org.sapia.corus.interop.InteropCodec.InteropWireFormat;
import org.sapia.corus.interop.api.Consts;

public class InteropCodecFactoryTest {

  @Test
  public void testGetByWireFormat_soap() {
    InteropCodecFactory.getByWireFormat(InteropWireFormat.SOAP);
  }
  
  @Test
  public void testGetByWireFormat_protobuf() {
    InteropCodecFactory.getByWireFormat(InteropWireFormat.PROTOBUF);
  }
  
  @Test
  public void testGetByType_soap() {
    InteropCodecFactory.getByType(InteropWireFormat.SOAP.type());
  }
  
  @Test
  public void testGetByType_protobuf() {
    InteropCodecFactory.getByType(InteropWireFormat.PROTOBUF.type());
  }

  @Test
  public void testGetByProperty() {
    Properties props = new Properties();
    props.setProperty(Consts.CORUS_PROCESS_INTEROP_PROTOCOL, InteropWireFormat.PROTOBUF.type());
    InteropCodecFactory.getByProperty(props);
  }

}
