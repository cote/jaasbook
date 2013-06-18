package chp10;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import chp04.UserService;
import chp07.BundleCallbackHandler;

public class AuthenticationFilter implements Filter {

  public void init(FilterConfig config) throws ServletException {
    appName_ = config.getInitParameter("app-name");
    subjectKey_ = config.getInitParameter("subject-key");
    if (subjectKey_ == null) {
      subjectKey_ = DEFAULT_SUBJECT_KEY;
    }
  }

  public void doFilter(ServletRequest request,
      ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;

    String remoteUser = httpRequest.getRemoteUser();
    if (remoteUser != null) {

      if (LOGGER.isLoggable(Level.FINE)) {
        Subject subj = (Subject) httpRequest.getSession()
            .getAttribute(subjectKey_);

        LOGGER.logp(Level.FINE, LOG_TOPIC, "doFilter()",
            "Subject found under key {0}:\n{1}", new Object[] {
                subjectKey_, subj });
      }

      String password = null;
      try {
        password = UserService.lookupPassword(remoteUser);
      } catch (SQLException e) {
        throw new ServletException(
            "Error retrieving credentials for " + remoteUser, e);
      }
      BundleCallbackHandler cb = new BundleCallbackHandler(
          remoteUser, password);
      try {
        LoginContext ctx = new LoginContext(appName_, cb);
        ctx.login();
        Subject subj = ctx.getSubject();

        httpRequest.getSession().setAttribute(subjectKey_, subj);

        LOGGER.info("Authenticated Subject " + subj
            + ". Under session key " + subjectKey_);

      } catch (LoginException e) {
        LOGGER
            .logp(
                Level.WARNING,
                LOG_TOPIC,
                "doFilter()",
                "LoginException thrown when validating user {0}. Exception:\n{1}",
                new Object[] { remoteUser, e });
      }

    }

    chain.doFilter(request, response);
  }

  public void destroy() {
  }

  static private String LOG_TOPIC = AuthenticationFilter.class
      .getName();

  static private Logger LOGGER = Logger.getLogger(LOG_TOPIC);

  static private final String DEFAULT_SUBJECT_KEY = "subject";

  private String appName_;

  private String subjectKey_;

}