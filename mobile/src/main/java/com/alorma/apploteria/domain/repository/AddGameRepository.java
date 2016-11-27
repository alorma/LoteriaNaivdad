package com.alorma.apploteria.domain.repository;

import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.datasource.AddGameDatasource;
import rx.Observable;

public class AddGameRepository implements Repository<Game, Boolean> {
  private AddGameDatasource datasource;

  public AddGameRepository(AddGameDatasource datasource) {
    this.datasource = datasource;
  }

  @Override
  public Observable<Boolean> execute(Game game) {
    return datasource.addGame(game);
  }
}
