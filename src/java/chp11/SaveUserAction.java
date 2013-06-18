package chp11;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SaveUserAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm rawForm, 
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    return mapping.findForward("success");
  }

}
