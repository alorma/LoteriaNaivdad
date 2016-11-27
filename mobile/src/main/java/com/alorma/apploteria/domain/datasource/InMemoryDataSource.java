package com.alorma.apploteria.domain.datasource;

import com.alorma.apploteria.domain.bean.Game;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;

public class InMemoryDataSource implements GamesDatasource, AddGameDatasource {
  private List<Game> list = new ArrayList<>();

  @Override
  public Observable<List<Game>> getList() {
    return Observable.just(list);
  }

  @Override
  public Observable<Boolean> addGame(Game game) {
    return Observable.fromCallable(() -> list.add(game));
  }
}
