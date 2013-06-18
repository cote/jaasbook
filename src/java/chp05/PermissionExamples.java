package chp05;

import java.io.FilePermission;
import java.security.Permission;
import java.util.PropertyPermission;

public class PermissionExamples {

  public static void main(String[] args) {
    FilePermission filePerm = new FilePermission("/tmp/log.txt",
        "read, write");

    SecurityManager sm = System.getSecurityManager();
    if (sm != null) {
      sm.checkPermission(new PropertyPermission("java.version",
          "read"));
    }
  }

  static public boolean hasPermission(Permission perm) {
    SecurityManager sm = System.getSecurityManager();
    if (sm != null) {
      try {
        sm.checkPermission(perm);
      } catch (SecurityException e) {
        return false;
      }
    }
    return true;
  }
}

