package com.alorma.apploteria.domain.repository.impl;

import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.datasource.GetGamesDataSource;
import com.alorma.apploteria.domain.repository.SingleRepository;
import java.util.List;
import rx.Single;

public class GetGamesRepository implements SingleRepository<List<Game>> {
  private GetGamesDataSource datasource;

  public GetGamesRepository(GetGamesDataSource datasource) {
    this.datasource = datasource;
  }

  @Override
  public Single<List<Game>> execute() {
    return datasource.getList();
  }
}
