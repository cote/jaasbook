package chp06;

import util.id.Id;
import chp04.UserGroupPrincipal;

public class SuperUserPrincipal
    extends UserGroupPrincipal {

  public SuperUserPrincipal(Id id, String name) {
    super(id, name);
  }

}