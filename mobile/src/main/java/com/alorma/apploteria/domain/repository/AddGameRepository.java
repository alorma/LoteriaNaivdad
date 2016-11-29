package com.alorma.apploteria.domain.repository;

import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.datasource.AddGameDataSource;
import rx.Observable;

public class AddGameRepository implements Repository<Game, Boolean> {
  private AddGameDataSource datasource;

  public AddGameRepository(AddGameDataSource datasource) {
    this.datasource = datasource;
  }

  @Override
  public Observable<Boolean> execute(Game game) {
    return datasource.addGame(game);
  }
}
