package com.alorma.apploteria.domain.repository.impl;

import com.alorma.apploteria.domain.datasource.RemoveGamesDataSource;
import com.alorma.apploteria.domain.repository.CompletableRepository;
import rx.Completable;

public class RemoveAllGamesRepository implements CompletableRepository<Void> {

  private RemoveGamesDataSource removeAllGamesSqlDataSource;

  public RemoveAllGamesRepository(RemoveGamesDataSource removeAllGamesSqlDataSource) {
    this.removeAllGamesSqlDataSource = removeAllGamesSqlDataSource;
  }

  @Override
  public Completable execute(Void aVoid) {
    return removeAllGamesSqlDataSource.removeAllGames();
  }
}
