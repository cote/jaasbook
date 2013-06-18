package chp06;

import java.security.AccessController;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.Principal;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import chp04.UserGroupPrincipal;

public class DbPolicyNoLogging
    extends Policy {

  public PermissionCollection getPermissions(CodeSource codesource) {
    // others may add to this, so use heterogeneous Permissions
    Permissions perms = new Permissions();
    perms.add(new AllPermission());
    return perms;
  }

  public PermissionCollection getPermissions(
      final ProtectionDomain domain) {
    final Permissions permissions = new Permissions();

    // Look up permissions
    final Set principalIds = new HashSet();
    Principal[] principals = domain.getPrincipals();
    if (principals != null && principals.length > 0) {
      for (int i = 0; i < principals.length; i++) {
        Principal p = principals[i];
        if (p instanceof UserGroupPrincipal) {
          UserGroupPrincipal userGroup = (UserGroupPrincipal) p;
          principalIds.add(userGroup.getId());
        }
      }
      if (!principalIds.isEmpty()) {
        try {
          List perms = (List) AccessController
              .doPrivileged(new PrivilegedExceptionAction() {

                public Object run() throws SQLException {
                  return PermissionService
                      .findPermissions(principalIds);
                }
              });
          for (Iterator itr = perms.iterator(); itr.hasNext();) {
            Permission perm = (Permission) itr.next();
            permissions.add(perm);
          }
        } catch (PrivilegedActionException e) {
          // Log
        }
      }
    } else if (domain.getCodeSource() != null) {

      PermissionCollection codeSrcPerms = getPermissions(domain
          .getCodeSource());
      for (Enumeration en = codeSrcPerms.elements(); en
          .hasMoreElements();) {
        Permission p = (Permission) en.nextElement();
        permissions.add(p);
      }
    }

    return permissions;
  }

  public boolean implies(final ProtectionDomain domain,
      final Permission permission) {
    PermissionCollection perms = getPermissions(domain);

    boolean implies = perms.implies(permission);

    return implies;
  }

  public void refresh() {
    // does nothing for DB.
  }
}