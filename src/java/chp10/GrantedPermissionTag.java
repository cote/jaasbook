package chp10;

import javax.servlet.jsp.JspException;

public class GrantedPermissionTag
    extends PermissionTag {
  public int doStartTag() throws JspException {
    boolean granted = checkPermission();

    if (granted) {
      return EVAL_BODY_INCLUDE;
    } else {
      return SKIP_BODY;
    }
  }
}