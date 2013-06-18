package chp07;

import java.io.IOException;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class BundleCallbackHandlerExample {

  private boolean authenticated;

  private CallbackHandler callbackHandler;

  public static void main(String[] args) throws Exception {
    BundleCallbackHandler bundle = new BundleCallbackHandler(
        "mcote", "thepassword");
    LoginContext ctx = new LoginContext("exampleApp", bundle);
    ctx.login();
    Subject subject = ctx.getSubject();
  }

  public boolean login() throws LoginException {
    NameCallback name = new NameCallback("Username:");
    PasswordCallback password = new PasswordCallback("Password:",
        false);
    try {
      callbackHandler.handle(new Callback[] { name, password });
    } catch (IOException e) {
      throw new LoginException(e.getMessage());
    } catch (UnsupportedCallbackException e) {
      throw new LoginException(e.getMessage());
    }
    String username = name.getName();
    String pw = String.valueOf(password.getPassword());
    authenticated = checkPassword(username, pw);
    if (authenticated) {
      return true;
    } else {
      throw new LoginException("User " + name
          + " not authenticated.");
    }
  }

  boolean checkPassword(String username, String password) {
    return false;
  }

}