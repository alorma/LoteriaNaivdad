package com.alorma.apploteria.domain.usecase.impl;

import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.repository.Repository;
import com.alorma.apploteria.domain.usecase.UseCase;
import java.util.List;
import rx.Observable;

public class GetGamesUseCase implements UseCase<Void,List<Game>> {

  private Repository<Void, List<Game>> repository;

  public GetGamesUseCase(Repository<Void, List<Game>> repository) {
    this.repository = repository;
  }

  @Override
  public Observable<List<Game>> execute(Void aVoid) {
    return repository.execute(aVoid);
  }
}
