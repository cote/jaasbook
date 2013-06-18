package chp10;

import java.io.IOException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.Subject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class DoAsPrivilegedFilter implements Filter {

  public void init(FilterConfig config) throws ServletException {
    subjectKey_ = config.getInitParameter("subject-key");
    if (subjectKey_ == null) {
      subjectKey_ = DEFAULT_SUBJECT_KEY;
    }
  }

  public void doFilter(final ServletRequest request,
      final ServletResponse response, final FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    Subject subj = (Subject) httpRequest.getSession().getAttribute(
        subjectKey_);
    if (subj == null) {
      LOGGER
          .logp(
              Level.FINE,
              LOG_TOPIC,
              "doFilter()",
              "No Subject found under key {0}, so creating new Subject.",
              subjectKey_);
      subj = new Subject();
    }
    try {
      if (LOGGER.isLoggable(Level.FINE)) {
        LOGGER.logp(Level.FINE, LOG_TOPIC, "doFilter()",
            "Running doAsPrivileged block with Subject: {0}", subj);
      }

      Subject.doAsPrivileged(subj, new PrivilegedExceptionAction() {

        public Object run() throws Exception {
          chain.doFilter(request, response);
          return null;
        }

      }, null);
    } catch (PrivilegedActionException e) {
      LOGGER
          .logp(
              Level.SEVERE,
              LOG_TOPIC,
              "doFilter()",
              "Exception executing filter with Subject:\n {0}\nException: {1}",
              new Object[] { subj, e });
      throw new ServletException(e);
    }
  }

  public void destroy() {
  }

  static private String LOG_TOPIC = DoAsPrivilegedFilter.class
      .getName();

  static private Logger LOGGER = Logger.getLogger(LOG_TOPIC);

  static private final String DEFAULT_SUBJECT_KEY = "subject";

  private String subjectKey_;
}