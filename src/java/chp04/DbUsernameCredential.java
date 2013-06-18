package chp04;

import util.id.Id;

public class DbUsernameCredential {

  private String name;
  private Id id;

  public DbUsernameCredential(Id id, String name) {
    if (name == null || id == null) {
      throw new NullPointerException(
          "name and/or id may not be null.");
    } else {
      this.name = name;
      this.id = id;
    }
  }

  public Id getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int hashCode() {
    return getName().hashCode() * 13 + getId().hashCode() * 13;
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof DbUsernameCredential)) {
      return false;
    } else {
      DbUsernameCredential other = (DbUsernameCredential) obj;
      return getName().equals(other.getName())
          && getId().equals(other.getId());
    }
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("(");
    buf.append("DbUsernameCredential: name=");
    buf.append(getName());
    buf.append(")");
    return buf.toString();
  }
}