package com.jumbohome.server.clientmanager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.jumbohome.server.clientmanager.models.User;
import com.jumbohome.server.clientmanager.models.UserLogin;
import com.jumbohome.server.clientmanager.utils.DatabaseHelper;
import com.jumbohome.server.clientmanager.utils.HtmlHelper;

public class EditProfileServlet extends LoanClientBaseServlet {
  private static final long serialVersionUID = 1L;
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setCharacterEncoding("utf-8");
    req.setCharacterEncoding("utf-8");
    PrintWriter out = resp.getWriter();
    int userId = isLogin(req);
    if (userId == 0) {
      // not login or session expired
      String destination = "login";
      resp.sendRedirect(resp.encodeRedirectURL(destination));
    }
    HtmlHelper.printHead(out);
    HtmlHelper.printTop(out);
    User user = DatabaseHelper.getUserById(userId);
    out.print("<h2>Edit Profile</h2><hr>");
    out.print("<form name=\"edit_profile\" action=\"edit_profile\" method=\"post\"><table>");
    out.print("<tr><td>Old Password:</td><td><input type=\"password\" name=\"old_password\" /></td><td>If you want to change password, please fill in the old password.  If not, just leave it blank.</td></tr>");
    out.print("<tr><td>New Password:</td><td><input type=\"password\" name=\"new_password\" /></td><td></td></tr>");
    out.print("<tr><td>Confirm New Password:</td><td><input type=\"password\" name=\"password_confirm\" /></td><td></td></tr>");
    out.print("<tr><td>Name: </td><td><input type=\"text\" name=\"user_name\" value=\"" + user.getName() + "\"/></td><td></td></tr>");
    out.print("<tr><td>Description: </td><td><input type=\"text\" name=\"description\" value=\"" + user.getDescription() + "\"/></td><td></td></tr>");
    out.print("<tr><td><input type=\"submit\" name=\"submit\" value=\"Edit\" /></td><td><input type=\"reset\" value=\"Clear\" /></td><td></td></tr>");
    out.print("</table></form><hr>");
    out.print("<a href=\"track_client_loan\">View All Clients</a>");
    HtmlHelper.printFoot(out);
  }

  
  
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setCharacterEncoding("utf-8");
    req.setCharacterEncoding("utf-8");
    PrintWriter out = resp.getWriter();
    int userId = isLogin(req);
    if (userId == 0) {
      // not login or session expired
      String destination = "login";
      resp.sendRedirect(resp.encodeRedirectURL(destination));
    }

    String name = req.getParameter("user_name");
    String description = req.getParameter("description");
    String oldPassword = req.getParameter("old_password");
    String newPassword = req.getParameter("new_password");
    String confirmPassword = req.getParameter("password_confirm");
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    User user = (User) session.get(User.class, userId);
    UserLogin userLogin = (UserLogin) session.get(UserLogin.class, user.getEmail());
    user.setDescription(description);
    user.setName(name);
    if (oldPassword != null && oldPassword != "") {
      String encryptedPassword = HtmlHelper.encryptPassword(oldPassword);
      if (encryptedPassword.equals(userLogin.getPassword())) {
        if (newPassword != null && newPassword != "") {
          if (newPassword.equals(confirmPassword)) {
            userLogin.setPassword(HtmlHelper.encryptPassword(newPassword));      
          }
        }
      }
    }
    session.update(userLogin);
    session.update(user);
    transaction.commit();
    session.flush();
    session.close();
    HtmlHelper.printHead(out);
    HtmlHelper.printTop(out);
    out.print("Profile updated successfully! <a href=\"track_client_loan\">View All Clients</a>");
    HtmlHelper.printFoot(out);
  }
}
