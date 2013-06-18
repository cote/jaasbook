package chp02.auth;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;

public class SimpleCallbackHandler implements CallbackHandler {

  private String name;
  private String password;

  public SimpleCallbackHandler(String name, String password) {
    this.name = name;
    this.password = password;
  }

  public void handle(Callback[] callbacks) {
    for (int i = 0; i < callbacks.length; i++) {
      Callback callback = callbacks[i];
      if (callback instanceof NameCallback) {
        NameCallback nameCB = (NameCallback) callback;
        nameCB.setName(name);
      } else if (callback instanceof PasswordCallback) {
        PasswordCallback passwordCB = (PasswordCallback) callback;
        passwordCB.setPassword(password.toCharArray());
      }
    }

  }
}