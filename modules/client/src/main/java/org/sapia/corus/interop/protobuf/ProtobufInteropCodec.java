package org.sapia.corus.interop.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.sapia.corus.interop.InteropCodec;
import org.sapia.corus.interop.api.message.InteropMessageBuilderFactory;
import org.sapia.corus.interop.api.message.Message;
import org.sapia.corus.interop.protobuf.message.EncodableCommand;
import org.sapia.corus.interop.protobuf.message.EncodableHeader;
import org.sapia.corus.interop.protobuf.message.FaultProtoAdapter;
import org.sapia.corus.interop.protobuf.message.ProtobufInteropMessageBuilderFactory;

import com.google.protobuf.ExtensionRegistry;

/**
 * Implements the {@link InteropCodec} interface over Protobuf.
 * 
 * @author yduchesne
 *
 */
public class ProtobufInteropCodec implements InteropCodec {
  
  private static final ProtobufInteropMessageBuilderFactory FACTORY  = new ProtobufInteropMessageBuilderFactory();
  private static final ExtensionRegistry                    REGISTRY = ExtensionRegistry.newInstance();
  
  static {
    CorusInteroperability.registerAllExtensions(REGISTRY);
  }
    
  @Override
  public InteropWireFormat getWireFormat() {
    return InteropCodec.InteropWireFormat.PROTOBUF;
  }
  
  @Override
  public InteropMessageBuilderFactory getMessageBuilderFactory() {
    return FACTORY;
  }
  
  @Override
  public Message decode(InputStream anInput) throws IOException {
    CorusInteroperability.Message decodedMsg = CorusInteroperability.Message.newBuilder().mergeFrom(anInput, REGISTRY).build();
    return ProtobufDecoderHelper.decode(decodedMsg);
  }
  
  @Override
  public void encode(Message msg, OutputStream anOutput) throws IOException {
    List<CorusInteroperability.Command> encCmds = new ArrayList<>(msg.getCommands().size());
    CorusInteroperability.Header  encHeader;
    for (int i = 0; i < msg.getCommands().size(); i++) {
      if (msg.getCommands().get(i) instanceof EncodableCommand) {
        CorusInteroperability.Command ecmd = ((EncodableCommand) msg.getCommands().get(i)).encode();
        encCmds.add(ecmd);
      } else {
        throw new IllegalArgumentException("Expected instance of: " 
            + EncodableCommand.class.getName() 
            + ". Got: " + msg.getCommands().get(i).getClass().getName());
      }
    }
    
    if (msg.getHeader() instanceof EncodableHeader) {
      encHeader = ((EncodableHeader) msg.getHeader()).encode();
    } else {
      throw new IllegalArgumentException("Expected instance of: " 
            + EncodableHeader.class.getName() + ". Got: " 
            + msg.getHeader().getClass().getName());
    }
    
    if (msg.getError() != null) {
      CorusInteroperability.Message encMsg = CorusInteroperability.Message.newBuilder()
          .setError(((FaultProtoAdapter) msg.getError()).getDelegate())
          .setHeader(encHeader)
          .build();
      encMsg.writeTo(anOutput);
    } else {
      CorusInteroperability.Message encMsg = CorusInteroperability.Message.newBuilder()
          .clearError()
          .addAllCommands(encCmds)
          .setHeader(encHeader)
          .build();
      encMsg.writeTo(anOutput);
    }
    anOutput.flush();
  }
}
