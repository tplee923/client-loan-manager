package com.jumbohome.server.clientmanager;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.jumbohome.server.clientmanager.models.Client;
import com.jumbohome.server.clientmanager.models.Loan;
import com.jumbohome.server.clientmanager.utils.HtmlHelper;

public class ModifyClientServlet extends LoanClientBaseServlet {
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
    String clientIdString = req.getParameter("client_id");
    int clientId = Integer.parseInt(clientIdString);
    String loanIdString = req.getParameter("loan_id");
    int loanId = Integer.parseInt(loanIdString);
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    Client client = (Client) session.get(Client.class, clientId);
    Loan loan = (Loan) session.get(Loan.class, loanId);
    session.close();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String closingDate = dateFormat.format(loan.getClosingDate());
    String[] dateParts = closingDate.split("-");
    int year = Integer.parseInt(dateParts[0]);
    int month = Integer.parseInt(dateParts[1]);
    int day = Integer.parseInt(dateParts[2]);
    out.print("<h2>Review and Change client information</h2><hr>");
    out.print("<form name=\"client_info\" action=\"modify_client\" method=\"post\"><table>");
    out.print("<tr><td>client name: </td><td><input type=\"text\" name=\"client_name\" value=\"" + client.getName() + "\" /></td><td></td></tr>");
    out.print("<tr><td>loan amount: </td><td><input type=\"text\" name=\"loan_amount\" value=\"" + loan.getLoanAmount() + "\" /></td><td>(format: only numbers, example: 250000.0)</td></tr>");
    out.print("<tr><td>loan program: </td><td><input type=\"text\" name=\"loan_program\" value=\"" + loan.getLoanProgram() + "\" /></td><td></td></tr>");
    out.print("<tr><td>loan rate: </td><td><input type=\"text\" name=\"loan_rate\" value=\"" + loan.getRate() + "\" />%</td><td>(format: only numbers, example: 3.875)</td</tr>");
    out.print("<tr><td>loan closing date: </td><td>" + HtmlHelper.buildMonthDropdown(month) + "/" + HtmlHelper.buildDayDropdown(day) + "/" + HtmlHelper.buildYearDropdown(year) + "</td><td></td></tr>");
    out.print("<tr><td>loan interval: </td><td><input type=\"text\" name=\"loan_interval\" value=\"" + loan.getRefiInterval() + "\" /> months</td><td></td></tr>");    
    out.print("<tr><td>client email: </td><td><input type=\"text\" name=\"client_email\" value=\"" + client.getEmail() + "\" /></td><td></td></tr>");
    out.print("<tr><td>client phone: </td><td><input type=\"text\" name=\"client_phone\" value=\"" + client.getPhone() + "\" /></td><td></td></tr>");
    out.print("<tr><td>client employment status: </td><td><input type=\"text\" name=\"client_employment\" value=\"" + client.getEmployment() + "\" /></td><td></td></tr>");
    out.print("<tr><td><input type=\"hidden\" name=\"client_id\" value=\"" + clientId + "\" /></td><td></td><td></td></tr>");
    out.print("<tr><td><input type=\"hidden\" name=\"loan_id\" value=\"" + loanId + "\" /></td><td></td><td></td></tr>");
    out.print("<tr><td><input type=\"submit\" name=\"submit\" value=\"Submit\" /></td><td><input type=\"submit\" name=\"delete_client\" value=\"Delete\" /></td><td></td></tr>");
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

    String clientIdString = req.getParameter("client_id");
    int clientId = Integer.parseInt(clientIdString);
    String loanIdString = req.getParameter("loan_id");
    int loanId = Integer.parseInt(loanIdString);
    String deleteString = req.getParameter("delete_client");
    Boolean isDelete = false; 
    if (deleteString != null) {
      isDelete = true;
    }
    
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
    String closingDate = year + "-" + month + "-" + day;
    
    if (HtmlHelper.checkParameter(out, name, loanAmount, loanProgram, rate)) {
      SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
      Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();

      Client newClient = (Client) session.get(Client.class, clientId);
      Loan newLoan = (Loan) session.get(Loan.class, loanId);
      newClient.setName(name);
      newClient.setEmail(email);
      newClient.setPhone(phone);
      newClient.setEmployment(employmentStatus);
      newLoan.setLoanAmount(Float.parseFloat(loanAmount));
      newLoan.setLoanProgram(loanProgram);
      newLoan.setRate(Float.parseFloat(rate));
      if (isDelete) {
        newLoan.setStatus(3);
      }
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date loanDate = null;
      try {
        loanDate = dateFormat.parse(closingDate);
      } catch (ParseException e) {
        out.print("date parse error: " + e.getMessage());
      }
      if (loanDate != null) {
        newLoan.setClosingDate(loanDate);
      }
      newLoan.setRefiInterval(Integer.parseInt(refiInterval));
      //newLoan.setClientId(clientId);
      session.update(newClient);
      session.update(newLoan);
      transaction.commit();
      session.close();
      String destination = "track_client_loan";
      resp.sendRedirect(resp.encodeRedirectURL(destination));
    } else {
      HtmlHelper.printHead(out);
      out.print("<p><a href=\"modify_client?client_id=" + clientId + "&loan_id=" + loanId + "\">Get back to client information page</a>");
      HtmlHelper.printFoot(out);
    }
  }
}
