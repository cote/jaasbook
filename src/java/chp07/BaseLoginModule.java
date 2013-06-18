package chp07;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.spi.LoginModule;

public abstract class BaseLoginModule implements LoginModule {

  private Subject subject;
  private CallbackHandler callbackHandler;
  private Map sharedState;
  private Map options;

  public void initialize(Subject subject,
      CallbackHandler callbackHandler, Map sharedState, Map options) {
    this.subject = subject;
    this.callbackHandler = callbackHandler;
    this.sharedState = sharedState;
    this.options = options;
  }

  protected CallbackHandler getCallbackHandler() {
    return callbackHandler;
  }

  protected Map getOptions() {
    return options;
  }

  protected Map getSharedState() {
    return sharedState;
  }

  protected Subject getSubject() {
    return subject;
  }

}