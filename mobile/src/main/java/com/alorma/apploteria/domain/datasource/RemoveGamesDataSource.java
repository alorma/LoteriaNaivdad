package com.alorma.apploteria.domain.datasource;

import rx.Completable;

public interface RemoveGamesDataSource {
  Completable removeAllGames();
}
