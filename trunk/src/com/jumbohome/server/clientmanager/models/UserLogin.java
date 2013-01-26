package com.jumbohome.server.clientmanager.models;

public class UserLogin {
  private String email;
  private String password;
  private String sessionId;
  //private User user;
  
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  //public User getUser() {
  //  return user;
  //}
  //public void setUser(User user) {
  //  this.user = user;
  //}
  public String getSessionId() {
    return sessionId;
  }
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
}
