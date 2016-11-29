package com.alorma.apploteria.inject.module;

import android.content.Context;
import com.afollestad.inquiry.Inquiry;
import com.alorma.apploteria.domain.ResourceCleanUp;
import com.alorma.apploteria.domain.bean.Game;
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

  public GamesModule() {

  }

  @Provides
  @PerActivity
  public Inquiry getDatabase(Context context) {
    return Inquiry.newInstance(context, "GAMES").build();
  }

  @Provides
  @PerActivity
  SqlGamesDataSource provideDatabase(Inquiry database) {
    return new SqlGamesDataSource(database);
  }

  @Provides
  @PerActivity
  ResourceCleanUp getCleanUp(SqlGamesDataSource dataSource) {
    return dataSource;
  }

  @Provides
  @PerActivity
  Repository<Void, List<Game>> getGamesRepository(SqlGamesDataSource dataSource) {
    return new GetGamesRepository(dataSource);
  }

  @Provides
  @PerActivity
  Repository<Game, Boolean> getAddGameRepository(SqlGamesDataSource dataSource) {
    return new AddGameRepository(dataSource);
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
