package org.sapia.corus.interop;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.sapia.corus.interop.api.message.InteropMessageBuilderFactory;
import org.sapia.corus.interop.api.message.Message;
import org.sapia.corus.interop.api.message.MessageCommand;

/**
 * Interface that defines primitives for encoding and decoding interop commands.
 * 
 * @author jcdesrochers
 * @author yduchesne
 */
public interface InteropCodec {
  
  /**
   * Corresponds to the different interop wire formats.
   * 
   * @author yduchesne
   *
   */
  public enum InteropWireFormat {
    SOAP("soap"), 
    PROTOBUF("protobuf");
    
    private String type;
    
    private InteropWireFormat(String type) {
      this.type = type;
    }
    
    public String type() {
      return type;
    }
    
    /**
     * @param type a wire format type.
     * @return the {@link InteropWireFormat} corresponding to the given type.
     * @throws IllegalArgumentException if no {@link InteropWireFormat} could be found for 
     * the given type.
     */
    public static InteropWireFormat forType(String type) throws IllegalArgumentException {
      if (type.equalsIgnoreCase(SOAP.type)) {
        return SOAP;
      } else if (type.equalsIgnoreCase(PROTOBUF.type)) {
        return PROTOBUF;
      } 
      throw new IllegalArgumentException(
          String.format("Unknown wire format type: %s. Expected one of: %s", 
              Arrays.asList(values())
          )
      );
    }
  }
  
  // --------------------------------------------------------------------------
  
  /**
   * @return the wire format name that this instance supports (soap, protobuf, etc.).
   */
  public InteropWireFormat getWireFormat();
  
  /**
   * @return the {@link InteropMessageBuilderFactory} to use for building {@link MessageCommand}s.
   */
  public InteropMessageBuilderFactory getMessageBuilderFactory();

  /**
   * @param msg a {@link Message} to encode.
   * @param anOutput the {@link OutputStream} to write to.
   * @throws Exception if an error occurs while encoding.
   */
  public void encode(Message msg, OutputStream anOutput) throws IOException;
  
  /**
   * @param anInput the stream to decode from.
   * @return the {@link Message} that was decoded.
   * @throws Exception if an error occurs while decoding.
   */
  public Message decode(InputStream anInput) throws IOException;
  
}
