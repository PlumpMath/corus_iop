package org.sapia.corus.interop.soap.message;

import org.sapia.corus.interop.api.message.MessageCommand;

/**
 * This class represents a Corus interop command. It contains the command identifier
 * that is associated to every command returned by the Corus server.
 *
 * @author jcdesrochers
 * @author yduchesne
 */
public abstract class Command implements java.io.Serializable, MessageCommand{
  
  static final long serialVersionUID = 1L;

  private String _theCommandId;

  /**
   * Creates a new Command instance.
   */
  protected Command() {
  }

  @Override
  public String getCommandId() {
    return _theCommandId;
  }

  public void setCommandId(String aCommandId) {
    _theCommandId = aCommandId;
  }

  /**
   * Returns a string representation of this abstract request.
   *
   * @return A string representation of this abstract request.
   */
  public String toString() {
    StringBuffer aBuffer = new StringBuffer(super.toString());
    aBuffer.append("[commandId=").append(_theCommandId).append("]");

    return aBuffer.toString();
  }
}
