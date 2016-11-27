package com.alorma.apploteria.domain.bean;

import com.afollestad.inquiry.annotations.Column;
import com.afollestad.inquiry.annotations.Table;

@Table(name = "GamePlace")
public class GamePlace {

  @Column(name = "_id", primaryKey = true, notNull = true, autoIncrement = true) public long id;

  @Column public String name;

  @Column public double latitude;

  @Column public double longitude;

  @Column
  public long gamePartId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }
}
