package com.jumbohome.server.clientmanager.models;

import java.util.Date;

public class User {
  private int userId;
  private String email;
  private String name;
  private int type;
  private int status;
  private Date lastLogin;
  private String description;
  //private UserLogin userLogin;
  public int getUserId() {
    return userId;
  }
  public void setUserId(int userId) {
    this.userId = userId;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getType() {
    return type;
  }
  public void setType(int type) {
    this.type = type;
  }
  public int getStatus() {
    return status;
  }
  public void setStatus(int status) {
    this.status = status;
  }
  public Date getLastLogin() {
    return lastLogin;
  }
  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  //public UserLogin getUserLogin() {
  //  return userLogin;
  //}
  //public void setUserLogin(UserLogin userLogin) {
  //  this.userLogin = userLogin;
  //}  
}
