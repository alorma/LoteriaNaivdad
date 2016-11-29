package com.alorma.apploteria.domain.datasource;

import com.alorma.apploteria.domain.bean.Game;
import rx.Completable;

public interface AddGameDataSource {
  Completable addGame(Game game);
}
