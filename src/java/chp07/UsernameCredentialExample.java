package chp07;

import java.util.Set;
import java.util.logging.Logger;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;

public class UsernameCredentialExample {

  private boolean authenticated;
  private Subject subject;
  private String username;

  public static void main(String[] args) throws Exception {
    LoginContext ctx = new LoginContext("exampleApp",
        new BundleCallbackHandler("mcote", "thepassword"));
    ctx.login();
    Subject subject = ctx.getSubject();
    Set usernames = subject.getPublicCredentials(UsernameCredential.class);
    if (usernames.size() > 1) {
      LOGGER
          .warning("More than one UsernameCredential found.");
    } else {
      UsernameCredential username = (UsernameCredential) usernames
          .iterator().next();
    }

  }

  public void commit() {
    //...other code to add Princiapls...

    if (authenticated) {
      UsernameCredential cred = new UsernameCredential(username);
    }

  }

  static private final Logger LOGGER = Logger
      .getLogger(UsernameCredentialExample.class.getName());

}