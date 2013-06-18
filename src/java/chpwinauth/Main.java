package chpwinauth;

import javax.security.auth.login.LoginContext;

public class Main
{

  public static void main(String [] args) throws Exception
  {
   /*
    NameCallback
    PasswordCallback
    TextInputCallback for domain
    */
   String username = "";
   String password = "";
   String domain = "";
   WinCallbackHandler ch = new WinCallbackHandler(username, password, domain);    
   LoginContext ctx = new LoginContext("NTLogin",ch);
   ctx.login();
    
  }
  
}
