package com.alorma.apploteria.domain.repository;

import rx.Single;

public interface SingleRepository<RESPONSE> {

  Single<RESPONSE> execute();
}
