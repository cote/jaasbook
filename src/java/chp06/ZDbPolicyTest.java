package chp06;

import java.io.FilePermission;
import java.security.AccessController;
import java.security.AllPermission;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import junit.framework.TestCase;
import util.LoggerInit;
import util.id.Id;
import chp04.UserGroupPrincipal;

public class ZDbPolicyTest
    extends TestCase {

  public void testGetPermissions_CodeSource() {
    Policy p = Policy.getPolicy();
    PermissionCollection col = p.getPermissions(ZDbPolicyTest.class
        .getProtectionDomain().getCodeSource());
    Enumeration en = col.elements();
    List perms = new ArrayList();
    while (en.hasMoreElements()) {
      Permission perm = (Permission) en.nextElement();
      perms.add(perm);
    }

    assertEquals(1, perms.size());
    assertEquals(AllPermission.class, perms.get(0).getClass());
  }

  public void testGetPrermissons_ProtectionDomain_1()
      throws Exception {
    initPolicy();
    System.setSecurityManager(new SecurityManager());
    createTestUser();
    loginTestUser();
    assertNotNull(testSubject_);

    final FilePermission filePerm = new FilePermission("/tmp/test",
        "read");
    boolean passed = false;

    try {
      Subject.doAsPrivileged(testSubject_, new PrivilegedAction() {

        public Object run() {
          AccessController.checkPermission(filePerm);
          return null;
        }

      }, null);
    } catch (SecurityException e) {
      passed = true;
    }

    assertTrue("Shouldn't have file perm yet.", passed);

    PermissionService.addPermission(testUserGrp_.getId(), Id
        .create(), filePerm);

    passed = true;
    try {
      Subject.doAsPrivileged(testSubject_, new PrivilegedAction() {

        public Object run() {
          AccessController.checkPermission(filePerm);
          return null;
        }

      }, null);
    } catch (SecurityException e) {
      passed = false;
    }

    assertTrue("Should now have file perm yet.", passed);

  }

  private void loginTestUser() throws LoginException {
    authHelper_.loginTestUser();
    testSubject_ = authHelper_.getSubject();
  }

  private void createTestUser() throws SQLException {
    authHelper_.createTestUser("testuser", "testpassword");

    testUserId_ = authHelper_.getUserId();
    testUserGrp_ = authHelper_.getUserGrp();

  }

  public void setUp() {
    LoggerInit.init();
    authHelper_ = new AuthHelper();
  }

  private void initPolicy() {
    Policy defaultPolicy = Policy.getPolicy();
    DbPolicy dbPolicy = new DbPolicy();
    List policies = new ArrayList(2);
    policies.add(defaultPolicy);
    policies.add(dbPolicy);
    CompositePolicy p = new CompositePolicy(policies);
    Policy.setPolicy(p);
  }

  public void tearDown() throws Exception {
    authHelper_.cleanUp();
  }

  private String testUsername_ = "testuser";

  private String testPassword_ = "testpassword";

  private Id testUserId_;

  private UserGroupPrincipal testUserGrp_;

  private Subject testSubject_;
  private AuthHelper authHelper_;
}