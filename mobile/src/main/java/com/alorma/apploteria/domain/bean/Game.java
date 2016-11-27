package com.alorma.apploteria.domain.bean;

import com.afollestad.inquiry.annotations.Column;
import com.afollestad.inquiry.annotations.ForeignKey;
import com.afollestad.inquiry.annotations.Table;
import java.util.List;

@Table
public class Game {

  @Column(name = "_id", primaryKey = true, notNull = true, autoIncrement = true)
  public long id;

  @Column(notNull = true)
  public String number;

  @Column
  public int color;

  @ForeignKey(tableName = "GamePart", foreignColumnName = "gameId")
  private List<GamePart> parts;

  public Game() {

  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    this.color = color;
  }

  public List<GamePart> getParts() {
    return parts;
  }

  public void setParts(List<GamePart> parts) {
    this.parts = parts;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
