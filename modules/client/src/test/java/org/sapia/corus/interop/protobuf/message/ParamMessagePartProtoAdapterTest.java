package org.sapia.corus.interop.protobuf.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ParamMessagePart;

public class ParamMessagePartProtoAdapterTest {
  
  private ParamMessagePart param;

  @Before
  public void setUp() throws Exception {
    param = new ParamMessagePartProtoAdapter.ParamProtobufAdapterBuilder()
        .name("test")
        .value("testValue")
        .build();
  }

  @Test
  public void testGetName() {
    assertEquals("test", param.getName());
  }

  @Test
  public void testGetValue() {
    assertEquals("testValue", param.getValue());
  }

}
