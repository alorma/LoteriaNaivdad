package com.alorma.apploteria.ui.presenter.impl;

import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.usecase.CompletableUseCase;
import com.alorma.apploteria.domain.usecase.SingleUseCase;
import com.alorma.apploteria.ui.presenter.BaseRxPresenter;
import com.alorma.apploteria.ui.presenter.View;
import java.util.ArrayList;
import java.util.List;
import rx.Scheduler;
import rx.Single;

public class GamesListPresenter extends BaseRxPresenter<Void, List<Game>, View<List<Game>>> {
  private SingleUseCase<List<Game>> getItemsUseCase;
  private CompletableUseCase<Game> addGameUseCase;
  private CompletableUseCase<Void> removeGameUseCase;

  public GamesListPresenter(SingleUseCase<List<Game>> getItemsUseCase, CompletableUseCase<Game> addGameUseCase,
      CompletableUseCase<Void> removeGameUseCase, Scheduler ioScheduler, Scheduler mainScheduler) {
    super(mainScheduler, ioScheduler, getItemsUseCase);
    this.getItemsUseCase = getItemsUseCase;
    this.addGameUseCase = addGameUseCase;
    this.removeGameUseCase = removeGameUseCase;

    setDefaultIfEmpty(new ArrayList<>());
  }

  public void addGame(Game game) {
    Single<List<Game>> listObservable = addGameUseCase.execute(game).andThen(getItemsUseCase.execute());

    subscribe(listObservable);
  }

  public void removeAllGames() {
    Single<List<Game>> listObservable = removeGameUseCase.execute(null).andThen(getItemsUseCase.execute());

    subscribe(listObservable);
  }

  @Override
  protected boolean responseIsEmpty(List<Game> games) {
    return games == null || games.isEmpty();
  }
}
