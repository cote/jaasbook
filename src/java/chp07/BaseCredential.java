package chp07;

public abstract class BaseCredential implements Comparable {

  private String value;

  public BaseCredential(String name) {
    if (name == null) {
      throw new NullPointerException("Name may not be null.");
    }

    value = name;
  }

  public String getValue() {
    return value;
  }

  public int hashCode() {
    return value.hashCode() * 29 + getClass().hashCode() * 29;
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!getClass().equals(obj.getClass())) {
      return false;
    }

    BasePrincipal other = (BasePrincipal) obj;

    if (!getValue().equals(other.getName())) {
      return false;
    }

    return true;
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("(");
    buf.append(getClass().getName());
    buf.append(": value=");
    buf.append(getValue());
    buf.append(")");
    return buf.toString();
  }

  public int compareTo(Object obj) {
    BaseCredential other = (BaseCredential) obj;
    int classComp = getClass().getName().compareTo(
        other.getClass().getName());
    if (classComp == 0) {
      return getValue().compareTo(other.getValue());
    } else {
      return classComp;
    }
  }

}