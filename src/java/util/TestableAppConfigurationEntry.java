package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.AppConfigurationEntry;

/**
 * Decorator for {@link AppConfigurationEntry}instances that provides member
 * comparision for equals() and hashCode(). This can be used in
 * {@link junit.framework.TestCase}s when you want to use
 * {@link junit.framework.Assert#assertEquals(Object, Object)}.
 * 
 * @author michaelcote
 */
public class TestableAppConfigurationEntry extends AppConfigurationEntry
    implements Comparable
{

  public TestableAppConfigurationEntry(AppConfigurationEntry wrap)
  {
    this(wrap.getLoginModuleName(), wrap.getControlFlag(), new HashMap(
        wrap.getOptions()));
  }

  public TestableAppConfigurationEntry(String loginModuleName,
      LoginModuleControlFlag controlFlag, Map options)
  {
    super(loginModuleName, controlFlag, options);
    if (loginModuleName == null) { throw new NullPointerException(
        "loginModuleName may not be null."); }
    if (controlFlag == null) { throw new NullPointerException(
        "controlFlag may not be null."); }
    if (options == null) { throw new NullPointerException(
        "options may be null."); }
  }

  /**
   * Converts the passed in AppConfigurationEntry array to a sorted
   * List of TestableAppConfigurationEntrys.
   * @param entries array to conver
   * @return List of TestableAppConfigurationEntrys sorted in natural order
   * by login module name.
   */
  static public List makeTestable(AppConfigurationEntry[] entries)
  {
    List toReturn = new ArrayList();
    if (entries == null || entries.length == 0) { return toReturn; }

    for (int i = 0; i < entries.length; i++)
    {
      TestableAppConfigurationEntry t = new TestableAppConfigurationEntry(
          entries[i]);
      toReturn.add(t);
    }

    Collections.sort(toReturn);
    return toReturn;
  }

  public boolean equals(Object obj)
  {
    if (obj == this) { return true; }

    if (!(obj instanceof TestableAppConfigurationEntry)) { return false; }

    TestableAppConfigurationEntry other = (TestableAppConfigurationEntry) obj;
    return getLoginModuleName().equals(other.getLoginModuleName())
        && getControlFlag().equals(other.getControlFlag())
        && getOptions().equals(other.getOptions());
  }

  public int hashCode()
  {
    int h = 1;
    h = h * 17 + getLoginModuleName().hashCode();
    h = h * 17 + getControlFlag().hashCode();
    h = h * 17 + getOptions().hashCode();
    return h;
  }

  public String toString()
  {
   StringBuffer buf = new StringBuffer();
   buf.append("(loginModuleName=");
   buf.append(getLoginModuleName());
   buf.append(", controlFlag=");
   buf.append(getControlFlag());
   buf.append(", options=");
   buf.append(getOptions());
   buf.append(")");
   return buf.toString();
  }
  
  /**
   * Compares TestableAppConfigurationEntry by their LoginModule names
   * 
   * @param o
   *          the other TestableAppConfigurationEntry
   * @return negative int, 0, or positive int if this object is less than, equal
   *         to, or greater than the object passed in
   */
  public int compareTo(Object o)
  {
    TestableAppConfigurationEntry other = (TestableAppConfigurationEntry) o;

    return getLoginModuleName().compareTo(other.getLoginModuleName());
  }

}