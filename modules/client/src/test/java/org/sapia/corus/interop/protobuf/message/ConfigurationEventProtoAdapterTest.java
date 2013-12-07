package org.sapia.corus.interop.protobuf.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.sapia.corus.interop.api.message.ConfigurationEventMessageCommand;

public class ConfigurationEventProtoAdapterTest {

  private ConfigurationEventMessageCommand cmd;
  
  @Before
  public void setUp() throws Exception {
    cmd = new ConfigurationEventProtoAdapter.ConfigurationEventProtoAdapterBuilder()
        .commandId("123")
        .param("test", "testValue")
        .type(ConfigurationEventMessageCommand.TYPE_UPDATE).build();
  }

  @Test
  public void testGetCommandId() {
    assertEquals("123", cmd.getCommandId());
  }

  @Test
  public void testGetParams() {
    assertEquals(1, cmd.getParams().size());
  }

  @Test
  public void testToMap() {
    assertEquals("testValue", cmd.toMap().get("test"));
  }

  @Test
  public void testGetType() {
    assertEquals(ConfigurationEventMessageCommand.TYPE_UPDATE, cmd.getType());
  }

}
