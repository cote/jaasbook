package chp11.uiobjects;

import java.util.ArrayList;
import java.util.List;

public class UserRow {

  public String getId() {
    return id_;
  }

  public void setId(String id) {
    id_ = id;
  }

  public String getUsername() {
    return username_;
  }

  public void setUsername(String username) {
    username_ = username;
  }

  public boolean isAdmin() {
    return admin_;
  }

  public void setAdmin(boolean admin) {
    admin_ = admin;
  }

  public boolean isCustomer() {
    return customer_;
  }

  public void setCustomer(boolean customer) {
    customer_ = customer;
  }

  private String username_;
  private String id_;
  private boolean admin_;
  private boolean customer_;

}
