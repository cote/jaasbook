package chp06;

import java.security.Permission;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TestablePermission
    extends Permission {

  public TestablePermission(String name) {
    super(name);
  }

  public TestablePermission(String name, String actions) {
    this(name);
    actions_ = actions;
  }

  public boolean implies(Permission permission) {
    return false;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof TestablePermission)) {
      return false;
    }
    TestablePermission other = (TestablePermission) obj;

    EqualsBuilder b = new EqualsBuilder();
    b.append(getName(), other.getName());
    b.append(getActions(), other.getActions());
    return b.isEquals();
  }

  public int hashCode() {
    HashCodeBuilder b = new HashCodeBuilder();
    b.append(getName());
    b.append(getActions());
    return b.toHashCode();
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("(name=");
    buf.append(getName());
    buf.append(", actions=");
    buf.append(getActions());
    buf.append(", class=");
    buf.append(getClass().getName());
    buf.append(")");
    return buf.toString();
  }

  public String getActions() {
    return actions_;
  }

  private String actions_;
}