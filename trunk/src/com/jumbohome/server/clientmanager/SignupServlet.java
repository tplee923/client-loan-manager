package com.jumbohome.server.clientmanager;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jumbohome.server.clientmanager.models.User;
import com.jumbohome.server.clientmanager.models.UserLogin;
import com.jumbohome.server.clientmanager.utils.DatabaseHelper;
import com.jumbohome.server.clientmanager.utils.HtmlHelper;

public class SignupServlet extends LoanClientBaseServlet {
  private static final long serialVersionUID = 1L;
  
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setCharacterEncoding("utf-8");
    req.setCharacterEncoding("utf-8");
    PrintWriter out = resp.getWriter();
    HtmlHelper.printHead(out);
    out.print("<h2>User Sign Up</h2><hr>");
    out.print("Fields with '*' are required");
    out.print("<form name=\"user_signup\" action=\"signup\" method=\"post\"><table>");
    out.print("<tr><td>Email Address:</td><td><input type=\"text\" name=\"user_email\" />*</td><td></td></tr>");
    out.print("<tr><td>Password:</td><td><input type=\"password\" name=\"password\" />*</td><td></td></tr>");
    out.print("<tr><td>Confirm Password:</td><td><input type=\"password\" name=\"password_confirm\" />*</td><td></td></tr>");
    out.print("<tr><td>Name: </td><td><input type=\"text\" name=\"user_name\" />*</td><td></td></tr>");
    out.print("<tr><td>Description: </td><td><input type=\"text\" name=\"description\" /></td><td></td></tr>");
    out.print("<tr><td><input type=\"submit\" name=\"submit\" value=\"Sign Up\" /></td><td><input type=\"reset\" value=\"Clear\" /></td><td></td></tr>");
    out.print("</table></form><hr>");
    HtmlHelper.printFoot(out);
  }
  
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setCharacterEncoding("utf-8");
    req.setCharacterEncoding("utf-8");
    PrintWriter out = resp.getWriter();
    HtmlHelper.printHead(out);
    out.print("<h2>User Sign Up</h2><hr>");
    // TODO(jumbo): check parameters
    String email = req.getParameter("user_email");
    String password = req.getParameter("password");
    String password_confirmed = req.getParameter("password_confirm");
    String name = req.getParameter("user_name");
    String description = req.getParameter("description");
    
    UserLogin newUserLogin = new UserLogin();
    newUserLogin.setEmail(email);
    if (password.equals(password_confirmed)) {
      newUserLogin.setPassword(HtmlHelper.encryptPassword(password));
    }
    User newUser = new User();
    newUser.setEmail(email);
    newUser.setName(name);
    newUser.setStatus(1);
    newUser.setType(1);
    newUser.setDescription(description);
    
    DatabaseHelper.saveNewUser(newUserLogin, newUser);
    // if save successfully, print succeed message, and provide link to login
    out.print("Signed up successfully! Please <a href=\"login\">Login</a>");
    // else error message and ask user to fill out the form again.
    HtmlHelper.printFoot(out);
  }
}
