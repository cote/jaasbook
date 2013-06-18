package chp09;

import javax.security.auth.login.LoginException;

import chp04.DbLoginModule;

public class TomcatLoginModule
    extends DbLoginModule {

  public boolean commit() throws LoginException {
    if (super.commit()) {
      UserPrincipal userP = new UserPrincipal(getUsername());
      getSubject().getPrincipals().add(userP);
      getPrincipalsAdded().add(userP);
      return true;
    } else {
      return false;
    }
  }
}