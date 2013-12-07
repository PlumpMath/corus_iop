package org.sapia.corus.interop.soap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.sapia.corus.interop.InteropCodec;
import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.HeaderMessagePart;
import org.sapia.corus.interop.api.message.InteropMessageBuilderFactory;
import org.sapia.corus.interop.api.message.Message;
import org.sapia.corus.interop.api.message.MessageCommand;
import org.sapia.corus.interop.api.message.ShutdownMessageCommand.Requestor;
import org.sapia.corus.interop.soap.message.SoapInteropMessageBuilderFactory;
import org.sapia.util.xml.Namespace;
import org.sapia.util.xml.ProcessingException;
import org.sapia.util.xml.confix.CompositeObjectFactory;
import org.sapia.util.xml.confix.ConfixProcessorFactory;
import org.sapia.util.xml.confix.ConfixProcessorIF;
import org.sapia.util.xml.confix.ReflectionFactory;
import org.sapia.util.xml.idefix.CompositeNamespaceFactory;
import org.sapia.util.xml.idefix.DefaultNamespaceFactory;
import org.sapia.util.xml.idefix.DefaultSerializerFactory;
import org.sapia.util.xml.idefix.IdefixProcessorFactory;
import org.sapia.util.xml.idefix.IdefixProcessorIF;
import org.sapia.util.xml.idefix.PatternNamespaceFactory;


/**
 * The Interop processor provides methods to serializa to xml and deserialize
 * from xml the interop messages. It act as the central processor to perform
 * these operations.
 *
 * @author yduchesne
 * @author jcdesrochers
 */
public class SoapInteropCodec implements InteropCodec {
    
  /** Defines the namespace URI of inerop */
  public static final String INTEROP_NAMESPACE_URI = "http://schemas.sapia-oss.org/corus/interoperability/";

  /** Defines the namespace URI of SOAP */
  public static final String SOAP_NAMESPACE_URI = "http://schemas.xmlsoap.org/soap/envelope/";

  /** The Confix processor to transform the XML into objects */
  private ConfixProcessorIF _theConfixProcessor;

  /** The Idefic processor to transform the XML into objects */
  private IdefixProcessorIF _theIdefixProcessor;
  
  private SoapInteropMessageBuilderFactory _factory = new SoapInteropMessageBuilderFactory();

  /**
   * Creates a new InteropProcessor instance.
   */
  public SoapInteropCodec() {
    initializeConfix();
    initializeIdefix();
  }
  
  @Override
  public InteropWireFormat getWireFormat() {
    return InteropWireFormat.SOAP;
  }
  
  @Override
  public InteropMessageBuilderFactory getMessageBuilderFactory() {
    return _factory;
  }

  /**
   * Initialize the Confix processor to perform the deserialization.
   */
  private void initializeConfix() {
    // Create the reflection factories for the different packages
    ReflectionFactory anInteropObjectFactory = new ReflectionFactory(new String[] {
                                                                       "org.sapia.corus.interop.soap.message"
                                                                     });
    ReflectionFactory aSoapObjectFactory = new ReflectionFactory(new String[] {
                                                                   "org.sapia.corus.interop.soap"
                                                                 });

    // Create the composite factory associating the XML namespace for each factory
    CompositeObjectFactory aCompositeFactory = new CompositeObjectFactory();
    aCompositeFactory.registerFactory(SOAP_NAMESPACE_URI, aSoapObjectFactory);
    aCompositeFactory.registerFactory(INTEROP_NAMESPACE_URI,
                                      anInteropObjectFactory);

    // Create the Confix processor
    _theConfixProcessor = ConfixProcessorFactory.newFactory().createProcessor(aCompositeFactory);
  }

  /**
   * Initialize the Confix processor to perform the serialization.
   */
  private void initializeIdefix() {
    // Define the namespace factories for SOAP and Interop
    PatternNamespaceFactory aPatternNSFactory = new PatternNamespaceFactory();
    aPatternNSFactory.addNamespace("org.sapia.corus.interop.soap.*",
                                   new Namespace(SOAP_NAMESPACE_URI, "SOAP-ENV"));
    aPatternNSFactory.addNamespace("org.sapia.corus.interop.soap.message.*",
                                   new Namespace(INTEROP_NAMESPACE_URI,
                                                 "CORUS-IOP"));

    // Defines the composite namespace factory with the fallback default namespace           
    CompositeNamespaceFactory aCompositeNSFactory = new CompositeNamespaceFactory();
    aCompositeNSFactory.registerNamespaceFactory(aPatternNSFactory);
    aCompositeNSFactory.registerNamespaceFactory(new DefaultNamespaceFactory());

    // Define a specific serializer for SOAP objects in the serializer factory
    DefaultSerializerFactory aSerializerFactory = new DefaultSerializerFactory();
    SoapSerializer           aSoapSerializer = new SoapSerializer();
    RequestorSerializer      aRequestorSerializer = new RequestorSerializer();
    aSerializerFactory.registerSerializer(Requestor.class, aRequestorSerializer);
    aSerializerFactory.registerSerializer(Envelope.class, aSoapSerializer);
    aSerializerFactory.registerSerializer(Fault.class, aSoapSerializer);

    // Create the Idefix processor
    _theIdefixProcessor = IdefixProcessorFactory.newFactory().createProcessor(aSerializerFactory, aCompositeNSFactory);
  }
  
  @Override
  public Message decode(InputStream anInput) throws IOException {
    Message.Builder msgBuilder = Message.Builder.newInstance();
    Envelope env = deserialize(anInput);
    InteropUtils.checkStateFalse(env.getBody().getObjects().isEmpty(), "SOAP envelope body is empty");
    
    if (env.getBody().getObjects().get(0) instanceof Fault) {
      msgBuilder.error((Fault) env.getBody().getObjects().get(0));
    } else {
      for (Object o : env.getBody().getObjects()) {
        InteropUtils.checkStateTrue(o instanceof MessageCommand, "Expected instance of %s. Got: instance of %s",
            MessageCommand.class.getName(), o.getClass().getName());
        MessageCommand cmd = (MessageCommand) o;
        msgBuilder.command(cmd);
      }
    }

    InteropUtils.checkStateFalse(env.getHeader().getObjects().isEmpty(), 
        "SOAP header not defined (expected process or server header, depending on origin of message)");
    
    msgBuilder.header((HeaderMessagePart) env.getHeader().getObjects().get(0));
    return msgBuilder.build();
  }
  
  @Override
  public void encode(Message msg, OutputStream anOutput) throws IOException {
    Envelope toEncode = new Envelope();
    if (msg.getError() != null) {
      InteropUtils.checkStateTrue(msg.getError() instanceof Fault, "Expected instance of %s. Got: instance of %s",
          Fault.class.getName(), msg.getError().getClass().getName());
      Body body = new Body();
      body.addObject((Fault) msg.getError());
    }
    
    Header header = new Header();
    header.addObject(msg.getHeader());
    toEncode.setHeader(header);
    
    Body body = new Body();
    for (MessageCommand cmd : msg.getCommands()) {
      body.addObject(cmd);
    }
    toEncode.setBody(body);
    toEncode.setHeader(header);
    serialize(toEncode, anOutput);
  }

  /**
   * Processes the input stream passed in using the Confix processor and
   * return the result {@link Envelope} object.
   *
   * @param anInput The input stream to process.
   * @return The created {@link Envelope} object.
   * @throws IOException If an error occurs while processing the given input stream.

   */
  Envelope deserialize(InputStream anInput) throws IOException {
    Object anObject = null;

    try {
      anObject = _theConfixProcessor.process(anInput);

      return (Envelope) anObject;
    } catch (ClassCastException cce) {
      throw new IOException("The object created from the input stream is not an Envelope: " +
                                    anObject, cce);
    } catch (ProcessingException e) {
      throw new IOException("Error performing XML to object conversion", e);
    }
  }

  /**
   * Processes the <CODE>Envelope</CODE> object passed in and add the result
   * XML string the output stream.
   *
   * @param anEnvelope The envelope to process.
   * @param anOutput The output stream in which the result is added.
   * @throws IOException If an error occurs while writing to the output stream.
   */
  void serialize(Envelope anEnvelope, OutputStream anOutput) throws IOException {
    try {
      _theIdefixProcessor.process(anEnvelope, anOutput);
    } catch (ProcessingException e) {
      throw new IOException("Error performing conversion to XML", e);
    }
  }
}
