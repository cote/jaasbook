package chp09;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import util.LoggerInit;

import chp04.DbConfiguration;

public class StartupServlet
    extends HttpServlet {

  public void init(ServletConfig config) throws ServletException {
    DbConfiguration.init();
    LoggerInit.init();
  }
}