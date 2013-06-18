package chp06;

import java.security.BasicPermission;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import util.id.Id;

public class DbPermission
    extends BasicPermission {
  private Id id;

  public DbPermission(Id id, String name, String actions) {
    super(name);
    if (id == null) {
      throw new NullPointerException("Id may not be null.");
    }
    this.id = id;
  }

  public DbPermission(Id id, String name) {
    this(id, name, null);
  }

  public int hashCode() {
    HashCodeBuilder b = new HashCodeBuilder();
    b.append(getName());
    b.append(getActions());
    b.append(getId());
    return b.toHashCode();
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof DbPermission)) {
      return false;
    }

    DbPermission other = (DbPermission) obj;
    EqualsBuilder b = new EqualsBuilder();
    b.append(getName(), other.getName());
    b.append(getActions(), other.getActions());
    b.append(getId(), other.getId());
    return b.isEquals();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("(name=");
    buf.append(getName());
    buf.append(", id=");
    buf.append(getId().getId());
    buf.append(", actions=");
    buf.append(getActions());
    buf.append(")");
    return buf.toString();
  }

  public Id getId() {
    return id;
  }

}