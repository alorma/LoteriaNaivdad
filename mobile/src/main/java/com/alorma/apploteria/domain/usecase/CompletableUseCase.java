package com.alorma.apploteria.domain.usecase;

import com.alorma.apploteria.domain.repository.CompletableRepository;
import rx.Completable;

public class CompletableUseCase<K> {

  private CompletableRepository<K> repository;

  public CompletableUseCase(CompletableRepository<K> repository) {
    this.repository = repository;
  }

  public Completable execute(K k) {
    return repository.execute(k);
  }
}
