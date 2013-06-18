package chp03;

public class UsernameCredential {
  private String name;

  public UsernameCredential(String name) {
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
    if (!(obj instanceof UsernameCredential)) {
      return false;
    }
    UsernameCredential other = (UsernameCredential) obj;
    return getName().equals(other.getName());
  }

  public int hashCode() {
    return getName().hashCode();
  }

}