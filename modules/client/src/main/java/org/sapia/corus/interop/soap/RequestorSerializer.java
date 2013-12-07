package org.sapia.corus.interop.soap;

import org.sapia.corus.interop.api.message.ShutdownMessageCommand.Requestor;
import org.sapia.util.xml.Namespace;
import org.sapia.util.xml.idefix.IdefixProcessorIF;
import org.sapia.util.xml.idefix.SerializationContext;
import org.sapia.util.xml.idefix.SerializationException;
import org.sapia.util.xml.idefix.SerializerIF;
import org.sapia.util.xml.idefix.serializer.SerializerHelper;

public class RequestorSerializer implements SerializerIF {

  @Override
  public void serialize(Object anObject, SerializationContext aContext)
    throws SerializationException {
    if (anObject != null) {
      // Extract the value of the object
      String aValue = ((Requestor) anObject).getType();
  
      // Add the value as the content of the current element
      aContext.getXmlBuffer().addContent(aValue);
    }
  }

  @Override
  public void serialize(Object anObject, Namespace aNamespace,
    String anObjectName, SerializationContext aContext) throws SerializationException {
    if (anObject != null) {
      // Extract the value of the object
      String aValue = ((Requestor) anObject).getType();
  
      // Modify the case of the object name
      String aName = SerializerHelper.firstToLowerFromIndex(anObjectName, 0);
  
      // Generated the XML attribute according to the encoding style
      if (aContext.getEncodingStyle() == IdefixProcessorIF.ENCODING_INLINE) {
        SerializerHelper.attributeEncodeInline(aContext.getXmlBuffer(), aNamespace, aName, aValue);
      } else if (aContext.getEncodingStyle() == IdefixProcessorIF.ENCODING_SOAP) {
        SerializerHelper.attributeEncodeSoap(aContext.getXmlBuffer(), aNamespace, aName, aValue);
      } else {
        throw new IllegalArgumentException("The XML encoding style is ivalid: " + aContext.getEncodingStyle());
      }
    }
  }

}
