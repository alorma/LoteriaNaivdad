package com.alorma.apploteria.domain;

import com.alorma.apploteria.domain.bean.Game;
import java.util.List;
import rx.Observable;

public interface GamesDatasource {

  Observable<List<Game>> getList();

}
