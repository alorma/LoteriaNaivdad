package com.alorma.apploteria.domain.datasource;

import com.alorma.apploteria.domain.bean.Game;
import java.util.List;
import rx.Observable;

public interface GetGamesDataSource {

  Observable<List<Game>> getList();

}
