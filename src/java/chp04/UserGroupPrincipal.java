package chp04;

import java.security.Principal;

import util.id.Id;

public class UserGroupPrincipal implements Principal {

  private Id id;
  private String name;

  public UserGroupPrincipal(Id id, String name) {
    if (id == null) {
      throw new NullPointerException("Id may not be null.");
    }

    if (name == null || name.length() == 0) {
      throw new NullPointerException(
          "User group name may not be empty.");
    }
    this.id = id;
    this.name = name;
  }

  public Id getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof UserGroupPrincipal)) {
      return false;
    }
    UserGroupPrincipal other = (UserGroupPrincipal) obj;
    return getName().equals(other.getName())
        && getId().equals(other.getId());
  }

  public int hashCode() {
    return getName().hashCode() * 27 + getId().hashCode() * 27;
  }
}