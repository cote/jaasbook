package chp11;

import javax.servlet.http.HttpServletRequest;

import notes.NoteService;

public class LookupFacade {

  static public NoteService getNoteService(HttpServletRequest req) {
    NoteService ns = (NoteService) req.getSession()
        .getServletContext().getAttribute("notes");
    if (ns == null) {
      throw new IllegalStateException(
          "NoteService has not been initialized.");
    }
    return ns;
  }

}