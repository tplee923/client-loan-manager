package com.jumbohome.server.clientmanager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.jumbohome.server.clientmanager.models.User;
import com.jumbohome.server.clientmanager.models.UserLogin;
import com.jumbohome.server.clientmanager.utils.HtmlHelper;

public class LoginServlet extends LoanClientBaseServlet {
  private static final long serialVersionUID = 1L;
  
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setCharacterEncoding("utf-8");
    req.setCharacterEncoding("utf-8");
    PrintWriter out = resp.getWriter();
    HtmlHelper.printHead(out);
    out.print("<h2>User Login</h2><hr>");
    out.print("<form name=\"user_login\" action=\"login\" method=\"post\"><table>");
    out.print("<tr><td>Email:</td><td><input type=\"text\" name=\"user_email\" /></td><td></td></tr>");
    out.print("<tr><td>Password</td><td><input type=\"password\" name=\"password\" /></td><td></td></tr>");
    out.print("<tr><td><input type=\"submit\" name=\"login\" value=\"Login\" /></td><td><input type=\"reset\" value=\"Clear\" /></td><td><a href=\"signup\">Sign Up</a></td></tr>");
    out.print("</table></form><hr>");
    HtmlHelper.printFoot(out);
  }
  
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setCharacterEncoding("utf-8");
    req.setCharacterEncoding("utf-8");
    String userErrorString = "";
    String passwordErrorString = "";
    PrintWriter out = resp.getWriter();
    HtmlHelper.printHead(out);
    out.print("<h2>User Login</h2><hr>");
    String email = req.getParameter("user_email");
    String password = req.getParameter("password");
    String sessionId = HtmlHelper.getSessionId();
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    List<UserLogin> userLogins = session.createCriteria(UserLogin.class).add(Restrictions.eq("email", email)).list();
    transaction.commit();
    session.close();
    UserLogin userLogin;
    if (userLogins.size() == 0) {
      // user not exists, ask to sign up
      userErrorString = "User does not exist, please go to <a href=\"signup\">User Sign Up</>";
    } else {
      userLogin = userLogins.get(0);
      String encryptedPassword = HtmlHelper.encryptPassword(password);
      if (!encryptedPassword.equals(userLogin.getPassword())) {
        // password wrong, ask to write again
        passwordErrorString = "Password incorrect, please check your password and try again!";
      } else {
        // Log in, 1) create login cookie; 2) redirect to list view
        Cookie loginCookie = new Cookie("s_cookie", sessionId);
        loginCookie.setMaxAge(60 * 60 *24);
        resp.addCookie(loginCookie);
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<User> users = session.createCriteria(User.class).add(Restrictions.eq("email", email)).list();
        UserLogin updateLogin = (UserLogin) session.get(UserLogin.class, email);
        updateLogin.setSessionId(sessionId);
        session.saveOrUpdate(updateLogin);
        transaction.commit();
        session.close();
        if (users.size() == 0) {
          resp.getWriter().println("Server error, could not find user!");
        } else {
          Cookie userCookie = new Cookie("u_cookie", Integer.toString(users.get(0).getUserId()));
          resp.addCookie(userCookie);
          String destination = "track_client_loan";
          resp.sendRedirect(resp.encodeRedirectURL(destination));
        }
      }
    }
    
    out.print("<form name=\"user_login\" action=\"login\" method=\"post\"><table>");    
    out.print("<tr><td>Email:</td><td><input type=\"text\" name=\"user_email\" /></td><td>" + userErrorString + "</td></tr>");
    out.print("<tr><td>Password</td><td><input type=\"password\" name=\"password\" /></td><td>" + passwordErrorString + "</td></tr>");
    out.print("<tr><td><input type=\"submit\" name=\"login\" value=\"Login\" /></td><td><input type=\"reset\" value=\"Clear\" /></td><td><a href=\"signup\">Sign Up</a></td></tr>");
    out.print("</table></form><hr>");
    HtmlHelper.printFoot(out);
  }

}
