package org.sapia.corus.interop.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.sapia.corus.interop.api.message.InteropMessageBuilderFactory;
import org.sapia.corus.interop.api.message.MessageCommand;
import org.sapia.corus.interop.api.message.StatusMessageCommand;
import org.sapia.corus.interop.soap.message.Ack;
import org.sapia.corus.interop.soap.message.SoapInteropMessageBuilderFactory;


/**
 * @author Yanick Duchesne
 *
 * <dl>
 * <dt><b>Copyright:</b><dd>Copyright &#169; 2002-2003 <a href="http://www.sapia-oss.org">Sapia Open Source Software</a>. All Rights Reserved.</dd></dt>
 * <dt><b>License:</b><dd>Read the license.txt file of the jar or visit the
 *        <a href="http://www.sapia-oss.org/license.html">license page</a> at the Sapia OSS web site</dd></dt>
 * </dl>
 */
public class TestProtocol implements InteropProtocol {
  static private int counter = 0;
  
  Log     log       = new StdoutLog();
  int     pollCount;
  int     statCount;
  boolean restart;
  boolean confirmShutdown;
  boolean confirmDump;
  
  @Override
  public InteropMessageBuilderFactory getMessageBuilderFactory() {
    return new SoapInteropMessageBuilderFactory();
  }

  public void confirmShutdown() throws FaultException, IOException {
    confirmShutdown = true;
  }

  public List<MessageCommand> poll() throws FaultException, IOException {
    pollCount++;

    return generateAck("POLL");
  }

  public void restart() throws FaultException, IOException {
    restart = true;
  }

  public List<MessageCommand> sendStatus(StatusMessageCommand stat) throws FaultException, IOException {
    statCount++;

    return generateAck("STATUS");
  }

  public List<MessageCommand> pollAndSendStatus(StatusMessageCommand stat) throws FaultException, IOException {
    pollCount++;
    statCount++;

    return generateAck("POLL-STATUS");
  }

  public void setLog(Log log) {
    this.log = log;
  }
  
  private static List<MessageCommand> generateAck(String suffix) {
    ArrayList<MessageCommand> list = new ArrayList<>(1);
    Ack ack = new Ack();
    ack.setCommandId(++counter + suffix);
    list.add(ack);
    return list;
  }
  
}
