package chp06;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.Permission;
import java.security.Principal;
import java.security.UnresolvedPermission;
import java.security.cert.Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.db.DbService;
import util.id.Id;

public class PermissionServiceNoLogging {

  static private final Certificate[] EMPTY_CERTS = new Certificate[0];

  static private final Class[] ZERO_ARGS = {};

  static private final Object[] ZERO_OBJS = {};

  static private final Class[] ONE_STRING_ARG = { String.class };

  static private final Class[] TWO_STRING_ARGS = { String.class,
      String.class };

  static public void removePermission(Id id) throws SQLException {
    removePermissions(Collections.singleton(id));
  }

  static public void removePermissions(Set ids) throws SQLException {
    Connection conn = null;
    try {
      conn = DbService.getInstance().getConnection();
      String sql = "DELETE FROM principal_permission WHERE permission_id = ?";
      PreparedStatement tiePstmt = conn.prepareStatement(sql);
      PreparedStatement permPstmt = conn
          .prepareStatement("DELETE FROM permission WHERE id= ?");
      for (Iterator itr = ids.iterator(); itr.hasNext();) {
        // HSQLDB doesn't support addBatch() :(
        Id id = (Id) itr.next();
        tiePstmt.setString(1, id.getId());
        permPstmt.setString(1, id.getId());
        tiePstmt.executeUpdate();
        permPstmt.executeUpdate();
      }
    } finally {
      if (conn != null) {
        conn.close();
      }
    }

  }

  static public List findPermissions(Set principalIds)
      throws SQLException {
    // HSQLDB doesn't allow batching, so we have to do a call per id
    List permissions = new ArrayList();
    for (Iterator itr = principalIds.iterator(); itr.hasNext();) {
      Id principalId = (Id) itr.next();
      permissions.addAll(findPermissions(principalId));
    }
    return permissions;
  }

  static public List findPermissions(Id principalId)
      throws SQLException {
    List perms = new ArrayList();
    Connection conn = null;
    try {
      conn = DbService.getInstance().getConnection();
      String sql = "SELECT permission.id id, "
          + "permission.permissionClass clazz, permission.name name, "
          + "permission.actions actions "
          + "FROM principal_permission, permission "
          + "WHERE principal_permission.principal_id=? "
          + "AND permission.id=principal_permission.permission_id ";

      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, principalId.getId());
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        String idStr = rs.getString("id");
        Id id = Id.create(idStr);
        String clazzStr = rs.getString("clazz");
        String name = rs.getString("name");
        String actions = rs.getString("actions");

        Permission perm = null;
        // make class
        Class clazz = null;

        perm = createPermission(id, clazzStr, name, actions);
        if (perm != null) {
          perms.add(perm);
        } else {
          continue;
        }

      }
    } finally {
      if (conn != null) {
        conn.close();
      }
    }
    return perms;
  }

  private static Permission createPermission(Id id,
      String clazzStr, String name, String actions) {
    Permission perm = null;
    Class clazz = null;
    try {
      clazz = Class.forName(clazzStr);
    } catch (ClassNotFoundException e) {
      // deal with below
    }

    if (clazz == null) {
      perm = new UnresolvedPermission(clazzStr, name, actions,
          EMPTY_CERTS);

    } else if (clazz.equals(DbPermission.class)) {
      perm = new DbPermission(id, name, actions);
    } else if (Permission.class.isAssignableFrom(clazz)) {
      try {
        if (name == null && actions == null) {
          Constructor con = clazz.getConstructor(ZERO_ARGS);
          perm = (Permission) con.newInstance(ZERO_OBJS);
        } else if (actions == null) {
          Constructor con = clazz.getConstructor(ONE_STRING_ARG);
          perm = (Permission) con
              .newInstance(new String[] { name });
        }
        // BasicPermission types
        else if (name != null && actions != null) {
          Constructor con = clazz.getConstructor(TWO_STRING_ARGS);
          perm = (Permission) con.newInstance(new String[] { name,
              actions });
        }
      } catch (Exception e) {
        // Log
      }
    }
    return perm;
  }

  static public void addPermission(Id principalId,
      DbPermission dbPermission) throws SQLException {
    addPermission(principalId, dbPermission.getId(), dbPermission);
  }

  static public void addPermission(Id principalId, Id permissionId,
      Permission permission) throws SQLException {
    Connection conn = null;
    try {
      conn = DbService.getInstance().getConnection();
      PreparedStatement pstmt = conn
          .prepareStatement("INSERT INTO permission VALUES (?, ?, ?, ?)");
      pstmt.setString(1, permissionId.getId());
      pstmt.setString(2, permission.getClass().getName());
      pstmt.setString(3, permission.getName());
      pstmt.setString(4, permission.getActions());
      pstmt.executeUpdate();

      PreparedStatement pstmt2 = conn
          .prepareStatement("INSERT INTO principal_permission VALUES (?, ?)");
      pstmt2.setString(1, principalId.getId());
      pstmt2.setString(2, permissionId.getId());
      pstmt2.executeUpdate();
    } finally {
      if (conn != null) {
        conn.close();
      }
    }
  }

}