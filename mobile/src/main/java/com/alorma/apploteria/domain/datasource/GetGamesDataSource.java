package com.alorma.apploteria.domain.datasource;

import com.alorma.apploteria.domain.bean.Game;
import java.util.List;
import rx.Single;

public interface GetGamesDataSource {

  Single<List<Game>> getList();

}
