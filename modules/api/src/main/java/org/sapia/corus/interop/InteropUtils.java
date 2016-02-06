package org.sapia.corus.interop;

import java.io.Closeable;
import java.io.IOException;

/**
 * Holds various utility methods.
 * 
 * @author yduchesne
 *
 */
public class InteropUtils {
  
  private InteropUtils() {
    
  }

  public static void checkStateFalse(boolean condition, String msg, Object...args) {
    if (condition == true) {
      throw new IllegalStateException(String.format(msg, args));
    }
  }
  
  public static void checkStateTrue(boolean condition, String msg, Object...args) {
    if (condition == false) {
      throw new IllegalStateException(String.format(msg, args));
    }
  }
  
  public static void checkStateNotNull(Object toCheck, String msg, Object...args) {
    if (toCheck == null) {
      throw new IllegalStateException(String.format(msg, args));
    }
  }
  
  public static void checkStateNotNullOrBlank(String toCheck, String msg, Object...args) {
    if (toCheck == null || toCheck.isEmpty()) {
      throw new IllegalStateException(String.format(msg, args));
    }
  }
  
  public static void closeSilently(Closeable toClose) {
    if (toClose != null) {
      try {
        toClose.close();
      } catch (IOException e) {
        // noop
      }
    }
  }
}
