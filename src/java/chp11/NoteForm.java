package chp11;

import notes.Note;

import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.ValidatorForm;

/**
 * @author mcote
 */
public class NoteForm
    extends ValidatorForm {

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Note getNote() {
    return note;
  }

  public void setNote(Note note) {
    this.note = note;
  }

  private String id;
  private Note note = new Note();
}