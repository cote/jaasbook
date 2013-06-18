package chp03;

import java.security.Principal;

public class UserGroupPrincipal implements Principal {
  private String name;

  public UserGroupPrincipal(String name) {
    if (name == null || name.length() == 0) {
      throw new NullPointerException(
          "User group name may not be empty.");
    }
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof UserGroupPrincipal)) {
      return false;
    }
    UserGroupPrincipal other = (UserGroupPrincipal) obj;
    return getName().equals(other.getName());
  }

  public int hashCode() {
    return getName().hashCode();
  }

}