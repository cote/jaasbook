package chp06;

import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompositePolicy
    extends Policy {

  private List policies = Collections.EMPTY_LIST;

  public CompositePolicy(List policies) {
    this.policies = new ArrayList(policies);
  }

  public PermissionCollection getPermissions(ProtectionDomain domain) {
    Permissions perms = new Permissions();
    for (Iterator itr = policies.iterator(); itr.hasNext();) {
      Policy p = (Policy) itr.next();
      PermissionCollection permCol = p.getPermissions(domain);
      for (Enumeration en = permCol.elements(); en
          .hasMoreElements();) {
        Permission p1 = (Permission) en.nextElement();
        perms.add(p1);
      }
    }
    return perms;
  }

  public boolean implies(final ProtectionDomain domain,
      final Permission permission) {
    for (Iterator itr = policies.iterator(); itr.hasNext();) {
      Policy p = (Policy) itr.next();
      if (p.implies(domain, permission)) {
        return true;
      }
    }

    return false;
  }

  public PermissionCollection getPermissions(CodeSource codesource) {
    Permissions perms = new Permissions();
    for (Iterator itr = policies.iterator(); itr.hasNext();) {
      Policy p = (Policy) itr.next();
      PermissionCollection permsCol = p.getPermissions(codesource);
      for (Enumeration en = permsCol.elements(); en
          .hasMoreElements();) {
        Permission p1 = (Permission) en.nextElement();
        perms.add(p1);
      }
    }
    return perms;
  }

  public void refresh() {
    for (Iterator itr = policies.iterator(); itr.hasNext();) {
      Policy p = (Policy) itr.next();
      p.refresh();
    }
  }

}