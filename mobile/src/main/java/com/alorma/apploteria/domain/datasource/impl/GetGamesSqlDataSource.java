package com.alorma.apploteria.domain.datasource.impl;

import com.afollestad.inquiry.Inquiry;
import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.datasource.GetGamesDataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rx.Single;

public class GetGamesSqlDataSource implements GetGamesDataSource {

  private final Inquiry database;

  public GetGamesSqlDataSource(Inquiry database) {
    this.database = database;
  }

  @Override
  public Single<List<Game>> getList() {
    return Single.fromCallable(() -> {
      Game[] all = database.select(Game.class).all();
      if (all != null) {
        return Arrays.asList(all);
      } else {
        return new ArrayList<Game>();
      }
    });
  }
}
