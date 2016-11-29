package com.alorma.apploteria.domain.usecase;

import com.alorma.apploteria.domain.repository.SingleRepository;
import rx.Single;

public class SingleUseCase<RESPONSE> {
  private SingleRepository<RESPONSE> repository;

  public SingleUseCase(SingleRepository<RESPONSE> repository) {
    this.repository = repository;
  }

  public Single<RESPONSE> execute() {
    return repository.execute();
  }
}
