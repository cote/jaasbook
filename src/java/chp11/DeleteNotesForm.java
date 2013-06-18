package chp11;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * @author mcote
 */
public class DeleteNotesForm
    extends ActionForm {

  
  
  public ActionErrors validate(ActionMapping mapping,
      HttpServletRequest request) {
    
    ActionErrors errors = new ActionErrors();
    if (getIds() == null || getIds().length == 0)
    {
     ActionMessage error = new ActionMessage("delete.error.select.ids");
     errors.add("ids", error);
    }
        
    return errors;
  }
  public String[] getIds() {
    return ids;
  }
  public void setIds(String[] ids) {
    this.ids = ids;
  }
  public String[] ids = new String[0];
 
}
