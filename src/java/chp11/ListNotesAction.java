package chp11;

import java.util.List;

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
public class ListNotesAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm rawForm,
      HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    ListNotesForm form = (ListNotesForm)rawForm;
        
    NoteService ns = LookupFacade.getNoteService(request);
    
    List notes = ns.getNotes();
    form.setNotes(notes);
    
    return mapping.getInputForward();
  }
}
