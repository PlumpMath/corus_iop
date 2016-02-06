package org.sapia.corus.interop.protobuf.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ContextMessagePart;
import org.sapia.corus.interop.api.message.ParamMessagePart;

public class ContextMessagePartProtoAdapterTest {
  
  private ContextMessagePart context;

  @Before
  public void setUp() throws Exception {
    context = new ContextMessagePartProtoAdapter.ContextProtoAdapterBuilder()
        .name("test")
        .build();
  }

  @Test
  public void testGetName() {
    assertEquals("test", context.getName());
  }
  
  @Test
  public void testToMap() {
    context = new ContextMessagePartProtoAdapter.ContextProtoAdapterBuilder()
        .name("test")
        .param("p", "v")
        .build();
    assertEquals(1, context.getParams().size());
  }

  @Test
  public void testCompare_before() {
    ParamMessagePart p1 = new ParamMessagePartProtoAdapter.ParamProtobufAdapterBuilder().name("p1").value("v1").build();
    ParamMessagePart p2 = new ParamMessagePartProtoAdapter.ParamProtobufAdapterBuilder().name("p2").value("v2").build();
    
    assertTrue(context.compare(p1, p2) < 0);
  }

  @Test
  public void testCompare_after() {
    ParamMessagePart p2 = new ParamMessagePartProtoAdapter.ParamProtobufAdapterBuilder().name("p1").value("v1").build();
    ParamMessagePart p1 = new ParamMessagePartProtoAdapter.ParamProtobufAdapterBuilder().name("p2").value("v2").build();
    
    assertTrue(context.compare(p1, p2) > 0);
  }

  @Test
  public void testCompare_same() {
    ParamMessagePart p2 = new ParamMessagePartProtoAdapter.ParamProtobufAdapterBuilder().name("p1").value("v1").build();
    ParamMessagePart p1 = new ParamMessagePartProtoAdapter.ParamProtobufAdapterBuilder().name("p1").value("v2").build();
    
    assertTrue(context.compare(p1, p2) == 0);
  }

}
