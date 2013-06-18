package chp07;

import junit.framework.TestCase;

public class ZActionsPermissionTest
    extends TestCase {

  public void testImplies_name_identity() {
    TestActionsPermission p1 = new TestActionsPermission("name",
        null);
    TestActionsPermission p2 = new TestActionsPermission("name",
        null);
    assertTrue(p1.implies(p1));
    assertTrue(p1.implies(p2));
    assertTrue(p2.implies(p1));
  }

  public void testImplies_name_identity_bad() {
    TestActionsPermission p1 = new TestActionsPermission("name",
        null);
    TestActionsPermission p2 = new TestActionsPermission("name1",
        null);
    assertTrue(p1.implies(p1));
    assertFalse(p1.implies(p2));
    assertFalse(p2.implies(p1));
  }

  public void testImplies_actions_identity() {
    TestActionsPermission p1 = new TestActionsPermission("name",
        "create, read");
    TestActionsPermission p2 = new TestActionsPermission("name",
        "read,create");
    assertTrue(p1.implies(p1));
    assertTrue(p1.implies(p2));
    assertTrue(p2.implies(p1));
  }

  public void testImplies_actions_identity_subset() {
    TestActionsPermission p1 = new TestActionsPermission("name",
        "create");
    TestActionsPermission p2 = new TestActionsPermission("name",
        "read,create");
    assertTrue(p1.implies(p1));
    assertFalse(p1.implies(p2));
    assertTrue(p2.implies(p1));
  }

  public void testImplies_example() {
    TestActionsPermission superSet = new TestActionsPermission(
        "name", "create, read");
    TestActionsPermission subSet = new TestActionsPermission(
        "name", "create");

    assertTrue(superSet.implies(subSet));
  }

  class TestActionsPermission
      extends ActionsPermission {

    public TestActionsPermission(String name, String actions) {
      super(name, actions);
    }

  }
}