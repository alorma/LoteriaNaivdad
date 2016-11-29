package com.alorma.apploteria.domain.datasource.impl;

import com.afollestad.inquiry.Inquiry;
import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.datasource.AddGameDataSource;
import rx.Completable;

public class AddGameSqlDataSource implements AddGameDataSource {

  private final Inquiry database;

  public AddGameSqlDataSource(Inquiry database) {
    this.database = database;
  }

  @Override
  public Completable addGame(Game game) {
    return Completable.fromAction(() -> database.insert(Game.class).values(game).run());
  }
}
