package chp04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.security.auth.login.AppConfigurationEntry;

import util.TestableAppConfigurationEntry;

import junit.framework.TestCase;

/**
 * @author michaelcote
 */
public class ZDbConfigurationTest extends TestCase
{

  public void testGetAppConfigurationEntry() throws Exception
  {
    DbConfiguration conf = new DbConfiguration();

    boolean passed = false;
    try
    {
      conf.getAppConfigurationEntry(null);
    }
    catch (NullPointerException e)
    {
      passed = true;
    }

    assertTrue("Should have thrown NullPointerException", passed);

    try
    {
      // create two different app sets, make sure we only get the correct
      // loginmodules back
      AppConfigurationEntry entry1 = new TestableAppConfigurationEntry(
          "module01", AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL,
          Collections.EMPTY_MAP);

      AppConfigurationEntry entry2 = new TestableAppConfigurationEntry(
          "module02", AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
          Collections.EMPTY_MAP);

      conf.addAppConfigurationEntry("testApp1", entry1);
      conf.addAppConfigurationEntry("testApp1", entry2);

      AppConfigurationEntry entry3 = new TestableAppConfigurationEntry(
          "module03", AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
          Collections.EMPTY_MAP);

      conf.addAppConfigurationEntry("testApp2", entry3);

      AppConfigurationEntry[] entries1 = conf.getAppConfigurationEntry("testApp1");
      assertNotNull(entries1);
      assertEquals(2, entries1.length);
      List entriesList1 = TestableAppConfigurationEntry.makeTestable(entries1);
      List expectedEntries1 = new ArrayList(2);
      expectedEntries1.add(entry1);
      expectedEntries1.add(entry2);
      assertEquals(expectedEntries1, entriesList1);

      AppConfigurationEntry[] entries2 = conf.getAppConfigurationEntry("testApp2");
      assertNotNull(entries2);
      assertEquals(1, entries2.length);
      List entriesList2 = TestableAppConfigurationEntry.makeTestable(entries2);
      List expectedEntries2 = new ArrayList(1);
      expectedEntries2.add(entry3);
      assertEquals(expectedEntries2, entriesList2);

    }
    finally
    {
      // clean up
      conf.deleteAllAppEntries("testApp1");
      conf.deleteAllAppEntries("testApp2");
    }
  }

  public void testAddAppConfigurationEntry() throws Exception
  {
    DbConfiguration conf = new DbConfiguration();
    try
    {
      AppConfigurationEntry entry = new AppConfigurationEntry(
          UserGroupPrincipal.class.getName(),
          AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL,
          Collections.EMPTY_MAP);

      conf.addAppConfigurationEntry("testAppName", entry);

      AppConfigurationEntry[] entries = conf.getAppConfigurationEntry("testAppName");

      assertNotNull(entries);
      assertEquals(1, entries.length);

      assertEquals(entry.getControlFlag(), entries[0].getControlFlag());
      assertEquals(entry.getLoginModuleName(), entries[0].getLoginModuleName());
      assertEquals(entry.getOptions(), entries[0].getOptions());
    }
    finally
    {
      // cleanup
      conf.deleteAllAppEntries("testAppName");
    }
  }

  public void testDeleteAppConfigurationEntry() throws Exception
  {
    DbConfiguration conf = new DbConfiguration();
    try
    {
      AppConfigurationEntry entry1 = new AppConfigurationEntry("loginModule1",
          AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL,
          Collections.EMPTY_MAP);

      AppConfigurationEntry entry2 = new AppConfigurationEntry("loginModule2",
          AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL,
          Collections.EMPTY_MAP);

      conf.addAppConfigurationEntry("testAppName", entry1);
      conf.addAppConfigurationEntry("testAppName", entry2);

      AppConfigurationEntry[] entries = conf.getAppConfigurationEntry("testAppName");

      assertNotNull(entries);
      assertEquals(2, entries.length);

      conf.deleteAppConfigurationEntry("testAppName",
          entry2.getLoginModuleName());

      entries = conf.getAppConfigurationEntry("testAppName");

      assertNotNull(entries);
      assertEquals(1, entries.length);

    }
    finally
    {
      // cleanup
      conf.deleteAllAppEntries("testAppName");
    }
  }

  public void testResolveControlFlag()
  {

    assertEquals(AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL,
        DbConfiguration.resolveControlFlag("OPTIONAL"));

    assertEquals(AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
        DbConfiguration.resolveControlFlag("REQUIRED"));

    assertEquals(AppConfigurationEntry.LoginModuleControlFlag.REQUISITE,
        DbConfiguration.resolveControlFlag("REQUISITE"));

    assertEquals(AppConfigurationEntry.LoginModuleControlFlag.SUFFICIENT,
        DbConfiguration.resolveControlFlag("SUFFICIENT"));

  }

  public void testControlFlagString()
  {
    assertEquals(
        "OPTIONAL",
        DbConfiguration.controlFlagString(AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL));

    assertEquals(
        "REQUIRED",
        DbConfiguration.controlFlagString(AppConfigurationEntry.LoginModuleControlFlag.REQUIRED));

    assertEquals(
        "REQUISITE",
        DbConfiguration.controlFlagString(AppConfigurationEntry.LoginModuleControlFlag.REQUISITE));

    assertEquals(
        "SUFFICIENT",
        DbConfiguration.controlFlagString(AppConfigurationEntry.LoginModuleControlFlag.SUFFICIENT));

  }

}