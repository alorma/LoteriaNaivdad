package com.alorma.apploteria.domain.repository;

import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.datasource.GetGamesDataSource;
import java.util.List;
import rx.Observable;

public class GetGamesRepository implements Repository<Void, List<Game>> {
  private GetGamesDataSource datasource;

  public GetGamesRepository(GetGamesDataSource datasource) {
    this.datasource = datasource;
  }

  @Override
  public Observable<List<Game>> execute(Void aVoid) {
    return datasource.getList();
  }
}
