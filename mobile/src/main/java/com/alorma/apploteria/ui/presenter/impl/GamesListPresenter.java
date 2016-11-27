package com.alorma.apploteria.ui.presenter.impl;

import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.usecase.UseCase;
import com.alorma.apploteria.ui.presenter.BaseRxPresenter;
import com.alorma.apploteria.ui.presenter.View;
import java.util.List;
import rx.Observable;
import rx.Scheduler;

public class GamesListPresenter extends BaseRxPresenter<Void, List<Game>, View<List<Game>>> {
  private UseCase<Void, List<Game>> getItemsUseCase;
  private UseCase<Game, Boolean> addGameUseCase;

  public GamesListPresenter(UseCase<Void, List<Game>> getItemsUseCase, UseCase<Game, Boolean> addGameUseCase, Scheduler ioScheduler,
      Scheduler mainScheduler) {
    super(mainScheduler, ioScheduler, getItemsUseCase);
    this.getItemsUseCase = getItemsUseCase;
    this.addGameUseCase = addGameUseCase;
  }

  public void addGame(Game game) {
    Observable<List<Game>> listObservable =
        addGameUseCase.execute(game)
            .flatMap(aBoolean -> aBoolean ? getItemsUseCase.execute(null) : Observable.error(new Exception()));

    subscribe(listObservable);
  }
}
