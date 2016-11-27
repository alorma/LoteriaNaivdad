package com.alorma.apploteria.domain.datasource;

import com.afollestad.inquiry.Inquiry;
import com.alorma.apploteria.domain.bean.Game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rx.Observable;

public class SqlGamesDataSource implements GamesDatasource, AddGameDatasource {

  private final Inquiry database;

  public SqlGamesDataSource(Inquiry database) {
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
