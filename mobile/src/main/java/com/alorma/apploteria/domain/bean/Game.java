package com.alorma.apploteria.domain.bean;

import java.util.List;

public class Game {
  private String number;
  private int color;
  private List<GamePart> parts;

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
}
