package chp03;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class ExampleLoginModule implements LoginModule {

  private Subject subject;
  private CallbackHandler handler;
  private Map sharedState;
  private Map option;
  private boolean userAuthenticated;
  private String username;
  private Boolean debug;
  private Object caching;

  public void initialize(Subject subject,
      CallbackHandler callbackHandler, Map sharedState, Map options) {
    this.subject = subject;
    this.handler = callbackHandler;
    this.sharedState = sharedState;
    this.option = options;
    this.debug = Boolean.valueOf((String)options.get("debug"));
    this.caching = options.get("caching");
  }

  public boolean login() throws LoginException {
    NameCallback name = new NameCallback("Username:");
    PasswordCallback password = new PasswordCallback("Password",
        false);
    try {
      handler.handle(new Callback[] { name, password });
    } catch (IOException e) {
      LoginException ex = new LoginException(
          "IO error getting credentials: " + e.getMessage());
      e.initCause(e);
      throw ex;
    } catch (UnsupportedCallbackException e) {
      LoginException ex = new LoginException(
          "UnsupportedCallback: " + e.getMessage());
      e.initCause(e);
      throw ex;
    }

    String usernameText = name.getName();
    String passwordText = String.valueOf(password.getPassword());

    userAuthenticated = UserService.checkPassword(usernameText,
        passwordText);

    if (userAuthenticated) {
      username = usernameText;
      return true;
    } else {
      throw new FailedLoginException("Username/Password for "
          + usernameText + " incorrect.");
    }
  }

  public boolean commit() {
    if (userAuthenticated) {
      Set groups = UserService.findGroups(username);
      for (Iterator itr = groups.iterator(); itr.hasNext();) {
        String groupName = (String) itr.next();
        UserGroupPrincipal group = new UserGroupPrincipal(groupName);
        subject.getPrincipals().add(group);
      }

      UsernameCredential cred = new UsernameCredential(username);
      subject.getPublicCredentials().add(cred);
    }
    // either way, cleanup
    username = null;
    return true;
  }

  public boolean abort() {
    username = null;
    return true;
  }

  public boolean logout() {
    if (!subject.isReadOnly()) {
      Set principals = subject
          .getPrincipals(UserGroupPrincipal.class);
      subject.getPrincipals().removeAll(principals);
      Set creds = subject
          .getPublicCredentials(UsernameCredential.class);
      subject.getPublicCredentials().removeAll(creds);
      return true;
    } else {
      return false;
    }
  }

}