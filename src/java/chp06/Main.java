package chp06;

import java.io.FilePermission;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import util.id.Id;

public class Main {

  static public void main(String[] args) throws Exception {
    AuthHelper authHelper = new AuthHelper();

    try {
      Policy defaultPolicy = Policy.getPolicy();
      DbPolicy dbPolicy = new DbPolicy();
      List policies = new ArrayList(2);
      policies.add(defaultPolicy);
      policies.add(dbPolicy);
      CompositePolicy p = new CompositePolicy(policies);
      Policy.setPolicy(p);

      System.setSecurityManager(new SecurityManager());
      authHelper.createTestUser("testuser", "testpassword");
      authHelper.loginTestUser();
      Subject subject = authHelper.getSubject();

      final FilePermission filePerm = new FilePermission(
          "/tmp/test", "read");

      boolean allowed = true;
      try {
        Subject.doAsPrivileged(subject, new PrivilegedAction() {

          public Object run() {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
              sm.checkPermission(filePerm);
            }
            return null;
          }

        }, null);
      } catch (SecurityException e) {
        allowed = false;
      }

      if (allowed) {
        System.out.println("Subject can read file /tmp/test");
      } else {
        System.out.println("Subject cannot read file /tmp/test");
      }

      Id principalId = authHelper.getUserGrp().getId();
      PermissionService.addPermission(principalId, Id.create(),
          filePerm);
      System.out.println("Added " + filePerm + " to Subject.");

      allowed = true;
      try {
        Subject.doAsPrivileged(subject, new PrivilegedAction() {

          public Object run() {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
              sm.checkPermission(filePerm);
            }
            return null;
          }

        }, null);
      } catch (SecurityException e) {
        allowed = false;
      }

      if (allowed) {
        System.out.println("Subject can read file /tmp/test");
      } else {
        System.out.println("Subject cannot read file /tmp/test");
      }
    } finally {
      if (authHelper != null) {
        authHelper.cleanUp();
      }
    }
  }

}