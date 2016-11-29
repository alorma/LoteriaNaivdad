package com.alorma.apploteria.domain.datasource.impl;

import com.afollestad.inquiry.Inquiry;
import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.datasource.GetGamesDataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rx.Observable;

public class GetGamesSqlDataSource implements GetGamesDataSource {

  private final Inquiry database;

  public GetGamesSqlDataSource(Inquiry database) {
    this.database = database;
  }

  @Override
  public Observable<List<Game>> getList() {
    return Observable.fromCallable(() -> database.select(Game.class).all()).flatMap(games -> {
      if (games != null) {
        return Observable.just(games);
      } else {
        return Observable.empty();
      }
    }).map(Arrays::asList).switchIfEmpty(Observable.just(new ArrayList<>()));
  }
}
