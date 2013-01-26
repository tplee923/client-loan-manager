package com.jumbohome.server.clientmanager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jumbohome.server.clientmanager.utils.HtmlHelper;

public class LogoutServlet extends LoanClientBaseServlet {
  
  private static final long serialVersionUID = 1L;

  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setCharacterEncoding("utf-8");
    PrintWriter out = resp.getWriter();
    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("s_cookie")) {
          cookie.setMaxAge(0);
          resp.addCookie(cookie);
          break;
        }
      }
    }
    HtmlHelper.printHead(out);
    out.println("User log out successfully!<br>");
    out.println("<a href=\"login\">Login</a> back");
    HtmlHelper.printFoot(out);
  }
}
