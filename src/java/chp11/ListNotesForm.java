package chp11;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * @author mcote
 */
public class ListNotesForm
    extends ActionForm {

  public List getNotes() {
    return notes;
  }

  public void setNotes(List notes) {
    this.notes = notes;
  }

  private List notes = new ArrayList();
}