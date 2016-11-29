package com.alorma.apploteria.domain.repository;

import rx.Completable;

public interface CommpletableRepository {

  Completable execute();

}
