package chp02;

import java.io.File;
import java.security.PrivilegedAction;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import chp02.auth.SimpleCallbackHandler;

public class Chp02Main {

  public static void main(String[] args) throws Exception {

    File policyFile = new File("build/conf/chp02.policy");

    testAccess(policyFile, "user", "password");
    testAccess(policyFile, "sysadmin", "password");
  }

  static void testAccess(final File policyFile,
      final String username, final String password)
      throws LoginException {
    //  Login a user
    SimpleCallbackHandler cb = new SimpleCallbackHandler(username,
        password);
    LoginContext ctx = new LoginContext("chp02", cb);
    ctx.login();
    Subject subject = ctx.getSubject();
    System.out.println("Logged in " + subject);

    // Create privileged action block which limits permissions
    // to only the Subject's permissions.
    try {
      Subject.doAsPrivileged(subject, new PrivilegedAction() {

        public Object run() {
          policyFile.canRead();
          System.out.println(username + " can access Policy file.");
          return null;
        }
      }, null);
    } catch (SecurityException e) {
      System.out.println(username + " can NOT access Policy file.");
    }

  }

}