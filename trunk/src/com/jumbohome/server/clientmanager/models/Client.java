package com.jumbohome.server.clientmanager.models;

public class Client {
  private int clientId;
  private String name;
  private String email;
  private String phone;
  private String address;
  private String employment;
  private boolean isActive;
  
  public int getClientId() {
    return clientId;
  }
  
  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmployment() {
    return employment;
  }

  public void setEmployment(String employment) {
    this.employment = employment;
  }

  public boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }
}
