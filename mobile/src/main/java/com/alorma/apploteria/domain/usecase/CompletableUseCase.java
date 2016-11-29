package com.alorma.apploteria.domain.usecase;

import com.alorma.apploteria.domain.repository.CommpletableRepository;
import rx.Completable;

public class CompletableUseCase {

  private CommpletableRepository repository;

  public CompletableUseCase(CommpletableRepository repository) {
    this.repository = repository;
  }

  public Completable execute() {
    return repository.execute();
  }
}
