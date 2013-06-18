package chp02.auth;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import chp02.SysAdminPrincipal;
import chp02.UserPrincipal;

public class SimpleLoginModule implements LoginModule {

  private Subject subject;
  private CallbackHandler callbackHandler;
  private String name;
  private String password;

  public void initialize(Subject subject,
      CallbackHandler callbackHandler, Map sharedState, Map options) {
    this.subject = subject;
    this.callbackHandler = callbackHandler;
  }

  public boolean login() throws LoginException {
    // Each callback is responsible for collecting a credential
    // needed to authenticate the user.
    NameCallback nameCB = new NameCallback("Username");
    PasswordCallback passwordCB = new PasswordCallback("Password",
        false);
    Callback[] callbacks = new Callback[] { nameCB, passwordCB };
    // Delegate to the provided CallbackHandler to gather the
    // username and password.
    try {
      callbackHandler.handle(callbacks);
    } catch (IOException e) {
      e.printStackTrace();
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

    // Now that the CallbackHandler has gathered the username and password,
    // use them to authenticate the user against the expected passwords.
    name = nameCB.getName();
    password = String.valueOf(passwordCB.getPassword());

    if ("sysadmin".equals(name) && "password".equals(password)) {
      // login in sysadmin
      return true;
    } else if ("user".equals(name) && "password".equals(password)) {
      // login user
      return true;
    } else {
      return false;
    }
  }

  public boolean commit() {
    // If this method is called, the user successfully authenticated, and
    // we can add the appropriate Principles to the Subject.
    if ("sysadmin".equals(name)) {
      Principal p = new SysAdminPrincipal(name);

      subject.getPrincipals().add(p);
      password = null;
      return true;
    } else if ("user".equals(name)) {
      Principal p = new UserPrincipal(name);
      subject.getPrincipals().add(p);
      password = null;
      return true;
    } else {
      return false;
    }
  }

  public boolean abort() {
    name = null;
    password = null;
    return true;
  }

  public boolean logout() {
    name = null;
    password = null;
    return true;
  }

}