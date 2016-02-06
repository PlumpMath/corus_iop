package org.sapia.corus.interop;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.sapia.corus.interop.InteropCodec.InteropWireFormat;
import org.sapia.corus.interop.api.Consts;
import org.sapia.corus.interop.protobuf.ProtobufInteropCodec;
import org.sapia.corus.interop.soap.SoapInteropCodec;

/**
 * A factory of statically registered {@link InteropCodec} instances.
 * 
 * @author yduchesne
 */
public class InteropCodecFactory {
  
  private static final SoapInteropCodec     SOAP_CODED     = new SoapInteropCodec();
  private static final ProtobufInteropCodec PROTOBUF_CODEC = new ProtobufInteropCodec();
  private static final InteropCodec         DEFAULT_CODEC  = PROTOBUF_CODEC;
  
  private static Map<String, InteropCodec> codecs = new HashMap<>();
  static {
    codecs.put(PROTOBUF_CODEC.getWireFormat().type(), PROTOBUF_CODEC);
    codecs.put(SOAP_CODED.getWireFormat().type(), SOAP_CODED);
  }
  
  
  private InteropCodecFactory() {
  }
  
  /**
   * @param wireFormat the wire format of the codec to return.
   * @return the {@link InteropCodec} corresponding to the given type.
   * @throws IllegalStateException if no codec exists for the given type.
   */
  public static InteropCodec getByWireFormat(InteropWireFormat wireFormat) throws IllegalStateException {
    return getByType(wireFormat.type());
  }
  
  /**
   * @param type the type identifier (soap, protobuf, etc.) of the codec to return.
   * @return the {@link InteropCodec} corresponding to the given type.
   * @throws IllegalStateException if no codec exists for the given type.
   */
  public static InteropCodec getByType(String type) throws IllegalStateException {
    InteropCodec codec = codecs.get(type.toLowerCase());
    InteropUtils.checkStateNotNull(codec, "No codec found for: %s. Expected one of: %s", type, codecs.keySet());
    return codec;
  }

  /**
   * @param props the {@link Properties} to use for getting the codec wire format name.
   * @return the {@link InteropCodec} instance corresponding to the given name.
   * @throws IllegalStateException if no codec exists for the name that was specified.
   * @see Consts#CORUS_PROCESS_INTEROP_PROTOCOL
   */
  public static InteropCodec getByProperty(Properties props) {
    String name = props.getProperty(Consts.CORUS_PROCESS_INTEROP_PROTOCOL);
    if (name == null) {
      return DEFAULT_CODEC;
    }
    return getByType(name);
  }
  
  /**
   * This method internally calls {@link #getByProperty(Properties)}, using the system properties.
   * 
   * @return the {@link InteropCodec} that is specified as part of the system properties.
   * 
   * @see System#getProperties()
   */
  public static InteropCodec getBySystemProperty() {
    return getByProperty(System.getProperties());
  }

}
