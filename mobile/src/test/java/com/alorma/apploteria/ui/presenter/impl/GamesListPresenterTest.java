package com.alorma.apploteria.ui.presenter.impl;

import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.usecase.UseCase;
import com.alorma.apploteria.ui.presenter.View;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GamesListPresenterTest {

  private static final Scheduler IO_SCHEDULER = Schedulers.trampoline();
  private static final Scheduler MAIN_SCHEDULER = Schedulers.trampoline();

  @Mock UseCase<Void, List<Game>> getItemsUseCase;
  @Mock UseCase<Game, Boolean> addGamesUseCase;
  @Mock View<List<Game>> view;

  private GamesListPresenter gamesListPresenter;

  @Mock Game game1;
  @Mock Game game2;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    gamesListPresenter = new GamesListPresenter(getItemsUseCase, addGamesUseCase, IO_SCHEDULER, MAIN_SCHEDULER);
    gamesListPresenter.attachView(view);
  }

  @Test
  public void shouldCallShowLoading_whenStart() {
    givenEmptyGetUseCase();

    gamesListPresenter.execute(null);

    verify(view).showLoading();
  }

  @Test
  public void shouldCallHideLoading_whenUseCaseFinish() {
    givenEmptyGetUseCase();

    gamesListPresenter.execute(null);

    verify(view).hideLoading();
  }

  @Test
  public void shouldCallHideLoading_whenUseCaseWithData() {
    givenNotEmptyGetUseCase();

    gamesListPresenter.execute(null);

    verify(view).hideLoading();
  }

  @Test
  public void shouldCallOnDataLoaded_whenUseCaseWithData() {
    givenNotEmptyGetUseCase();

    gamesListPresenter.execute(null);

    verify(view).onDataReceived(anyListOf(Game.class));
  }

  @Test
  public void shouldCallOnDataEmpty_whenUseCaseWithEmptyData() {
    givenEmptyListGetUseCase();

    gamesListPresenter.execute(null);

    verify(view).onDataEmpty();
  }

  @Test
  public void shouldCallOnDataEmpty_whenUseCaseWithEmptyObservable() {
    givenEmptyGetUseCase();

    gamesListPresenter.execute(null);

    verify(view).onDataEmpty();
  }

  @Test
  public void shouldCallShowError_whenUseCaseThrowsError() {
    givenErrotGetUseCase();

    gamesListPresenter.execute(null);

    verify(view).showError(any(Throwable.class));
  }

  private void givenEmptyGetUseCase() {
    when(getItemsUseCase.execute(any())).thenReturn(Observable.empty());
  }
  private void givenErrotGetUseCase() {
    when(getItemsUseCase.execute(any())).thenReturn(Observable.error(new Exception()));
  }

  private void givenNotEmptyGetUseCase() {
    when(getItemsUseCase.execute(any())).thenReturn(Observable.just(game1, game2).toList());
  }

  private void givenEmptyListGetUseCase() {
    when(getItemsUseCase.execute(any())).thenReturn(Observable.just(new ArrayList<>()));
  }
}