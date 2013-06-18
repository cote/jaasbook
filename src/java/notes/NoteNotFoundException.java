package notes;

public class NoteNotFoundException
    extends RuntimeException {

  public NoteNotFoundException(String id) {
    super("Note not found for id " + id);
    this.id = id;
  }

  public String getId() {
    return id;
  }

  private String id;

}