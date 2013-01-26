package com.jumbohome.server.clientmanager.utils;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.jumbohome.server.clientmanager.models.User;
import com.jumbohome.server.clientmanager.models.UserLogin;

public class HtmlHelper {
  public static final int DEFAULT_SELECTED_MONTH = 1;
  public static final int DEFAULT_SELECTED_DAY = 1;
  public static final Calendar calendar = Calendar.getInstance();
  public static final int DEFAULT_SELECTED_YEAR = calendar.get(Calendar.YEAR);
  
  public static void printHead(PrintWriter out) {
    out.print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"");
    out.print("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
    out.print("<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"></head><body>");
  }
  
  public static void printFoot(PrintWriter out) {
    out.print("<hr>Copyright 2012 Jumbo Chow.</body></html>");
  }
  
  public static void printTop(PrintWriter out) {
    out.print("<table><tr><td><a href=\"logout\">Log Out</a></td><td><a href=\"edit_profile\">Edit Profile</a></td><td><a href=\"update_rate\">Update Rate</a></td></tr></table><hr>");
  }

  public static String buildYearDropdown(int selected) {
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    StringBuilder yearOption = new StringBuilder();
    for (int i = 0; i < 40; i++) {
      StringBuilder currentYear = new StringBuilder();
      currentYear.append("<option ");
      
      if ((year - i) == selected) {
        currentYear.append("selected ");
      }
      
      currentYear.append("value=\"" + (year - i) + "\">" + (year - i) + "</option>");
      
      yearOption.append(currentYear.toString());
    }
    return "<select name=\"year\">" + yearOption.toString() + "</select>";    
  }
  
  public static String buildYearDropdown() {
    return buildYearDropdown(DEFAULT_SELECTED_YEAR);
  }
  
  public static String buildMonthDropdown(int selected) {
    StringBuilder monthOption = new StringBuilder();
    for (int i = 1; i <= 12; i++) {
      StringBuilder currentMonth = new StringBuilder();
      currentMonth.append("<option ");
      if (i == selected) {
        currentMonth.append("selected ");
      }
      currentMonth.append("value=\"" + i + "\">" + i + "</option>");
      monthOption.append(currentMonth.toString());
    }
    return "<select name=\"month\">" + monthOption.toString() + "</select>";
  }
  
  public static String buildMonthDropdown() {
    return buildMonthDropdown(DEFAULT_SELECTED_MONTH);
  }
  
  public static String buildDayDropdown(int selected) {
    StringBuilder dayOption = new StringBuilder();
    for (int i = 1; i <= 31; i++) {
      StringBuilder currentDay = new StringBuilder();
      currentDay.append("<option ");
      if (i == selected) {
        currentDay.append("selected ");
      }
      currentDay.append("value=\"" + i + "\">" + i + "</option>");
      dayOption.append(currentDay.toString());
    }
    return "<select name=\"day\">" + dayOption.toString() + "</select>";
  }
  
  public static String buildDayDropdown() {
    return buildDayDropdown(DEFAULT_SELECTED_DAY);
  }
  
  public static Boolean checkParameter(PrintWriter out, String name, String loanAmount,
      String loanProgram, String rate) {
    if (name == null || name == "") {
      out.print("<p>Name could not be empty!</p>");
      return false;
    }
    if (loanAmount == null || loanAmount == "") {
      out.print("<p>Loan amount could not be empty</p>");
      return false;
    } else {
      try {
        Float.parseFloat(loanAmount);
      } catch (NumberFormatException numberException) {
        out.print("<p>Loan amount should be a pure number, e.g. 10000.00</p>");
        return false;
      }
    }
    if (loanProgram == null || loanProgram == "") {
      out.print("<p>Loan program could not be empty</p>");
      return false;
    }
    if (rate == null || rate == "") {
      out.print("<p>Rate could not be empty</p>");
      return false;
    } else {
      try {
        Float.parseFloat(rate);
      } catch (NumberFormatException numberException) {
        out.print("<p>Rate should be a pure number, e.g. 3.6</p>");
        return false;
      }
    }
    return true;
  }
  
  public static synchronized String encryptPassword(String password) {
    MessageDigest digest = null;
    String encryptedPassword = null;
    try {
        digest = MessageDigest.getInstance("SHA");
        digest.update(password.getBytes("UTF-8"));
        byte passwordBytes[] = digest.digest();
        encryptedPassword = new String(Base64.encodeBase64(passwordBytes));
    } catch (NoSuchAlgorithmException e) {
      System.out.println("No Such Algorithm Exists");
    } catch (UnsupportedEncodingException e) {
      System.out.println("The Encoding Is Not Supported");
    }
    return encryptedPassword;
  }
  
  public static String getSessionId() {
    SecureRandom random = new SecureRandom();
    return new BigInteger(64, random).toString();
  }
}
