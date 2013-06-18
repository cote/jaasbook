package chp10;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.Permission;

import javax.security.auth.Subject;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class PermissionTag
    extends TagSupport {

  protected boolean checkPermission() throws JspException {
    Subject ctxSubject = Subject.getSubject(AccessController
        .getContext());
    String type = getType();
    if (type == null) {
      throw new NullPointerException("type is null.");
    }

    String name = getName();
    String actions = getActions();
    Permission perm = null;

    Class clazz = null;
    try {
      clazz = Class.forName(type);
    } catch (ClassNotFoundException e) {
      throw new JspException(type + " was not found.", e);
    }

    if (!Permission.class.isAssignableFrom(clazz)) {
      throw new IllegalArgumentException(type
          + " is not a java.security.Permission.");
    }

    try {
      if (name != null && actions == null) {
        Constructor c = clazz
            .getConstructor(new Class[] { String.class });
        perm = (Permission) c.newInstance(new Object[] { name });
      } else if (name != null && actions != null) {
        // name and actions
        Constructor c = clazz.getConstructor(new Class[] {
            String.class, String.class });
        perm = (Permission) c.newInstance(new Object[] { name,
            actions });
      } else {
        throw new NullPointerException(
            "Permission name must be specified.");
      }
    } catch (SecurityException e) {
      throw new JspException(e);
    } catch (NoSuchMethodException e) {
      throw new JspException("Could not instantiate " + type
          + " instance.", e);
    } catch (IllegalArgumentException e) {
      throw new JspException(e);
    } catch (InstantiationException e) {
      throw new JspException(e);
    } catch (IllegalAccessException e) {
      throw new JspException(e);
    } catch (InvocationTargetException e) {
      throw new JspException(e);
    }

    boolean granted = true;

    try {
      AccessController.checkPermission(perm);
    } catch (SecurityException e) {
      granted = false;
    }
    return granted;
  }

  public String getActions() {
    return actions_;
  }

  public void setActions(String actions) {
    actions_ = actions;
  }

  public String getName() {
    return name_;
  }

  public void setName(String name) {
    name_ = name;
  }

  public String getType() {
    return type_;
  }

  public void setType(String type) {
    type_ = type;
  }

  private String type_;
  private String name_;
  private String actions_;
}