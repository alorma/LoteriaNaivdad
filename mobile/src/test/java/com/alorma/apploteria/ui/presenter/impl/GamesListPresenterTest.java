package com.alorma.apploteria.ui.presenter.impl;

import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.bean.GamePart;
import com.alorma.apploteria.domain.bean.GamePlace;
import com.alorma.apploteria.domain.datasource.AddGameDataSource;
import com.alorma.apploteria.domain.datasource.GetGamesDataSource;
import com.alorma.apploteria.domain.datasource.RemoveGamesDataSource;
import com.alorma.apploteria.domain.repository.AddGameRepository;
import com.alorma.apploteria.domain.repository.CompletableRepository;
import com.alorma.apploteria.domain.repository.GetGamesRepository;
import com.alorma.apploteria.domain.repository.RemoveAllGamesRepository;
import com.alorma.apploteria.domain.repository.Repository;
import com.alorma.apploteria.domain.usecase.CompletableUseCase;
import com.alorma.apploteria.domain.usecase.UseCase;
import com.alorma.apploteria.domain.usecase.impl.GetGamesUseCase;
import com.alorma.apploteria.ui.presenter.View;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Completable;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GamesListPresenterTest {

  private static final Scheduler IO_SCHEDULER = Schedulers.trampoline();
  private static final Scheduler MAIN_SCHEDULER = Schedulers.trampoline();

  @Mock UseCase<Void, List<Game>> getItemsUseCase;
  @Mock CompletableUseCase<Game> addGamesUseCase;
  @Mock CompletableUseCase<Void> removeGamesUseCase;

  @Mock View<List<Game>> view;

  private GamesListPresenter gamesListPresenter;

  @Mock Game game1;
  @Mock Game game2;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    gamesListPresenter = new GamesListPresenter(getItemsUseCase, addGamesUseCase, removeGamesUseCase, IO_SCHEDULER, MAIN_SCHEDULER);

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
    givenErrorGetUseCase();

    gamesListPresenter.execute(null);

    verify(view).showError(any(Throwable.class));
  }

  @Test
  public void shouldReturnData_whenAddNewGame() {
    GamesListPresenter gamesListPresenter = givenInMemoryPresenter();

    gamesListPresenter.addGame(getNewGame());

    verify(view).onDataReceived(anyListOf(Game.class));
  }

  @Test
  public void shouldReturnEMpty_whenRemoveAllGames() {
    GamesListPresenter gamesListPresenter = givenInMemoryPresenter();

    gamesListPresenter.addGame(getNewGame());
    gamesListPresenter.addGame(getNewGame());
    gamesListPresenter.addGame(getNewGame());
    gamesListPresenter.addGame(getNewGame());
    gamesListPresenter.addGame(getNewGame());

    gamesListPresenter.removeAllGames();

    verify(view).onDataEmpty();
  }

  private GamesListPresenter givenInMemoryPresenter() {
    InMemoryDataSource ds = new InMemoryDataSource();

    Repository<Void, List<Game>> getGamesRepository = new GetGamesRepository(ds);
    UseCase<Void, List<Game>> getAllItemsUseCase = new GetGamesUseCase(getGamesRepository);

    CompletableRepository<Game> addGamesRepository = new AddGameRepository(ds);
    CompletableUseCase<Game> addNewItemUseCase = new CompletableUseCase<>(addGamesRepository);

    CompletableRepository<Void> removeAllItemsRepository = new RemoveAllGamesRepository(ds);
    CompletableUseCase<Void> removeAllGamesUseCase = new CompletableUseCase<>(removeAllItemsRepository);
    GamesListPresenter presenter =
        new GamesListPresenter(getAllItemsUseCase, addNewItemUseCase, removeAllGamesUseCase, IO_SCHEDULER, MAIN_SCHEDULER);

    presenter.attachView(view);

    return presenter;
  }

  private class InMemoryDataSource implements AddGameDataSource, GetGamesDataSource, RemoveGamesDataSource {

    private List<Game> games;

    public InMemoryDataSource() {
      this.games = new ArrayList<>();
    }

    @Override
    public Completable addGame(Game game) {
      return Completable.fromAction(() -> this.games.add(game));
    }

    @Override
    public Observable<List<Game>> getList() {
      return Observable.just(games);
    }

    @Override
    public Completable removeAllGames() {
      return Completable.fromAction(() -> this.games.clear());
    }
  }

  private Game getNewGame() {
    GamePlace place = mock(GamePlace.class);
    GamePart part = mock(GamePart.class);

    when(part.getPlace()).thenReturn(place);

    Game game = mock(Game.class);

    List<GamePart> parts = new ArrayList<>();
    parts.add(part);

    when(game.getParts()).thenReturn(parts);

    return game;
  }

  private void givenEmptyGetUseCase() {
    when(getItemsUseCase.execute(any())).thenReturn(Observable.empty());
  }

  private void givenErrorGetUseCase() {
    when(getItemsUseCase.execute(any())).thenReturn(Observable.error(new Exception()));
  }

  private void givenNotEmptyGetUseCase() {
    when(getItemsUseCase.execute(any())).thenReturn(Observable.just(game1, game2).toList());
  }

  private void givenEmptyListGetUseCase() {
    when(getItemsUseCase.execute(any())).thenReturn(Observable.just(new ArrayList<>()));
  }
}