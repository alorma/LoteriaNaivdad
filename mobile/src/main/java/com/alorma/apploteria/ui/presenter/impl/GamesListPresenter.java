package com.alorma.apploteria.ui.presenter.impl;

import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.usecase.UseCase;
import com.alorma.apploteria.ui.presenter.BaseRxPresenter;
import com.alorma.apploteria.ui.presenter.View;
import java.util.List;
import rx.Scheduler;

public class GamesListPresenter extends BaseRxPresenter<Void, List<Game>, View<List<Game>>> {
  public GamesListPresenter(UseCase<Void, List<Game>> useCase, Scheduler ioScheduler, Scheduler mainScheduler) {
    super(ioScheduler, mainScheduler, useCase);
  }
}
