package chp11;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import chp11.uiobjects.UserQuery;

public class ListUsersAction
    extends Action {

  public ActionForward execute(ActionMapping mapping,
      ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    ListUsersForm form = (ListUsersForm)rawForm;
    
    UserQuery q = new UserQuery();
    List userRows = q.getAllUserRows();
    form.setUserRows(userRows);
    return mapping.findForward("success");
  }

}
