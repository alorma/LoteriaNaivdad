package com.alorma.apploteria.domain.datasource.impl;

import com.afollestad.inquiry.Inquiry;
import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.bean.GamePart;
import com.alorma.apploteria.domain.bean.GamePlace;
import com.alorma.apploteria.domain.datasource.RemoveGamesDataSource;
import rx.Completable;

public class RemoveAllGamesSqlDataSource implements RemoveGamesDataSource {
  private Inquiry inquiry;

  public RemoveAllGamesSqlDataSource(Inquiry inquiry) {
    this.inquiry = inquiry;
  }

  @Override
  public Completable removeAllGames() {
    return Completable.fromAction(() -> {
      inquiry.dropTable(GamePlace.class);
      inquiry.dropTable(GamePart.class);
      inquiry.dropTable(Game.class);
    });
  }
}
