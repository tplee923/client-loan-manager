package com.jumbohome.server.clientmanager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jumbohome.server.clientmanager.utils.HtmlHelper;

public class UpdateRateServlet extends LoanClientBaseServlet {
  // TODO(Jumbo): Investigate the way to update the rate and send out the email/show client info.
  private static final long serialVersionUID = 1L;
  
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    
    resp.setCharacterEncoding("utf-8");
    PrintWriter out = resp.getWriter();
    int userId = isLogin(req);

    if (userId > 0) {
      HtmlHelper.printHead(out);
      HtmlHelper.printTop(out);
      out.print("<h1>Not done yet!<h1>");
      out.print("<h2><a href=\"track_client_loan\">View All Clients</a> | <a href=\"add_client\">Add New Client</a></h2><hr>");
      out.print("<form name=\"update_rate\" action=\"update_rate\" method=\"post\"><table>");
      out.print("<tr><td>30 Year Fix: </td><td><input type=\"text\" name=\"thirty_fix\" /></td><td></td></tr>");
      out.print("<tr><td>15 Year Fix: </td><td><input type=\"text\" name=\"fifteen_fix\" /></td><td></td></tr>");
      out.print("<tr><td>5 Year ARM: </td><td><input type=\"text\" name=\"five_arm\" /></td><td></td></tr>");
      out.print("<tr><td>7 Year ARM: </td><td><input type=\"text\" name=\"seven_arm\" /></td><td></td</tr>");
      out.print("<tr><td><input type=\"submit\" name=\"submit\" value=\"Submit\" /></td><td><input type=\"reset\" name=\"reset\" value=\"Reset\" /></td><td></td></tr>");
      out.print("</table></form>");
      HtmlHelper.printFoot(out);
    } else {
      // not login or session expired
      String destination = "login";
      resp.sendRedirect(resp.encodeRedirectURL(destination));
    }
  }
  
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    
    resp.setCharacterEncoding("utf-8");
    PrintWriter out = resp.getWriter();
    int userId = isLogin(req);

    if (userId > 0) {
      HtmlHelper.printHead(out);
      HtmlHelper.printTop(out);
      
      HtmlHelper.printFoot(out);
    } else {
      // not login or session expired
      String destination = "login";
      resp.sendRedirect(resp.encodeRedirectURL(destination));
    }
  }
}
