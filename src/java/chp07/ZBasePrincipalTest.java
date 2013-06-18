package chp07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

public class ZBasePrincipalTest
    extends TestCase {

  public void testEquals() {
    TestBasePrincipal test1 = new TestBasePrincipal("samename");
    AnotherTestPrincipal test2 = new AnotherTestPrincipal(
        "samename");
    assertTrue(test1.equals(test1));
    assertTrue(test2.equals(test2));

    assertFalse(test1.equals(test2));
    assertFalse(test2.equals(test1));

    ChildTestBasePrincipal child = new ChildTestBasePrincipal(
        "samename");
    assertFalse(test1.equals(child));
  }

  public void testCompareTo() {
    AnotherTestPrincipal test1 = new AnotherTestPrincipal("a name");
    AnotherTestPrincipal test2 = new AnotherTestPrincipal(
        "samename");
    ChildTestBasePrincipal test3 = new ChildTestBasePrincipal(
        "samename");
    TestBasePrincipal test4 = new TestBasePrincipal("samename");

    List toSort = new ArrayList(4);
    toSort.add(test2);
    toSort.add(test4);
    toSort.add(test3);
    toSort.add(test1);

    List expected = new ArrayList(4);
    expected.add(test1);
    expected.add(test2);
    expected.add(test3);
    expected.add(test4);

    Collections.sort(toSort);
    assertEquals(expected, toSort);
  }

}

class TestBasePrincipal
    extends BasePrincipal {

  public TestBasePrincipal(String name) {
    super(name);
  }

}

class ChildTestBasePrincipal
    extends TestBasePrincipal {

  public ChildTestBasePrincipal(String name) {
    super(name);
  }

}

class AnotherTestPrincipal
    extends BasePrincipal {

  public AnotherTestPrincipal(String name) {
    super(name);
  }

}