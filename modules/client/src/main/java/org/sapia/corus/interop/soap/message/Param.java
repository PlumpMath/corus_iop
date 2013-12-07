package org.sapia.corus.interop.soap.message;

import java.io.Serializable;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ParamMessagePart;

/**
 * @author jcdesrochers
 * @author yduchesne
 */
public class Param implements Serializable, ParamMessagePart {
  
  static final long serialVersionUID = 1L;

  /** The name of this context. */
  private String _theName;

  /** The value of this context. */
  private String _theValue;

  /**
   * Creates a new Param instance.
   */
  public Param() {
  }

  /**
   * Creates a new Param instance.
   */
  public Param(String aName, String aValue) {
    _theName  = aName;
    _theValue = aValue;
  }

  @Override
  public String getName() {
    return _theName;
  }

  @Override
  public String getValue() {
    return _theValue;
  }

  public void setName(String aName) {
    _theName = aName;
  }

  public void setValue(String aValue) {
    _theValue = aValue;
  }

  public String toString() {
    StringBuffer aBuffer = new StringBuffer(super.toString());
    aBuffer.append("[name=").append(_theName).append(" value=").append(_theValue)
           .append("]");

    return aBuffer.toString();
  }
  
  @Override
  public int hashCode() {
    return _theName.hashCode();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ParamMessagePart) {
      ParamMessagePart other = (ParamMessagePart) obj;
      if (_theValue == null && other.getValue() != null) {
        return false;
      } else if (_theValue != null && other.getValue() == null) {
        return false;
      } else {
        return _theName.equals(other.getName()) && _theValue.equals(other.getValue());
      }
    }
    return false;
  }
  
  public static class ParamBuilder implements Builder {
    
    private Param param = new Param();
    
    public Builder name(String name) {
      param.setName(name);
      return this;
    }
    
    @Override
    public Builder value(String value) {
      param.setValue(value);
      return this;
    }
    
    @Override
    public ParamMessagePart build() {
      InteropUtils.checkStateNotNullOrBlank(param.getName(), "Param name not set");
      InteropUtils.checkStateNotNull(param.getValue(), "Param value not set");
      Param p = param;
      param = null;
      return p;
    }
    
  }
}
