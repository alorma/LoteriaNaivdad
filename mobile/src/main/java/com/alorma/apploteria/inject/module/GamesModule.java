package com.alorma.apploteria.inject.module;

import com.afollestad.inquiry.Inquiry;
import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.datasource.AddGameDatasource;
import com.alorma.apploteria.domain.datasource.GamesDatasource;
import com.alorma.apploteria.domain.datasource.SqlGamesDataSource;
import com.alorma.apploteria.domain.repository.AddGameRepository;
import com.alorma.apploteria.domain.repository.GetGamesRepository;
import com.alorma.apploteria.domain.repository.Repository;
import com.alorma.apploteria.domain.usecase.AddGameUseCase;
import com.alorma.apploteria.domain.usecase.GetGamesUseCase;
import com.alorma.apploteria.domain.usecase.UseCase;
import com.alorma.apploteria.inject.named.IOScheduler;
import com.alorma.apploteria.inject.named.MainScheduler;
import com.alorma.apploteria.inject.scope.PerActivity;
import com.alorma.apploteria.ui.presenter.impl.GamesListPresenter;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import rx.Scheduler;

@Module public class GamesModule {

  private GamesDatasource gamesDatasource;
  private AddGameDatasource addGameDatasource;

  public GamesModule(Inquiry database) {
    SqlGamesDataSource dataSource = new SqlGamesDataSource(database);
    gamesDatasource = dataSource;
    addGameDatasource = dataSource;
  }

  @Provides
  @PerActivity
  GamesDatasource getGamesDatasource() {
    return gamesDatasource;
  }

  @Provides
  @PerActivity
  AddGameDatasource getAddGameDatasource() {
    return addGameDatasource;
  }

  @Provides
  @PerActivity
  Repository<Void, List<Game>> getGamesRepository(GamesDatasource datasource) {
    return new GetGamesRepository(datasource);
  }

  @Provides
  @PerActivity
  Repository<Game, Boolean> getAddGameRepository(AddGameDatasource datasource) {
    return new AddGameRepository(datasource);
  }

  @Provides
  @PerActivity
  UseCase<Void, List<Game>> provideGetGamesUseCase(Repository<Void, List<Game>> repository) {
    return new GetGamesUseCase(repository);
  }

  @Provides
  @PerActivity
  UseCase<Game, Boolean> getAddGameUseCase(Repository<Game, Boolean> repository) {
    return new AddGameUseCase(repository);
  }

  @Provides
  @PerActivity
  GamesListPresenter getGamesListPresenter(UseCase<Void, List<Game>> getGamesUseCase, UseCase<Game, Boolean> addGameUseCase,
      @IOScheduler Scheduler ioScheduler, @MainScheduler Scheduler mainScheduler) {
    return new GamesListPresenter(getGamesUseCase, addGameUseCase, ioScheduler, mainScheduler);
  }
}
