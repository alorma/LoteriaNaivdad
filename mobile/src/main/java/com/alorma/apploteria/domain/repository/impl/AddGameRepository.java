package com.alorma.apploteria.domain.repository.impl;

import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.datasource.AddGameDataSource;
import com.alorma.apploteria.domain.repository.CompletableRepository;
import rx.Completable;
import rx.Observable;

public class AddGameRepository implements CompletableRepository<Game> {
  private AddGameDataSource datasource;

  public AddGameRepository(AddGameDataSource datasource) {
    this.datasource = datasource;
  }

  @Override
  public Completable execute(Game game) {
    return datasource.addGame(game);
  }
}
