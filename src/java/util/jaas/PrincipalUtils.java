package util.jaas;

import java.security.Principal;

/**
 * @author michaelcote
 */
public class PrincipalUtils
{

  private PrincipalUtils()
  {
  }

  public static String toString(Principal[] principals)
  {
    if (principals == null || principals.length == 0) { return "<empty principals>"; }
    StringBuffer buf = new StringBuffer();

    buf.append("<");
    for (int i = 0; i < principals.length; i++)
    {
      Principal p = principals[i];
      buf.append("(class=");
      buf.append(p.getClass());
      buf.append(", name=");
      buf.append(p.getName());
      buf.append(")");
      if (i < principals.length)
      {
        buf.append(", ");
      }

    }
    buf.append(">");

    return buf.toString();
  }
}