package com.alorma.apploteria.domain.usecase;

import rx.Observable;

public interface UseCase<REQUEST, RESPONSE> {

  Observable<RESPONSE> execute(REQUEST request);

}
