package chp11;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chp11.uiobjects.UserQuery;


public class LoadUsers extends HttpServlet {

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response) throws ServletException,
      IOException {
    // Load up each user, making a list.
    UserQuery q = new UserQuery();
    try
    {
    List userRows = q.getAllUserRows();
    request.setAttribute("users", userRows);
    }
    catch(SQLException e)
    {
     throw new ServletException("Error querying for all users", e); 
    }
    // set the SelectOptions for that user.
   request.getRequestDispatcher("/users.jsp").forward(request, response);
  }
}
