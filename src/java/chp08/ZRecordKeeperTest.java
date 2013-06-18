package chp08;

import java.security.Policy;
import java.security.PrivilegedAction;

import javax.security.auth.Subject;

import junit.framework.TestCase;
import util.id.Id;
import chp04.UserGroupPrincipal;
import chp06.AuthHelper;
import chp06.DbPolicy;
import chp06.PermissionService;

public class ZRecordKeeperTest extends TestCase
{

  public void setUp()
  {
    authHelper_ = new AuthHelper();
  }

  public void tearDown() throws Exception
  {
    authHelper_.cleanUp();
  }

  public void testCreate() throws Exception
  {
    try
    {
      authHelper_.createTestUser("testuser", "password");
      UserGroupPrincipal p = authHelper_.getUserGrp();
      authHelper_.loginTestUser();
      Subject subject = authHelper_.getSubject();

      Policy.setPolicy(new DbPolicy());
      boolean passed = false;
      try
      {
        Subject.doAsPrivileged(subject, new PrivilegedAction()
        {

          public Object run()
          {
            Record r = new Record(Id.create("id1"), "record1", "this is some content.");
            RecordKeeper rk = new RecordKeeper();
            rk.create(r);
            return null;
          }
        }, null);
      }
      catch (SecurityException e)
      {
        passed = true;
      }

      assertTrue("Should have thrown SecurityException for create", passed);

      RecordPermission perm = new RecordPermission(Id.create("id1"), "read");
      PermissionService.addPermission(p.getId(), Id.create("test"), perm);
      passed = false;
      try
      {
        Subject.doAsPrivileged(subject, new PrivilegedAction()
        {

          public Object run()
          {
            Record r = new Record(Id.create("id1"), "record1", "this is some content.");
            RecordKeeper rk = new RecordKeeper();
            rk.create(r);
            return null;
          }
        }, null);
      }
      catch (SecurityException e)
      {
        passed = true;
      }

      assertTrue(
          "Should not have permission now. Permission granted was to read.",
          passed);

      PermissionService.removePermission(Id.create("test"));

      perm = new RecordPermission(Id.create("id2"), "create,read");
      PermissionService.addPermission(p.getId(), Id.create("test2"), perm);
      passed = true;
      try
      {
        Subject.doAsPrivileged(subject, new PrivilegedAction()
        {

          public Object run()
          {
            Record r = new Record(Id.create("id2"), "record1", "this is some content.");
            RecordKeeper rk = new RecordKeeper();
            rk.create(r);
            return null;
          }
        }, null);
      }
      catch (SecurityException e)
      {
        passed = false;
      }

      assertTrue("Should have permission now. Permission granted was to read.",
          passed);

    }
    finally
    {
      PermissionService.removePermission(Id.create("test"));
      PermissionService.removePermission(Id.create("test2"));
    }
  }

  private AuthHelper authHelper_;
}
