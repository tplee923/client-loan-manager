package com.jumbohome.server.clientmanager.models;

public class UserType {
  private Long userTypeId;
  private String type;
  private String description;
  public Long getUserTypeId() {
    return userTypeId;
  }
  public void setUserTypeId(Long userTypeId) {
    this.userTypeId = userTypeId;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
}
