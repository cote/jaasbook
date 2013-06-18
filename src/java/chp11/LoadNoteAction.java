package chp11;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notes.Note;
import notes.NoteService;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author mcote
 */
public class LoadNoteAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm rawForm,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    
    NoteForm form = (NoteForm)rawForm;
    String id = form.getId();
    
    NoteService ns = LookupFacade.getNoteService(request);
    
    Note n = ns.findNote(id);
   
    form.setNote(n);
    
    return mapping.findForward("success");
  }
}
