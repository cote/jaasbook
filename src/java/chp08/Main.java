package chp08;

import java.security.Policy;
import java.security.PrivilegedAction;

import javax.security.auth.Subject;

import util.id.Id;
import chp04.UserGroupPrincipal;
import chp06.AuthHelper;
import chp06.DbPolicy;
import chp06.PermissionService;

public class Main {

  public static void main(String[] args) throws Exception {
    AuthHelper authHelper = new AuthHelper();

    try {
      authHelper.createTestUser("testuser", "password");
      UserGroupPrincipal p = authHelper.getUserGrp();
      authHelper.loginTestUser();
      Subject subject = authHelper.getSubject();

      Policy.setPolicy(new DbPolicy());
      boolean granted = true;
      try {
        Subject.doAsPrivileged(subject, new PrivilegedAction() {

          public Object run() {
            Record r = new Record(Id.create("id1"), "record1",
                "this is some content.");
            RecordKeeper rk = new RecordKeeper();
            rk.create(r);
            return null;
          }
        }, null);
      } catch (SecurityException e) {
        granted = false;
      }

      System.out.println("Permission granted? " + granted);

      RecordPermission perm = new RecordPermission(
          Id.create("id1"), "create,read");
      PermissionService.addPermission(p.getId(), Id
          .create("permId1"), perm);
      System.out.println("Added grant for RecordPermission.");
      granted = true;
      try {
        Subject.doAsPrivileged(subject, new PrivilegedAction() {

          public Object run() {
            Record r = new Record(Id.create("id1"), "record1",
                "this is some content.");
            RecordKeeper rk = new RecordKeeper();
            rk.create(r);
            return null;
          }
        }, null);
      } catch (SecurityException e) {
        granted = false;
      }
      System.out.println("Permission granted? " + granted);
    } finally {
      authHelper.cleanUp();
      PermissionService.removePermission(Id.create("permId1"));
    }
  }

}