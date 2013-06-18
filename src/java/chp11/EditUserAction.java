package chp11;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import chp11.uiobjects.UserQuery;
import chp11.uiobjects.UserRow;

public class EditUserAction
    extends Action {

  public ActionForward execute(ActionMapping mapping,
      ActionForm rawForm, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    EditUserForm form = (EditUserForm) rawForm;

    UserQuery q = new UserQuery();
    UserRow userRow = q.getUserRow(form.getId());
    form.setUserRow(userRow);
    return mapping.findForward("success");
  }

}
