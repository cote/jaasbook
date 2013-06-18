package notes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.doomdark.uuid.UUIDGenerator;

public class NoteService {

  public NoteService()
  {
   for (int i = 0; i < 15; i++)
   {
    String title = i+" title";
    String content = i+" content";
    Note n = new Note();
    n.setTitle(title);
    n.setContent(content);
    save(n);
   }
  }
  
  public void deleteNotes(String[] ids) {
    if (ids == null || ids.length == 0) {
      return;
    }

    for (int i = 0; i < ids.length; i++) {
      String id = ids[i];
      notes.remove(id);
    }
  }

  public Note findNote(String id) throws NoteNotFoundException {
    Note note = (Note) notes.get(id);
    if (note == null) {
      throw new NoteNotFoundException(id);
    }

    return note;
  }

  public Note save(Note note) {
    if (note.getId() == null) {
      note.setId(UUID_GENERATOR.generateTimeBasedUUID().toString());
      note.setLastModified(note.getCreated());
    } else {
      note.setLastModified(new Date());
    }
    notes.put(note.getId(), note);
    return note;
  }

  /**
   * @return <code>List</code> of all <code>Note</code>s, sorted.
   */
  public List getNotes() {
    List notes = new ArrayList(this.notes.values());
    Collections.sort(notes);
    return notes;
  }

  static private UUIDGenerator UUID_GENERATOR = UUIDGenerator
      .getInstance();
  private Map notes = new HashMap();
}