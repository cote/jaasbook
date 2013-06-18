package chp07;

import java.security.Permission;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public abstract class ActionsPermission
    extends Permission {

  private Set actionSet;
  private String actions;

  public ActionsPermission(String name, String actions) {
    super(name);
    if (name == null) {
      throw new NullPointerException(
          "permission name may not be null.");
    }

    actionSet = splitActions(actions);
    this.actions = canonizeActions(actionSet);
  }

  public String getActions() {
    return actions;
  }

  public boolean hasAction(String action) {
    return actionSet.contains(action);
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj.getClass() != ActionsPermission.class) {
      return false;
    }

    ActionsPermission other = (ActionsPermission) obj;

    return getName().equals(other.getName())
        && getActions().equals(other.getActions());

  }

  public int hashCode() {
    return getClass().getName().hashCode() * 19
        + getName().hashCode() * 19 + getActions().hashCode() * 19;
  }

  public boolean implies(Permission permission) {
    // Test: this implies passed in permission?
    // i.e., passed in permission is a sub-set of this.
    if (equals(permission)) {
      return true;
    }

    if (getClass() != permission.getClass()) {
      return false;
    }

    ActionsPermission other = (ActionsPermission) permission;
    if (!getName().equals(other.getName())) {
      return false;
    }

    if (!actionSet.containsAll(other.actionSet)) {
      return false;
    }

    return true;
  }

  private Set splitActions(String actions) {
    Set actionSet = Collections.EMPTY_SET;
    if (actions != null && actions.trim().length() > 0) {
      actionSet = new TreeSet();
      String[] split = actions.split(",");
      for (int i = 0; i < split.length; i++) {
        String action = split[i];
        actionSet.add(action.trim());
      }
    }
    return actionSet;
  }

  private String canonizeActions(Set actions) {
    if (actions == null || actions.isEmpty()) {
      return "";
    }

    StringBuffer buf = new StringBuffer();
    for (Iterator itr = actions.iterator(); itr.hasNext();) {
      String action = (String) itr.next();
      buf.append(action);
      if (itr.hasNext()) {
        buf.append(",");
      }
    }

    return buf.toString();
  }

}