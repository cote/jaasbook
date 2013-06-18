package chp02;

import java.io.Serializable;
import java.security.Principal;

public class SysAdminPrincipal implements Principal, Serializable {

  private String name;

  public SysAdminPrincipal(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof UserPrincipal)) {
      return false;
    }

    SysAdminPrincipal other = (SysAdminPrincipal) obj;
    return getName().equals(other.getName());
  }

  public int hashCode() {
    return getName().hashCode();
  }

  public String toString() {
    return "(SysAdminPrincipal: name=" + getName() + ")";
  }
}