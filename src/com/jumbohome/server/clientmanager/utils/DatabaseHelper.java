package com.jumbohome.server.clientmanager.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.jumbohome.server.clientmanager.models.User;
import com.jumbohome.server.clientmanager.models.UserLogin;

public class DatabaseHelper {
  public static User getUserById(int userId) {
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    User user = (User) session.get(User.class, userId);
    session.close();
    return user;
  }
  
  public static void saveNewUser(UserLogin newUserLogin, User newUser) {
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    session.save(newUserLogin);
    session.save(newUser);
    transaction.commit();
    session.flush();
    session.close();
  }
}
