package chp06;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import util.id.Id;

public class ZPermissionServiceTest
    extends TestCase {

  public void testAddPermission() throws SQLException {
    Set toRemove = new HashSet();
    try {
      Id principalId = Id.create();

      // test with non DbPermission
      TestablePermission perm1 = new TestablePermission(
          "/tmp/test", "read,write");
      TestablePermission perm2 = new TestablePermission(
          "/tmp/test2", "read");

      Id permId1 = Id.create();
      Id permId2 = Id.create();

      PermissionService.addPermission(principalId, permId1, perm1);
      toRemove.add(permId1);
      PermissionService.addPermission(principalId, permId2, perm2);
      toRemove.add(permId2);

      List perms2 = PermissionService.findPermissions(principalId);

      System.out.println(perms2);

      assertNotNull(perms2);
      assertEquals(2, perms2.size());
      assertTrue(perms2.contains(perm1));
      assertTrue(perms2.contains(perm2));
    } finally {
      PermissionService.removePermissions(toRemove);
    }
  }

}