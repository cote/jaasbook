package chp11;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notes.NoteService;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author mcote
 */
public class DeleteNotesAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm rawForm,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    
    DeleteNotesForm form = (DeleteNotesForm)rawForm;
    String [] ids = form.getIds();
        
    NoteService ns = LookupFacade.getNoteService(request);
    
    ns.deleteNotes(ids);
    
    return mapping.getInputForward();
  }
}
