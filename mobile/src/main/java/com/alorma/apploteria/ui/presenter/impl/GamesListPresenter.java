package com.alorma.apploteria.ui.presenter.impl;

import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.usecase.CompletableUseCase;
import com.alorma.apploteria.domain.usecase.UseCase;
import com.alorma.apploteria.ui.presenter.BaseRxPresenter;
import com.alorma.apploteria.ui.presenter.View;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Scheduler;

public class GamesListPresenter extends BaseRxPresenter<Void, List<Game>, View<List<Game>>> {
  private UseCase<Void, List<Game>> getItemsUseCase;
  private UseCase<Game, Boolean> addGameUseCase;
  private CompletableUseCase removeGameUseCase;

  public GamesListPresenter(UseCase<Void, List<Game>> getItemsUseCase, UseCase<Game, Boolean> addGameUseCase,
      CompletableUseCase removeGameUseCase, Scheduler ioScheduler, Scheduler mainScheduler) {
    super(mainScheduler, ioScheduler, getItemsUseCase);
    this.getItemsUseCase = getItemsUseCase;
    this.addGameUseCase = addGameUseCase;
    this.removeGameUseCase = removeGameUseCase;
    setDefaultIfEmpty(new ArrayList<>());
  }

  public void addGame(Game game) {
    Observable<List<Game>> observable = getItemsUseCase.execute(null);
    Observable<List<Game>> fallbackObservable = Observable.error(new Exception());
    Observable<List<Game>> listObservable = addGameUseCase.execute(game).flatMap(aBoolean -> aBoolean ? observable : fallbackObservable);

    subscribe(listObservable);
  }

  public void removeAllGames() {
    Observable<List<Game>> listObservable = removeGameUseCase.execute().andThen(getItemsUseCase.execute(null));

    subscribe(listObservable);
  }

  @Override
  protected boolean responseIsEmpty(List<Game> games) {
    return games == null || games.isEmpty();
  }
}
