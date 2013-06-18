package chp04;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;

public class DbCallbackHandler implements CallbackHandler {

  private String username;
  private String password;

  public DbCallbackHandler(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public void handle(Callback[] callbacks) {
    for (int i = 0; i < callbacks.length; i++) {
      Callback callback = callbacks[i];
      if (callback instanceof NameCallback) {
        NameCallback nameCB = (NameCallback) callback;
        nameCB.setName(username);
      } else if (callback instanceof PasswordCallback) {
        PasswordCallback passwordCB = (PasswordCallback) callback;
        passwordCB.setPassword(password.toCharArray());
      }
    }
  }
}