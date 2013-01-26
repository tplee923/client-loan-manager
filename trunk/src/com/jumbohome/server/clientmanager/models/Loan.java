package com.jumbohome.server.clientmanager.models;

import java.util.Date;

public class Loan {
  private int loanId;
  private float loanAmount;
  private String loanProgram;
  private float rate;
  private Date closingDate;
  private int refiInterval;
  private String propertyName;
  private int status;
  private int clientId;
  private int loanOfficerId;
  private Date updateTime;
  private String notes;
  public int getLoanId() {
    return loanId;
  }
  public void setLoanId(int loanId) {
    this.loanId = loanId;
  }
  public float getLoanAmount() {
    return loanAmount;
  }
  public void setLoanAmount(float loanAmount) {
    this.loanAmount = loanAmount;
  }
  public String getLoanProgram() {
    return loanProgram;
  }
  public void setLoanProgram(String loanProgram) {
    this.loanProgram = loanProgram;
  }
  public float getRate() {
    return rate;
  }
  public void setRate(float rate) {
    this.rate = rate;
  }
  public Date getClosingDate() {
    return closingDate;
  }
  public void setClosingDate(Date closingDate) {
    this.closingDate = closingDate;
  }
  public int getRefiInterval() {
    return refiInterval;
  }
  public void setRefiInterval(int refiInterval) {
    this.refiInterval = refiInterval;
  }
  public String getPropertyName() {
    return propertyName;
  }
  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }
  public int getStatus() {
    return status;
  }
  public void setStatus(int status) {
    this.status = status;
  }
  public int getClientId() {
    return clientId;
  }
  public void setClientId(int clientId) {
    this.clientId = clientId;
  }
  public int getLoanOfficerId() {
    return loanOfficerId;
  }
  public void setLoanOfficerId(int loanOfficerId) {
    this.loanOfficerId = loanOfficerId;
  }
  public Date getUpdateTime() {
    return updateTime;
  }
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
  public String getNotes() {
    return notes;
  }
  public void setNotes(String notes) {
    this.notes = notes;
  }
  
  
}
