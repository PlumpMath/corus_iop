package org.sapia.corus.interop.soap.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ContextMessagePart;

public class ContextTest {
  
  private Context ctx;

  @Before
  public void setUp() throws Exception {
    ctx = new Context("test");
  }

  @Test
  public void testGetName() {
    assertEquals("test", ctx.getName());
  }

  @Test
  public void testAddParam() {
    ctx.addParam(new Param("test", "value"));
    assertEquals(1, ctx.getParams().size());
  }

  @Test
  public void testRemoveParam() {
    ctx.addParam(new Param("test", "value"));
    assertEquals(1, ctx.getParams().size());  
    ctx.removeParam(new Param("test", "value"));
    assertTrue(ctx.getParams().isEmpty());
  }
  
  @Test
  public void testRemoveParam_for_name() {
    ctx.addParam(new Param("test", "value"));
    assertEquals(1, ctx.getParams().size());  
    ctx.removeParam("test");
    assertTrue(ctx.getParams().isEmpty());
  }

  @Test
  public void testClearParams() {
    ctx.addParam(new Param("test", "value"));
    assertEquals(1, ctx.getParams().size());  
    ctx.clearParams();
    assertTrue(ctx.getParams().isEmpty());  
  }

  @Test
  public void testCompare_before() {
    Param p1 = new Param("test", "value");
    Param p2 = new Param("test2", "value");

    assertTrue(ctx.compare(p1, p2) < 0);
  }

  @Test
  public void testCompare_after() {
    Param p1 = new Param("test", "value");
    Param p2 = new Param("test2", "value");

    assertTrue(ctx.compare(p2, p1) > 0);
  }
  
  @Test
  public void testCompare_equal() {
    Param p1 = new Param("test", "value");
    Param p2 = new Param("test", "value");

    assertTrue(ctx.compare(p1, p2) == 0);
  }
  
  @Test
  public void testBuilder() {
    ContextMessagePart cmp  = new Context.ContextBuilder()
        .name("test")
        .param("test", "testValue")
        .param(new Param.ParamBuilder().name("test2").value("value").build())
        .build();
    
    assertEquals("test", cmp.getName());
    assertEquals(2, cmp.getParams().size());
  }
}
