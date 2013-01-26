package com.jumbohome.server.clientmanager;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.jumbohome.server.clientmanager.models.Client;
import com.jumbohome.server.clientmanager.models.Loan;
import com.jumbohome.server.clientmanager.models.User;
import com.jumbohome.server.clientmanager.utils.HtmlHelper;

public class TrackClientLoan extends LoanClientBaseServlet {
  private static final long serialVersionUID = 1L;

  public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
    resp.setCharacterEncoding("utf-8");
    req.setCharacterEncoding("utf-8");
    PrintWriter out = resp.getWriter();
    int userId = isLogin(req);
    if (userId == 0) {
      String destination = "login";
      resp.sendRedirect(resp.encodeRedirectURL(destination));
    }

    HtmlHelper.printHead(out);
    HtmlHelper.printTop(out);
    
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    User user = (User)session.get(User.class, Integer.valueOf(userId));
    List<Loan> allLoans = session.createCriteria(Loan.class).add(
      Restrictions.eq("loanOfficerId", Integer.valueOf(user.getUserId()))).add(
      Restrictions.eq("status", Integer.valueOf(1))).list();
    transaction.commit();
    out.print("<h2>View All Clients | <a href=\"add_client\">Add New Client</a></h2><hr>");
    out.print("<table><tr><td>Name</td><td>Loan Amount</td><td>Loan Program</td><td>Rate</td><td>Last Closing Date</td></tr>");
    if (allLoans.size() > 0) {
      for (Loan loan : allLoans) {
        Client client = (Client)session.get(Client.class, Integer.valueOf(loan.getClientId()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        out.print("<tr><td><a href=\"modify_client?client_id=" + client.getClientId() + "&loan_id=" + loan.getLoanId() + "\">" + client.getName() + "</a></td>" + 
          "<td>" + loan.getLoanAmount() + "</td><td>" + loan.getLoanProgram() + "</td><td>" + loan.getRate() + "%</td>" + 
          "<td>" + dateFormat.format(loan.getClosingDate()) + "</td></tr>");
      }
    }
    session.close();

    out.print("</table>");
    
    HtmlHelper.printFoot(out);
  }
}
