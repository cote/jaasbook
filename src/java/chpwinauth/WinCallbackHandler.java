package chpwinauth;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class WinCallbackHandler implements CallbackHandler
{

  public WinCallbackHandler(String username, String password, String domain)
  {
    username_ = username;
    password_ = password;
    domain_ = domain;
  }

  public void handle(Callback[] callbacks) throws IOException,
      UnsupportedCallbackException
  {
    if (callbacks == null || callbacks.length == 0) { return; }
    for (int i = 0; i < callbacks.length; i++)
    {
      Callback callback = callbacks[i];

      if (callback instanceof NameCallback)
      {
        NameCallback c = (NameCallback) callback;
        c.setName(username_);
      }
      else if (callback instanceof PasswordCallback)
      {
        PasswordCallback c = (PasswordCallback) callback;
        c.setPassword(password_.toCharArray());
      }
      else if (callback instanceof TextInputCallback)
      {
        TextInputCallback c = (TextInputCallback) callback;
        c.setText(domain_);
      }
    }
  }

  private String username_;

  private String password_;

  private String domain_;
}
