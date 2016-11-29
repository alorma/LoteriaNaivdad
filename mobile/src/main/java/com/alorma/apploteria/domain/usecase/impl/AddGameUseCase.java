package com.alorma.apploteria.domain.usecase.impl;

import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.repository.Repository;
import com.alorma.apploteria.domain.usecase.UseCase;
import rx.Observable;

public class AddGameUseCase implements UseCase<Game, Boolean> {

  private Repository<Game, Boolean> repository;

  public AddGameUseCase(Repository<Game, Boolean> repository) {
    this.repository = repository;
  }

  @Override
  public Observable<Boolean> execute(Game game) {
    return repository.execute(game);
  }
}
