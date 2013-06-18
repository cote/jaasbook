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

public class DbPolicy
    extends Policy {

  static private final String LOG_TOPIC = DbPolicy.class.getName();
  static private final Logger LOGGER = Logger.getLogger(LOG_TOPIC);

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
        if (LOGGER.isLoggable(Level.FINEST)) {
          LOGGER.logp(Level.FINEST, LOG_TOPIC,
              "getPermissions(ProtectionDomain)",
              "Got Principlal type {0} with name {1}",
              new Object[] { p.getClass(), p.getName() });
        }
        if (p instanceof UserGroupPrincipal) {
          UserGroupPrincipal userGroup = (UserGroupPrincipal) p;
          principalIds.add(userGroup.getId());
        }
      }
      if (principalIds.isEmpty()) {
        LOGGER
            .logp(
                Level.FINE,
                LOG_TOPIC,
                "getPermissions(ProtectionDomain)",
                "No UserGroupPrincipals found. No permissions granted. Principals: {0}",
                Arrays.asList(principals));
      } else {
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
          LOGGER
              .logp(
                  Level.WARNING,
                  LOG_TOPIC,
                  "getPermissions(ProtectionDomain)",
                  "SQLException looking up Permissions for Principals {0}. Exception: {1}",
                  new Object[] { principalIds, e });
        }
      }
    } else {
      if (LOGGER.isLoggable(Level.FINEST)) {
        LOGGER
            .logp(
                Level.FINEST,
                LOG_TOPIC,
                "getPermissions(ProtectionDomain)",
                "No principals found for ProtectionDomain with CodeSource of {0}. Using CodeSource.",
                domain.getCodeSource());
      }

      if (domain.getCodeSource() != null) {

        PermissionCollection codeSrcPerms = getPermissions(domain
            .getCodeSource());
        for (Enumeration en = codeSrcPerms.elements(); en
            .hasMoreElements();) {
          Permission p = (Permission) en.nextElement();
          permissions.add(p);
        }
      }
    }

    LOGGER.logp(Level.FINEST, LOG_TOPIC,
        "getPermissions(ProtectionDomain)",
        "Returning permissions " + permissions);
    return permissions;
  }

  public boolean implies(final ProtectionDomain domain,
      final Permission permission) {
    PermissionCollection perms = getPermissions(domain);
    LOGGER.logp(Level.FINEST, LOG_TOPIC, "implies()",
        "Checking Permission {0} for ProtectionDomain {1}/{2}",
        new Object[] { permission, domain.getCodeSource(),
            toString(domain.getPrincipals()) });

    boolean implies = perms.implies(permission);

    if (!implies) {
      LOGGER.logp(Level.FINEST, LOG_TOPIC, "implies()",
          "Permission {0} denied for ProtectionDomain {1}/{2}",
          new Object[] { permission, domain.getCodeSource(),
              toString(domain.getPrincipals()) });
    }

    return implies;
  }

  public void refresh() {
    // does nothing for DB.
  }

  private String toString(Principal[] principals) {
    if (principals == null || principals.length == 0) {
      return "<empty principals>";
    }
    StringBuffer buf = new StringBuffer();

    buf.append("<");
    for (int i = 0; i < principals.length; i++) {
      Principal p = principals[i];
      buf.append("(class=");
      buf.append(p.getClass().getName());
      buf.append(", name=");
      buf.append(p.getName());
      buf.append(")");
      if (i < principals.length - 1) {
        buf.append(", ");
      }

    }
    buf.append(">");

    return buf.toString();
  }

}