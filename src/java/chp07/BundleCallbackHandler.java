package chp07;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class BundleCallbackHandler implements CallbackHandler {

  private static final char[] EMPTY_CHARS = new char[0];
  private Map callbackValues = new HashMap();

  public BundleCallbackHandler() {
  }

  public BundleCallbackHandler(String username, String password) {
    setName(username);
    setPassword(password);
  }

  public void handle(Callback[] callbacks) throws IOException,
      UnsupportedCallbackException {
    if (callbacks == null || callbacks.length == 0) {
      return;
    }
    for (int i = 0; i < callbacks.length; i++) {
      Callback c = callbacks[i];
      handleCallback(c);
    }

  }

  protected boolean handleCallback(Callback callback) {
    if (callback instanceof NameCallback) {
      NameCallback c = (NameCallback) callback;
      if (callbackValues.containsKey(NameCallback.class)) {
        String name = (String) callbackValues
            .get(NameCallback.class);
        c.setName(name);
        return true;
      } else {
        return false;
      }
    } else if (callback instanceof PasswordCallback) {
      PasswordCallback c = (PasswordCallback) callback;

      if (callbackValues.containsKey(PasswordCallback.class)) {
        String password = (String) callbackValues
            .get(PasswordCallback.class);
        if (password == null) {
          c.setPassword(EMPTY_CHARS);
        } else {
          c.setPassword(password.toCharArray());
        }
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public void setName(String name) {
    callbackValues.put(NameCallback.class, name);
  }

  public void setPassword(String password) {
    callbackValues.put(PasswordCallback.class, password);
  }

}