package com.alorma.apploteria.domain.bean;

import com.afollestad.inquiry.annotations.Column;
import com.afollestad.inquiry.annotations.ForeignKey;
import com.afollestad.inquiry.annotations.Table;

@Table(name = "GamePart")
public class GamePart {

  @Column(name = "_id", primaryKey = true, notNull = true, autoIncrement = true)
  public long id;

  @Column
  public String title;

  @Column
  public double amount;

  @Column
  public String description;

  @Column
  public String currency;

  @Column
  public long gameId;

  @ForeignKey(tableName = "GamePlace", foreignColumnName = "gamePartId")
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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
