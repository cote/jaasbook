package chp02;

import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.Principal;
import java.security.ProtectionDomain;

public class PolicyDebug
    extends Policy {

  //PolicyDebug.switchTo();
  // -Djava.security.auth.login.config=src/conf/loginmodules.properties
  // -Djava.security.manager -Djava.security.policy=src/conf/my.policy
  // -Dpolicy.provider=PolicyDebug
  // -Djava.security.debug=access:failure

  static public void switchTo() {
    SecurityManager current = System.getSecurityManager();
    if (current == null)
      System.setSecurityManager(new SecurityManager());

    PolicyDebug debug = new PolicyDebug();
    debug.originalPolicy_ = Policy.getPolicy();
    Policy.setPolicy(debug);
  }

  private PolicyDebug() {
    System.out.println("PolicyDebug created.");
  }

  public PermissionCollection getPermissions(
      final CodeSource codesource) {
    System.out.println("getPermissions(CodeSource): " + codesource);
    return originalPolicy_.getPermissions(codesource);

  }

  public PermissionCollection getPermissions(
      final ProtectionDomain domain) {
    System.out.println("getPermissions(ProtectionDomain): "
        + principalsToString(domain));
    return originalPolicy_.getPermissions(domain);
  }

  private String principalsToString(final ProtectionDomain domain) {
    Principal[] p = domain.getPrincipals();
    StringBuffer buf = new StringBuffer();
    buf.append("principals={");
    for (int i = 0; i < p.length; i++) {
      buf.append(p.getClass().getName());
      buf.append("=");
      buf.append(p[i].getName());
      if (i < p.length)
        buf.append(", ");
    }
    buf.append("}");

    buf.append(", CodeSource=");
    buf.append(domain.getCodeSource());
    return buf.toString();
  }

  public void refresh() {
    System.out.println("refresh()");
    originalPolicy_.refresh();
  }

  public boolean implies(final ProtectionDomain domain,
      final Permission permission) {

    System.out.println("ProtectionDomain="
        + principalsToString(domain));
    System.out.println("Permission=" + permission);

    return originalPolicy_.implies(domain, permission);
  }

  private Policy originalPolicy_;
}