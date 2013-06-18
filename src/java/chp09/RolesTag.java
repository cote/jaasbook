package chp09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

public class RolesTag
    extends TagSupport {

  public int doStartTag() {
    if (roles_ != null || roles_.length() != 0) {
      boolean userHasRole = false;
      HttpServletRequest request = (HttpServletRequest) pageContext
          .getRequest();
      String[] splitRoles = roles_.split(",");
      for (int i = 0; i < splitRoles.length; i++) {
        String role = splitRoles[i];
        if (request.isUserInRole(role.trim())) {
          return EVAL_BODY_INCLUDE;
        }

      }
    }
    return SKIP_BODY;
  }

  public String getRoles() {
    return roles_;
  }

  public void setRoles(String roles) {
    roles_ = roles;
  }

  private String roles_;
}