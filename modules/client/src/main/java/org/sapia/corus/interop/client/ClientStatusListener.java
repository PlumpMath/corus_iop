package org.sapia.corus.interop.client;

import org.sapia.corus.interop.api.StatusRequestListener;
import org.sapia.corus.interop.api.message.ContextMessagePart;
import org.sapia.corus.interop.api.message.InteropMessageBuilderFactory;
import org.sapia.corus.interop.api.message.StatusMessageCommand.Builder;

/**
 * @author Yanick Duchesne
 */
public class ClientStatusListener implements StatusRequestListener{
  
  public static final String CORUS_PROCESS_STATUS = "corus.process.status";
  public static final String TOTAL_MEMORY = "vm.totalMemory";
  public static final String FREE_MEMORY = "vm.freeMemory";
  public static final String MAX_MEMORY = "vm.maxMemory";  
  
  private static boolean isPlatformMBeansSupported;
  
  static{
    try{
      Class.forName("java.lang.management.RuntimeMXBean");
      isPlatformMBeansSupported = true;
    }catch(Exception e){
      isPlatformMBeansSupported = false;
    }
  }
 
  @Override
  public void onStatus(Builder statusBuilder, InteropMessageBuilderFactory factory) {
    ContextMessagePart.Builder c = factory.newContextBuilder();
      
    c.name(CORUS_PROCESS_STATUS);
    
    c.param(MAX_MEMORY, Long.toString(Runtime.getRuntime().maxMemory()));
    c.param(TOTAL_MEMORY, Long.toString(Runtime.getRuntime().totalMemory()));
    c.param(FREE_MEMORY, Long.toString(Runtime.getRuntime().freeMemory()));
  
    if(isPlatformMBeansSupported){
      PlatformMBeansStatusHelper.process(c);
    }
    statusBuilder.context(c.build());
  }

}
