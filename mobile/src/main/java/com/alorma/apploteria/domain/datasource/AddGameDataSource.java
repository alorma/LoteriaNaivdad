package com.alorma.apploteria.domain.datasource;

import com.alorma.apploteria.domain.bean.Game;
import rx.Observable;

public interface AddGameDataSource {
  Observable<Boolean> addGame(Game game);
}
