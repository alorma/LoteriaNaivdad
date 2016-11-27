package com.alorma.apploteria.domain.repository;

import rx.Observable;

public interface Repository<REQUEST, RESPONSE> {

  Observable<RESPONSE> execute(REQUEST request);

}
