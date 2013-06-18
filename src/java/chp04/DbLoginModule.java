package chp04;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import util.db.DbService;
import util.id.Id;

public class DbLoginModule implements LoginModule {

  private Subject subject;
  private CallbackHandler callbackHandler;
  private Map sharedState = Collections.EMPTY_MAP;
  private Map options = Collections.EMPTY_MAP;
  private Set principalsAdded;
  private boolean authenticated;
  private String username;
  private Id userId;
  private String password;

  public void initialize(Subject subject,
      CallbackHandler callbackHandler, Map sharedState, Map options) {
    this.subject = subject;
    this.callbackHandler = callbackHandler;
    this.sharedState = sharedState;
    this.options = options;
  }

  public boolean login() throws LoginException {
    NameCallback nameCB = new NameCallback("Username");
    PasswordCallback passwordCB = new PasswordCallback("Password",
        false);
    Callback[] callbacks = new Callback[] { nameCB, passwordCB };
    try {
      callbackHandler.handle(callbacks);
    } catch (IOException e) {
      LoginException ex = new LoginException(
          "IOException logging in.");
      ex.initCause(e);
      throw ex;
    } catch (UnsupportedCallbackException e) {
      String className = e.getCallback().getClass().getName();
      LoginException ex = new LoginException(className
          + " is not a supported Callback.");
      ex.initCause(e);
      throw ex;
    }

    // Authenticate username/password
    username = nameCB.getName();
    password = String.valueOf(passwordCB.getPassword());

    //
    // lookup credentials
    //
    Connection conn = null;
    try {
      conn = DbService.getInstance().getConnection();
      String sql = "SELECT id, password FROM db_user "
          + "WHERE username = ?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, username);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        String idStr = rs.getString("id");
        userId = Id.create(idStr);
        String storedPassword = rs.getString("password");
        if (storedPassword == null && password == null) {
          authenticated = true;
        } else if (storedPassword != null
            && storedPassword.equals(password)) {
          authenticated = true;
        } else {
          authenticated = false;
        }
      } else {
        // user does not exist...
        authenticated = false;
      }
    } catch (SQLException e) {
      LoginException ex = new LoginException(
          "SQLException logging in user " + username);
      ex.initCause(e);
      throw ex;
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          LOGGER.logp(Level.WARNING, CLASS_NAME, "login",
              "Could not close connection: {0}", e.getMessage());
        }
      }
    }
    return authenticated;
  }

  public boolean commit() throws LoginException {
    if (!authenticated) {
      LOGGER.logp(Level.FINEST, CLASS_NAME, "commit()",
          "{0} not authenticated.", getUsername());
      return false;
    }
    // set credential
    DbUsernameCredential cred = new DbUsernameCredential(userId,
        username);
    subject.getPublicCredentials().add(cred);
    // lookup user groups, add to Subject
    Set principals = lookupGroups(username);
    LOGGER.logp(Level.FINEST, CLASS_NAME, "commit()",
        "Loaded Principals {0}", principals);
    subject.getPrincipals().addAll(principals);
    principalsAdded = new HashSet();
    principalsAdded.addAll(principals);
    return true;
  }

  static private Set lookupGroups(String username)
      throws LoginException {
    Set principals = new HashSet();

    Connection conn = null;
    try {
      conn = DbService.getInstance().getConnection();
      String sql =  "SELECT principal.id, principal.name, " +
          "principal.class " +
          "FROM db_user, principal_db_user, principal " +
          "WHERE db_user.username = ? " +
          "AND principal_db_user.user_id=db_user.id " +
          "AND principal.id=principal_db_user.principal_id";
      PreparedStatement pstmt = conn
          .prepareStatement(sql);
      pstmt.setString(1, username);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        String idStr = rs.getString("id");
        Id groupId = Id.create(idStr);
        String groupName = rs.getString("name");
        String className = rs.getString("class");
        if (USR_GRP_CLASS.equals(className)) {
          UserGroupPrincipal grp = new UserGroupPrincipal(groupId,
              groupName);
          principals.add(grp);
        } else {
          Principal p = resolvePrincipal(groupName, className);
          if (p != null) {
            principals.add(p);
          }
        }
      }
    } catch (SQLException e) {
      LoginException ex = new LoginException(
          "SQLException logging in user " + username);
      ex.initCause(e);
      throw ex;
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        LOGGER.logp(Level.WARNING, CLASS_NAME, "commit",
            "Could not close connection: {0}", e.getMessage());
      }
    }

    return principals;
  }

  private static Principal resolvePrincipal(String groupName,
      String className) {
    Exception e = null;
    try {
      Class clazz = Class.forName(className);
      if (Principal.class.isAssignableFrom(clazz)) {
        Constructor c = clazz.getConstructor(STR_ARG);

        return (Principal) c
            .newInstance(new Object[] { groupName });
      }
    } catch (ClassNotFoundException ex) {
      e = ex;
    } catch (SecurityException ex) {
      e = ex;
    } catch (NoSuchMethodException ex) {
      e = ex;
    } catch (IllegalArgumentException ex) {
      e = ex;
    } catch (InstantiationException ex) {
      e = ex;
    } catch (IllegalAccessException ex) {
      e = ex;
    } catch (InvocationTargetException ex) {
      e = ex;
    }

    LOGGER
        .logp(
            Level.WARNING,
            CLASS_NAME,
            "resolvePrincipal()",
            "Class {0} specified for Principal with name {1} not found.",
            new Object[] { className, groupName });

    return null;
  }

  public boolean abort() {
    username = null;
    password = null;
    authenticated = false;
    return true;
  }

  public boolean logout() throws LoginException {
    //
    // Remove usergroup principals
    //

    if (principalsAdded != null && !principalsAdded.isEmpty()) {
      subject.getPrincipals().removeAll(principalsAdded);
    }
    return true;
  }

  protected boolean isAuthenticated() {
    return authenticated;
  }

  protected Subject getSubject() {
    return subject;
  }

  protected Set getPrincipalsAdded() {
    return principalsAdded;
  }

  protected String getUsername() {
    return username;
  }

  static private final String USR_GRP_CLASS = UserGroupPrincipal.class
      .getName();

  static private final Class[] STR_ARG = new Class[] { String.class };

  static public final String CLASS_NAME = DbLoginModule.class
      .getName();

  static public final Logger LOGGER = Logger.getLogger(CLASS_NAME);

}