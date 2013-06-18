package chp04;

import java.security.Principal;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class Main {

  public static void main(String[] args) throws Exception {
    // set Configuration
    DbConfiguration.init();
    // create DbLoginModule entry
    String appName = "simpleDb";
    DbConfiguration dbConfig = DbConfiguration.getDbConfiguration();

    AppConfigurationEntry appEntry = new AppConfigurationEntry(
        DbLoginModule.class.getName(),
        AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
        Collections.EMPTY_MAP);
    dbConfig.deleteAppConfigurationEntry(appName, appEntry
        .getLoginModuleName());
    dbConfig.addAppConfigurationEntry(appName, appEntry);
    // create LoginContext, login
    String username = "mcote";
    String password = "secret";
    LoginContext ctx = new LoginContext(appName,
        new DbCallbackHandler(username, password));
    // authenticate user
    boolean authenticated = true;
    try {
      ctx.login();
    } catch (LoginException e) {
      e.printStackTrace();
      authenticated = false;
    }
    if (authenticated) {
      // print username
      Subject subject = ctx.getSubject();
      Set creds = subject
          .getPublicCredentials(DbUsernameCredential.class);
      System.out.println("Subject's username: "
          + creds.iterator().next());
      // print principals
      for (Iterator itr = subject.getPrincipals().iterator(); itr
          .hasNext();) {
        Principal p = (Principal) itr.next();
        System.out.println("Principal: " + p.getName());
      }
    } else {
      System.out.println("Did not authenticate " + username);
    }
  }

}