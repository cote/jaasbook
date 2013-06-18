package chp04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;

import util.db.DbService;

public class DbConfigurationNoLogging
    extends Configuration {

  static private DbConfiguration dbConfig;

  static public void init() {
    dbConfig = new DbConfiguration();
    Configuration.setConfiguration(dbConfig);
  }

  static public DbConfiguration getDbConfiguration() {
    return dbConfig;
  }

  public void addAppConfigurationEntry(String appName,
      AppConfigurationEntry entry) throws SQLException {
    Connection conn = null;
    try {
      conn = DbService.getInstance().getConnection();
      String sql = "INSERT INTO app_configuration VALUES (?, ?, ?)";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, appName);
      pstmt.setString(2, entry.getLoginModuleName());
      pstmt.setString(3, controlFlagString(entry.getControlFlag()));
      pstmt.executeUpdate();
    } finally {
      if (conn != null) {
        conn.close();
      }
    }
  }

  public boolean deleteAllAppEntries(String appName)
      throws SQLException {
    Connection conn = null;
    try {
      conn = DbService.getInstance().getConnection();
      String sql = "DELETE FROM app_configuration WHERE appName=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, appName);
      return pstmt.executeUpdate() > 0;
    } finally {
      if (conn != null) {
        conn.close();
      }
    }

  }

  public boolean deleteAppConfigurationEntry(String appName,
      String loginModuleName) throws SQLException {
    Connection conn = null;
    try {
      conn = DbService.getInstance().getConnection();
      String sql = "DELETE FROM app_configuration "
          + "WHERE appName=? AND loginModuleClass=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, appName);
      pstmt.setString(2, loginModuleName);
      return pstmt.executeUpdate() > 0;
    } finally {
      if (conn != null) {
        conn.close();
      }
    }
  }

  public AppConfigurationEntry[] getAppConfigurationEntry(
      String applicationName) {
    if (applicationName == null) {
      throw new NullPointerException(
          "applicationName passed in was null.");
    }

    Connection conn = null;
    try {
      conn = DbService.getInstance().getConnection();
      String sql = "SELECT loginModuleClass, controlFlag "
          + "FROM app_configuration WHERE appName=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, applicationName);
      ResultSet rs = pstmt.executeQuery();
      List entries = new ArrayList();
      while (rs.next()) {
        String loginModuleClass = rs.getString("loginModuleClass");
        String controlFlagValue = rs.getString("controlFlag");
        AppConfigurationEntry.LoginModuleControlFlag controlFlag = 
          resolveControlFlag(controlFlagValue);
        AppConfigurationEntry entry = new AppConfigurationEntry(
            loginModuleClass, controlFlag, new HashMap());
        entries.add(entry);
      }

      return (AppConfigurationEntry[]) entries
          .toArray(new AppConfigurationEntry[entries.size()]);
    } catch (SQLException e) {
      throw new RuntimeException(
          "SQLException retrieving for applicationName="
              + applicationName, e);
    } finally {

      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
        // Time to panic!
        }
      }
    }
  }

  public void refresh() {
  }

  static String controlFlagString(LoginModuleControlFlag flag) {
    if (LoginModuleControlFlag.REQUIRED.equals(flag)) {
      return "REQUIRED";
    } else if (LoginModuleControlFlag.REQUISITE.equals(flag)) {
      return "REQUISITE";
    } else if (LoginModuleControlFlag.SUFFICIENT.equals(flag)) {
      return "SUFFICIENT";
    } else if (LoginModuleControlFlag.OPTIONAL.equals(flag)) {
      return "OPTIONAL";
    } else {
      // default if unknown
      return "OPTIONAL";
    }

  }

  static LoginModuleControlFlag resolveControlFlag(String name) {
    if (name == null) {
      throw new NullPointerException(
          "control flag name passed in is null.");
    }

    String uppedName = name.toUpperCase(Locale.US);
    if ("REQUIRED".equals(uppedName)) {
      return LoginModuleControlFlag.REQUIRED;
    } else if ("REQUISITE".equals(uppedName)) {
      return LoginModuleControlFlag.REQUISITE;
    } else if ("SUFFICIENT".equals(uppedName)) {
      return LoginModuleControlFlag.SUFFICIENT;
    } else if ("OPTIONAL".equals(uppedName)) {
      return LoginModuleControlFlag.OPTIONAL;
    } else {
      // default if unknown
      return AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL;
    }
  }

}