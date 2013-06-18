package util.jaas;

import java.security.Permission;

/**
 * @author michaelcote
 */
public class PermissionUtils
{

  private PermissionUtils()
  {
  }

  static public boolean hasPermission(Permission perm)
  {
    SecurityManager sm = System.getSecurityManager();
    if (sm != null)
    {
      try
      {
        sm.checkPermission(perm);
      }
      catch (SecurityException e)
      {
        return false;
      }
    }
    return true;
  }
}
