package chp10;

import javax.servlet.jsp.JspException;

public class NotGrantedPermissionTag
    extends PermissionTag {

  public int doStartTag() throws JspException {
    boolean granted = checkPermission();

    if (granted) {
      return SKIP_BODY;
    } else {
      return EVAL_BODY_INCLUDE;
    }
  }

}