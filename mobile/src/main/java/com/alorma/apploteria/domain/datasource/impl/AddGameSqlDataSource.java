package com.alorma.apploteria.domain.datasource.impl;

import com.afollestad.inquiry.Inquiry;
import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.datasource.AddGameDataSource;
import rx.Observable;

public class AddGameSqlDataSource implements AddGameDataSource {

  private final Inquiry database;

  public AddGameSqlDataSource(Inquiry database) {
    this.database = database;
  }

  @Override
  public Observable<Boolean> addGame(Game game) {
    return Observable.defer(() -> {
      try {
        Long[] insertedIds = database.insert(Game.class).values(game).run();
        return Observable.just(insertedIds != null && insertedIds.length > 0);
      } catch (Exception e) {
        return Observable.empty();
      }
    });
  }
}
