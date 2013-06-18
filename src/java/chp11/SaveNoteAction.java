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
public class SaveNoteAction extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm rawForm,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    
    NoteForm form = (NoteForm)rawForm;
    
    NoteService ns = LookupFacade.getNoteService(request);
    
    Note n = form.getNote();
    ns.save(n);
    // update ID on form in case this is a new note.
    form.setId(n.getId());
    return mapping.findForward("success");
  }
}
