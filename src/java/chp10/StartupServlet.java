package chp10;

import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import util.LoggerInit;

import chp04.DbConfiguration;
import chp06.CompositePolicy;
import chp06.DbPolicy;

public class StartupServlet extends HttpServlet
{

  public void init(ServletConfig config) throws ServletException
  {
    LoggerInit.init();
    DbConfiguration.init();
    
    Policy defaultPolicy = Policy.getPolicy();
    DbPolicy dbPolicy = new DbPolicy();
    List policies = new ArrayList(2);
    policies.add(defaultPolicy);
    policies.add(dbPolicy);
    CompositePolicy p = new CompositePolicy(policies);
    Policy.setPolicy(p);
    //System.setSecurityManager(new SecurityManager());
  }
}
