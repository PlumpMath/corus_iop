package org.sapia.corus.interop.api.message;

public interface ShutdownMessageCommand extends MessageCommand {
  
  public enum Requestor {
    ADMIN("corus.admin"),
    SERVER("corus.server"),
    PROCESS("corus.process");
    
    private String type;
    
    private Requestor(String type) {
      this.type = type;
    }
    
    public String getType() {
      return type;
    }
    
    public static Requestor fromType(String type) {
      if (type.equals(ADMIN.type)) {
        return ADMIN;
      } else if (type.equals(SERVER.type)) {
        return SERVER;
      } else if (type.equals(PROCESS.type)) {
        return PROCESS;
      } else {
        throw new IllegalStateException("Unknown requestor type " + type);
      }
    }
  }

  /**
   * Returns the entity that requested the shutdown command. Some possible values could:
   * <ul>
   * <li><b>corus</b> - when the Corus server discovers that a process has not polled back
   *                     the server for a too long period of time.</li>
   * <li><b>console</b> - when an administrator ask the server to stop a specific process.</li>
   * <li><b>process</b> - when a process managed by Corus explicitly ask to be shutdown
   *                      using the restart command.</li>
   * </ul>
   *
   *
   * @return The entity that requested the shutdown command.
   */
  Requestor getRequestor();

  // --------------------------------------------------------------------------
  // Builder interface
  
  interface Builder {
    
    Builder commandId(String id);
    
    Builder requestor(ShutdownMessageCommand.Requestor requestor);
    
    ShutdownMessageCommand build();
    
  }

}