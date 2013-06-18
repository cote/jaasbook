package chp07;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;

public class UserGroupPrincipalExample {

  private Subject subject;
  private boolean authenticated;
  private String userId;

  public static void main(String[] args) throws Exception {
    LoginContext ctx = new LoginContext("example",
        new BundleCallbackHandler("mcote", "thepassword"));
    ctx.login();
    Subject subj = ctx.getSubject();
    Set groups = subj.getPrincipals(UserGroupPrincipal.class);

  }

  public void commit() {
    if (authenticated) {
      List groupNames = findGroups(userId);
      for (Iterator itr = groupNames.iterator(); itr.hasNext();) {
        String groupName = (String) itr.next();
        UserGroupPrincipal up = new UserGroupPrincipal(groupName);
        subject.getPrincipals().add(up);
      }
    }
  }

  private List findGroups(String userid) {
    return Collections.EMPTY_LIST;
  }

}