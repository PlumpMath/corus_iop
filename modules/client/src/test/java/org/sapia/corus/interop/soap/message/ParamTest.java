package org.sapia.corus.interop.soap.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class ParamTest {
  
  Param p;

  @Before
  public void setUp() throws Exception {
    p = new Param("test", "testValue");
  }

  @Test
  public void testGetName() {
    assertEquals("test", p.getName());
  }

  @Test
  public void testGetValue() {
    assertEquals("testValue", p.getValue());
  }
  
  @Test
  public void testEqualsObject() {
    assertEquals(p, new Param("test", "testValue"));
  }

  @Test
  public void testEqualsObject_other_value_null() {
    assertNotEquals(p, new Param("test", null));
  }

  @Test
  public void testEqualsObject_this_value_null() {
    assertNotEquals(new Param("test", null), p);
  }
  
  @Test
  public void testEqualsObject_value_not_same() {
    assertNotEquals(p, new Param("test", "testValue2"));
  }
  

  @Test
  public void testEqualsObject_name_not_same() {
    assertNotEquals(p, new Param("test2", "testValue"));
  }
}
