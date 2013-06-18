package chp03;

import java.security.Principal;

public class UserPrincipal implements Principal {

  private String name;

  public UserPrincipal(String name) {
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
    if (!(obj instanceof UserPrincipal)) {
      return false;
    }
    UserPrincipal other = (UserPrincipal) obj;
    return getName().equals(other.getName());
  }

  public int hashCode() {
    return getName().hashCode();
  }

}