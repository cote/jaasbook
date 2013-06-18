package util;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.LogManager;

public class LoggerInit
{

  static public void init()
  {
    AccessController.doPrivileged(new PrivilegedAction()
    {

      public Object run()
      {
        InputStream in = LoggerInit.class.getResourceAsStream("/logger.properties");
        if (in != null)
        {

          try
          {
            LogManager.getLogManager().readConfiguration(in);
            System.out.println("Configured logger with logger.properties");
          }
          catch (SecurityException e)
          {
            System.out.println("Error reconfiguring LogManager with logger.properties. Default logging will be used.");
          }
          catch (IOException e)
          {
            System.out.println("Error reconfiguring LogManager with logger.properties. Default logging will be used.");
          }
        }
        else
        {
          System.out.println("logger.properties not found, using default Logger settings.");
        }
        return null;
      }
    });
  }

}
