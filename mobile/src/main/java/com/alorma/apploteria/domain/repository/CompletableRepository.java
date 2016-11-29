package com.alorma.apploteria.domain.repository;

import rx.Completable;

public interface CompletableRepository<K> {

  Completable execute(K k);

}
