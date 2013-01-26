package com.jumbohome.server.clientmanager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.jumbohome.server.clientmanager.models.User;
import com.jumbohome.server.clientmanager.models.UserLogin;

public class LoanClientBaseServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected int isLogin(HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();
    boolean isLogin = false;
    int userId = 0;
    Cookie userCookie = null;
    Cookie loginCookie = null;
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("u_cookie")) {
          userCookie = cookie;   
        }
        if (cookie.getName().equals("s_cookie")) {
          loginCookie = cookie;
        }
      }
    }
    if (userCookie != null && loginCookie != null) {
      userId = Integer.parseInt(userCookie.getValue());
      SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
      Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();    
      User user = (User) session.get(User.class, userId);
      String email = user.getEmail();
      UserLogin userLogin = (UserLogin) session.get(UserLogin.class, email);
      transaction.commit();
      String sessionId = userLogin.getSessionId();
      session.flush();
      session.close();
      
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("s_cookie")) {
          loginCookie = cookie;
          break;
        }
      }
      if (loginCookie != null) {
        if (loginCookie.getValue().equals(sessionId)) {
          isLogin = true;
        }
      }
    }
    return isLogin ? userId : 0;
  }
}
