package com.alorma.apploteria.domain.bean;

public class GamePart {
  private String title;
  private double amount;
  private String description;
  private String currency;
  private GamePlace place;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public GamePlace getPlace() {
    return place;
  }

  public void setPlace(GamePlace place) {
    this.place = place;
  }
}
