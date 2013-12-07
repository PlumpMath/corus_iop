package org.sapia.corus.interop.soap.message;


// Import of Sun's JDK classes
// ---------------------------
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sapia.corus.interop.InteropUtils;
import org.sapia.corus.interop.api.message.ContextMessagePart;
import org.sapia.corus.interop.api.message.ParamMessagePart;

/**
 * @author jcdesrochers
 * @author yduchesne
 */
public class Context implements Serializable, ContextMessagePart {
  
  static final long serialVersionUID = 1L;
  
  /** The name of this context. */
  private String _theName;

  /** The list of params of this context. */
  private List<ParamMessagePart> _theParams;

  /**
   * Creates a new Context instance.
   */
  public Context() {
    _theParams = new ArrayList<>();
  }

  /**
   * Creates a new Context instance.
   */
  public Context(String aName) {
    _theName  = aName;
    _theParams = new ArrayList<>();
  }

  @Override
  public String getName() {
    return _theName;
  }

  @Override
  public List<ParamMessagePart> getParams() {
    Collections.sort(_theParams, this);
    return _theParams;
  }

  public void setName(String aName) {
    _theName = aName;
  }

  public void addParam(ParamMessagePart anParam) {
    _theParams.add(anParam);
  }

  public void removeParam(ParamMessagePart anParam) {
    _theParams.remove(anParam);
  }
  
  public void removeParam(String name) {
    for (int i = 0; i < _theParams.size(); i++) {
      ParamMessagePart p = _theParams.get(i);
      if (p.getName().equals(name)) {
        _theParams.remove(i);
        break;
      }
    }
  }

  public void clearParams() {
    _theParams.clear();
  }

  @Override
  public String toString() {
    StringBuffer aBuffer = new StringBuffer(super.toString());
    aBuffer.append("[name=").append(_theName).append(" params=").append(_theParams)
           .append("]");

    return aBuffer.toString();
  }

  @Override
  public int compare(ParamMessagePart param1, ParamMessagePart param2) {
    return param1.getName().compareTo(param2.getName());
  }
  
  public static class ContextBuilder implements Builder {
    
    private Context context = new Context();
    
    @Override
    public Builder name(String name) {
      context.setName(name);
      return this;
    }
    
    @Override
    public Builder param(ParamMessagePart param) {
      Param soapParam = new Param();
      soapParam.setName(param.getName());
      soapParam.setValue(param.getValue());
      context.addParam(soapParam);
      return this;
    }
    
    @Override
    public Builder param(String name, String value) {
      Param soapParam = new Param();
      soapParam.setName(name);
      soapParam.setValue(value);
      context.addParam(soapParam);
      return this;
    }
    
    @Override
    public ContextMessagePart build() {
      Context c = context;
      InteropUtils.checkStateNotNullOrBlank(c.getName(), "Name on set on Context");
      context = null;
      return c;
    }
    
  }
}
