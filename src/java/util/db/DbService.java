package util.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author michaelcote
 */
public class DbService
{

  private DbService()
  {
    init();
  }

  public void init()
  {
    Properties props = new Properties();
    try
    {
      InputStream in = getClass().getResourceAsStream("/DbService.properties");
      if (in == null) { throw new NullPointerException(
          "Could not find /DbService.properties"); }
      props.load(in);
    }
    catch (IOException e)
    {
      throw new RuntimeException(
          "Error loading dbservices.properties from classpath.", e);
    }

    dbDriver_ = props.getProperty("dbDriver");
    dbUrl_ = props.getProperty("dbUrl");
    dbLogin_ = props.getProperty("dbLogin");
    dbPassword_ = props.getProperty("dbPassword");

    // load driver
    try
    {
      Class.forName(dbDriver_);
    }
    catch (ClassNotFoundException e)
    {
      throw new RuntimeException("Error loading dbDriver " + dbDriver_, e);
    }
  }

  public Connection getConnection() throws SQLException
  {
    Connection connection = DriverManager.getConnection(dbUrl_, dbLogin_,
        dbPassword_);
    return connection;
  }

  static public DbService getInstance()
  {
    return INSTANCE;
  }
  
  static private  DbService INSTANCE = new DbService();

  private String dbDriver_;

  private String dbUrl_;

  private String dbLogin_;

  private String dbPassword_;
}