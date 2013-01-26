package com.jumbohome.server.clientmanager;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.jumbohome.server.clientmanager.models.Client;
import com.jumbohome.server.clientmanager.models.Loan;
import com.jumbohome.server.clientmanager.utils.HtmlHelper;

public class AddClientServlet extends LoanClientBaseServlet {
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
    out.print("<h2>Add New Client</h2><hr>");
    out.print("Fields with '*' are required");
    out.print("<form name=\"record_info\" action=\"add_client\" method=\"post\"><table>");
    out.print("<tr><td>client name: </td><td><input type=\"text\" name=\"client_name\" />*</td><td></td></tr>");
    out.print("<tr><td>loan amount: </td><td><input type=\"text\" name=\"loan_amount\" />*</td><td>(format: only numbers, example: 250000.0)</td></tr>");
    out.print("<tr><td>loan program: </td><td><input type=\"text\" name=\"loan_program\" />*</td><td></td></tr>");
    out.print("<tr><td>loan rate: </td><td><input type=\"text\" name=\"loan_rate\" />%*</td><td>(format: only numbers, example: 3.875)</td></tr>");
    out.print("<tr><td>loan closing date: </td><td>" + HtmlHelper.buildMonthDropdown() + "/" + HtmlHelper.buildDayDropdown() + "/" + HtmlHelper.buildYearDropdown() + "*</td><td></td></tr>");
    out.print("<tr><td>loan interval: </td><td><input type=\"text\" name=\"loan_interval\" value=\"4\"/> months</td><td></td></tr>");    
    out.print("<tr><td>client email: </td><td><input type=\"text\" name=\"client_email\" /></td><td></td></tr>");
    out.print("<tr><td>client phone: </td><td><input type=\"text\" name=\"client_phone\" /></td><td></td></tr>");
    out.print("<tr><td>client address: </td><td><input type=\"text\" name=\"client_address\" /></td><td></td></tr>");
    out.print("<tr><td>client employment status: </td><td><input type=\"text\" name=\"client_employment\" value=\"employed\" /></td><td></td></tr>");
    out.print("<tr><td>property name: </td><td><input type=\"text\" name=\"property_name\" /></td><td></td></tr>");
    out.print("<tr><td><input type=\"submit\" name=\"submit\" value=\"Submit\" /></td><td><input type=\"reset\" value=\"Reset\" /></td><td></td></tr>");
    out.print("</table></form><hr>");
    out.print("<a href=\"track_client_loan\">Back to client list</a>");
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
    
    HtmlHelper.printHead(out);
    HtmlHelper.printTop(out);
    out.print("<h2>Add New Client</h2><hr>");
    out.print("Fields with '*' are required");
    String name = req.getParameter("client_name");
    String email = req.getParameter("client_email");
    String phone = req.getParameter("client_phone");
    String employmentStatus = req.getParameter("client_employment");
    String loanAmount = req.getParameter("loan_amount");
    String loanProgram = req.getParameter("loan_program");
    String rate = req.getParameter("loan_rate");
    String year = req.getParameter("year");
    String month = req.getParameter("month");
    String day = req.getParameter("day");
    String refiInterval = req.getParameter("loan_interval");
    String address = req.getParameter("client_address");
    String propertyName = req.getParameter("property_name");
    
    
    if (HtmlHelper.checkParameter(out, name, loanAmount, loanProgram, rate)) {
      SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
      Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      List<Client> clients = session.createCriteria(Client.class).add(Restrictions.eq("name", name)).add(Restrictions.eq("isActive", true)).list();
      int clientId = 0;
      if (clients.size() == 0) {
        Client newClient = new Client();
        newClient.setName(name);
        newClient.setEmail(email);
        newClient.setPhone(phone);
        newClient.setEmployment(employmentStatus);
        newClient.setAddress(address);
        newClient.setIsActive(true);
        session.save(newClient);
        transaction.commit();
        clientId = newClient.getClientId();
        session.flush();
        session.close();
        out.print("Add client: " + name + " successfully!");
      } else {
        clientId = clients.get(0).getClientId();
      }
      
      Loan newLoan = new Loan();
      
      newLoan.setPropertyName(propertyName);
      newLoan.setLoanAmount(Float.parseFloat(loanAmount));
      newLoan.setLoanProgram(loanProgram);
      newLoan.setRate(Float.parseFloat(rate));    
      newLoan.setStatus(1);
      newLoan.setClientId(clientId);
      newLoan.setLoanOfficerId(userId);
      String closingDate = year + "-" + month + "-" + day;
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date loanDate = null;
      try {
        loanDate = dateFormat.parse(closingDate);
      } catch (ParseException e) {
        out.print("parse error: " + e.getMessage());
      }
      if (loanDate != null) {
        newLoan.setClosingDate(loanDate);
      }
      newLoan.setRefiInterval(Integer.parseInt(refiInterval));
      //SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      session.save(newLoan);
      transaction.commit();
      session.flush();
      session.close();
      out.print("Add new loan for client: " + name + " successfully!");
    }
    
    out.print("<form name=\"record_info\" action=\"add_client\" method=\"post\"><table>");
    out.print("<tr><td>client name: </td><td><input type=\"text\" name=\"client_name\" />*</td><td></td></tr>");
    out.print("<tr><td>loan amount: </td><td><input type=\"text\" name=\"loan_amount\" />*</td><td>(format: only numbers, example: 250000.0)</td></tr>");
    out.print("<tr><td>loan program: </td><td><input type=\"text\" name=\"loan_program\" />*</td><td></td></tr>");
    out.print("<tr><td>loan rate: </td><td><input type=\"text\" name=\"loan_rate\" />%*</td><td>(format: only numbers, example: 3.875)</td></tr>");
    out.print("<tr><td>loan closing date: </td><td>" + HtmlHelper.buildMonthDropdown() + "/" + HtmlHelper.buildDayDropdown() + "/" + HtmlHelper.buildYearDropdown() + "*</td><td></td></tr>");
    out.print("<tr><td>loan interval: </td><td><input type=\"text\" name=\"loan_interval\" value=\"4\"/> months</td><td></td></tr>");    
    out.print("<tr><td>client email: </td><td><input type=\"text\" name=\"client_email\" /></td><td></td></tr>");
    out.print("<tr><td>client phone: </td><td><input type=\"text\" name=\"client_phone\" /></td><td></td></tr>");
    out.print("<tr><td>client address: </td><td><input type=\"text\" name=\"client_address\" /></td><td></td></tr>");
    out.print("<tr><td>client employment status: </td><td><input type=\"text\" name=\"client_employment\" value=\"employed\" /></td><td></td></tr>");
    out.print("<tr><td>property name: </td><td><input type=\"text\" name=\"property_name\" /></td><td></td></tr>");
    out.print("<tr><td><input type=\"submit\" name=\"submit\" value=\"Submit\" /></td><td><input type=\"reset\" value=\"Reset\" /></td><td></td></tr>");
    out.print("</table></form><hr>");    
    out.print("<br><a href=\"track_client_loan\">Back to client list</a>");
    HtmlHelper.printFoot(out);
  }
}
