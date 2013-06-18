package chp07;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.TextInputCallback;

public class WindowsCallbackHandler
    extends BundleCallbackHandler {

  private String domain;

  public WindowsCallbackHandler(String username, String password,
      String domain) {
    super(username, password);
    this.domain = domain;
  }

  protected boolean handleCallback(Callback callback) {
    if (!super.handleCallback(callback)) {
      if (callback instanceof TextInputCallback) {
        TextInputCallback c = (TextInputCallback) callback;
        c.setText(domain);
        return true;
      }

      return false;
    }

    return false;
  }

}