package chp04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.db.DbService;
import util.id.Id;

/**
 * @author michaelcote
 */
public class UserService
{

  static public String lookupPassword(String username) throws SQLException
  {
    Connection conn = null;
    try
    {
      conn = DbService.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT password FROM db_user WHERE username= ?");
      pstmt.setString(1, username);
      ResultSet rs = pstmt.executeQuery();
      String password = null;
      if (rs.next())
      {
       password = rs.getString("password"); 
      }
      
      return password;
    }
    finally
    {
      if (conn != null)
      {
        conn.close();
      }
    }
    
  }
  
  static public Id addUser(String username, String password)
      throws SQLException
  {
    Connection conn = null;
    try
    {
      conn = DbService.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("INSERT INTO db_user VALUES (?, ?, ?)");
      Id userId = Id.create();
      pstmt.setString(1, userId.getId());
      pstmt.setString(2, username);
      pstmt.setString(3, password);
      pstmt.executeUpdate();

      return userId;
    }
    finally
    {
      if (conn != null)
      {
        conn.close();
      }
    }
  }

  static public void removeUser(Id userId) throws SQLException
  {
    Connection conn = null;
    try
    {
      conn = DbService.getInstance().getConnection();

      PreparedStatement pstmtGrp = conn.prepareStatement("DELETE FROM principal_db_user WHERE user_id=?");
      pstmtGrp.setString(1, userId.getId());
      pstmtGrp.executeUpdate();

      PreparedStatement pstmt = conn.prepareStatement("DELETE FROM db_user WHERE id=?");
      pstmt.setString(1, userId.getId());
      pstmt.executeUpdate();
    }
    finally
    {
      if (conn != null)
      {
        conn.close();
      }
    }
  }

  static public boolean addToUserGroup(Id userId, Id userGroupId)
      throws SQLException
  {
    Connection conn = null;
    try
    {
      conn = DbService.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("INSERT INTO principal_db_user VALUES (?, ?)");
      pstmt.setString(1, userId.getId());
      pstmt.setString(2, userGroupId.getId());
      return 0 < pstmt.executeUpdate();
    }
    finally
    {
      if (conn != null)
      {
        conn.close();
      }
    }
  }

  static public boolean deleteFromUserGroup(Id userId, Id userGroupId)
      throws SQLException
  {
    Connection conn = null;
    try
    {
      conn = DbService.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("DELETE FROM principal_db_user WHERE user_id=? AND principal_id=?");
      pstmt.setString(1, userId.getId());
      pstmt.setString(2, userGroupId.getId());
      return 0 < pstmt.executeUpdate();
    }
    finally
    {
      if (conn != null)
      {
        conn.close();
      }
    }
  }

  static public boolean addUserGroup(UserGroupPrincipal group)
      throws SQLException
  {
    Connection conn = null;
    try
    {
      conn = DbService.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("INSERT INTO principal VALUES(?,?,?)");
      pstmt.setString(1, group.getId().getId());
      pstmt.setString(2, group.getName());
      pstmt.setString(3, group.getClass().getName());
      return 0 < pstmt.executeUpdate();
    }
    finally
    {
      if (conn != null)
      {
        conn.close();
      }
    }
  }

  static public void removeUserGroup(Id grpId) throws SQLException
  {
    Connection conn = null;
    try
    {
      conn = DbService.getInstance().getConnection();

      PreparedStatement pstmtGrp = conn.prepareStatement("DELETE FROM principal_db_user WHERE principal_id=?");
      pstmtGrp.setString(1, grpId.getId());
      pstmtGrp.executeUpdate();

      PreparedStatement pstmt = conn.prepareStatement("DELETE FROM principal WHERE id=?");
      pstmt.setString(1, grpId.getId());
      pstmt.executeUpdate();

    }
    finally
    {
      if (conn != null)
      {
        conn.close();
      }
    }

  }
}