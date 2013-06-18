package application;


/**
 * @author michaelcote
 * @since RUSHMORE 0.1
 */
public class App
{
  static public void message(String msg)
  {
    System.out.println("["+msg+"] Your java.home property: "
        + System.getProperty("java.home"));
  }
}
