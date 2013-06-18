package chp11;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ListUsersForm
    extends ActionForm {

  public List getUserRows() {
    return userRows_;
  }

  public void setUserRows(List users) {
    userRows_ = users;
  }
  
  private List userRows_ = new ArrayList();

}
