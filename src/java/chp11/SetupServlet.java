package chp11;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import util.LoggerInit;
import chp04.DbConfiguration;

import notes.NoteService;

public class SetupServlet
    extends HttpServlet {

  public void init(ServletConfig config) throws ServletException {
    DbConfiguration.init();
    LoggerInit.init();
    config.getServletContext().setAttribute("notes",
        new NoteService());
  }
}
