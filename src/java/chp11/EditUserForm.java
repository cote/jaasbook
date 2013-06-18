package chp11;

import org.apache.struts.action.ActionForm;

import chp11.uiobjects.UserRow;

public class EditUserForm
    extends ActionForm {

  public String getId() {
    return id_;
  }

  public void setId(String id) {
    id_ = id;
  }

  public UserRow getUserRow() {
    return userRow_;
  }

  public void setUserRow(UserRow userRow) {
    userRow_ = userRow;
  }

  private String id_;
  private UserRow userRow_;

}
