package chp11.uiobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.db.DbService;

public class UserQuery {

  public UserRow getUserRow(String id) throws SQLException {
    UserRow row = new UserRow();
    Connection conn = null;
    try {
      conn = DbService.getInstance().getConnection();

      String sql = "SELECT principal.name, "
          + "db_user.id userid, db_user.username "
          + "FROM db_user, principal_db_user, principal "
          + "WHERE db_user.id = ? "
          + "AND principal_db_user.user_id=db_user.id "
          + "AND principal.id=principal_db_user.principal_id ";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, id);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        String userId = rs.getString("userid");
        if (!userId.equals(row.getId())) {
          row = new UserRow();
          row.setId(userId);
          String username = rs.getString("username");
          row.setUsername(username);
        }
        String pName = rs.getString("name");
        if ("admin".equals(pName)) {
          row.setAdmin(true);
        } else if ("customer".equals(pName)) {
          row.setCustomer(true);
        }
      }

    } finally {
      if (conn != null) {
        conn.close();
      }
    }
    return row;

  }

  public List getAllUserRows() throws SQLException {
    List rows = new ArrayList();
    Connection conn = null;
    try {
      conn = DbService.getInstance().getConnection();

      String sql = "SELECT principal.name, "
          + "db_user.id userid, db_user.username "
          + "FROM db_user, principal_db_user, principal "
          + "WHERE " + "principal_db_user.user_id=db_user.id "
          + "AND principal.id=principal_db_user.principal_id "
          + "ORDER BY db_user.username";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      UserRow row = null;
      while (rs.next()) {
        String userId = rs.getString("userid");
        if (row == null || !userId.equals(row.getId())) {
          row = new UserRow();
          rows.add(row);
          row.setId(userId);
          String username = rs.getString("username");
          row.setUsername(username);
        }
        String pName = rs.getString("name");
        if ("admin".equals(pName)) {
          row.setAdmin(true);
        } else if ("customer".equals(pName)) {
          row.setCustomer(true);
        }
      }

    } finally {
      if (conn != null) {
        conn.close();
      }
    }
    return rows;
  }

}
