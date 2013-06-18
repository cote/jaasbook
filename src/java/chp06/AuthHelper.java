package chp06;

import java.sql.SQLException;
import java.util.Collections;

import javax.security.auth.Subject;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import util.id.Id;
import chp04.DbCallbackHandler;
import chp04.DbConfiguration;
import chp04.DbLoginModule;
import chp04.UserGroupPrincipal;
import chp04.UserService;

public class AuthHelper {

  private Id userId;
  private String username;
  private String password;
  private Subject subject;
  private UserGroupPrincipal userGrp;

  public void loginTestUser() throws LoginException {
    LoginContext ctx = new LoginContext("simpleDb",
        new DbCallbackHandler(username, password));
    ctx.login();

    Subject subj = ctx.getSubject();
    subj.setReadOnly();
    this.subject = subj;
  }

  public void createTestUser(String username, String password)
      throws SQLException {
    this.username = username;
    this.password = password;
    Id userId = UserService.addUser(this.username, this.password);
    Id grpId = Id.create();
    UserGroupPrincipal userGrp = new UserGroupPrincipal(grpId,
        "testgrp");
    UserService.addUserGroup(userGrp);
    UserService.addToUserGroup(userId, grpId);
    this.userId = userId;
    this.userGrp = userGrp;

    // set Configuration
    DbConfiguration.init();
    // create DbLoginModule entry
    String appName = "simpleDb";
    DbConfiguration dbConfig = DbConfiguration.getDbConfiguration();

    AppConfigurationEntry appEntry = new AppConfigurationEntry(
        DbLoginModule.class.getName(),
        AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
        Collections.EMPTY_MAP);
    dbConfig.deleteAppConfigurationEntry(appName, appEntry
        .getLoginModuleName());
    dbConfig.addAppConfigurationEntry(appName, appEntry);

  }

  public void cleanUp() throws SQLException, LoginException {
    if (userGrp != null) {
      UserService.removeUserGroup(userGrp.getId());
      userGrp = null;
      username = null;
      password = null;
    }

    if (userId != null) {
      UserService.removeUser(userId);
      userId = null;
    }

    if (subject != null) {
      LoginContext ctx = new LoginContext("simpleDb", subject);
      ctx.logout();
      subject = null;
    }
  }

  public String getPassword() {
    return password;
  }

  public Subject getSubject() {
    return subject;
  }

  public UserGroupPrincipal getUserGrp() {
    return userGrp;
  }

  public Id getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

}