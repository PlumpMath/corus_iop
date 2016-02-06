package org.sapia.corus.interop.soap.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ConfigurationEventMessageCommand;

public class ConfigurationEventTest {
  
  ConfigurationEvent event;
  
  @Before
  public void setUp() throws Exception {
    event = new ConfigurationEvent();
  }
  
  @Test
  public void testSetCommandId() {
    event.setCommandId("test");
    assertEquals("test", event.getCommandId());
  }

  @Test
  public void testSetType() {
    event.setType(ConfigurationEventMessageCommand.TYPE_UPDATE);
    assertEquals(ConfigurationEventMessageCommand.TYPE_UPDATE, event.getType());
  }

  @Test
  public void testAddParam() {
    event.addParam(new Param("test", "testValue"));
    assertEquals(1, event.getParams().size());
    assertEquals("testValue", event.toMap().get("test"));
  }

  @Test
  public void testBuilder() {
    ConfigurationEventMessageCommand cmd = new ConfigurationEvent.ConfigurationEventBuilder()
        .commandId("test")
        .type(ConfigurationEventMessageCommand.TYPE_UPDATE)
        .param(new Param.ParamBuilder().name("test").value("testValue").build())
        .build();
    
    assertEquals(1, cmd.getParams().size());
    assertEquals("testValue", cmd.toMap().get("test"));
    assertEquals("test", cmd.getCommandId());
  }

}
