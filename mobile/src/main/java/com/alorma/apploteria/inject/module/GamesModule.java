package com.alorma.apploteria.inject.module;

import android.content.Context;
import com.afollestad.inquiry.Inquiry;
import com.alorma.apploteria.domain.ResourceCleanUp;
import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.datasource.AddGameDataSource;
import com.alorma.apploteria.domain.datasource.GetGamesDataSource;
import com.alorma.apploteria.domain.datasource.RemoveGamesDataSource;
import com.alorma.apploteria.domain.datasource.impl.AddGameSqlDataSource;
import com.alorma.apploteria.domain.datasource.impl.GetGamesSqlDataSource;
import com.alorma.apploteria.domain.datasource.impl.RemoveAllGamesSqlDataSource;
import com.alorma.apploteria.domain.repository.AddGameRepository;
import com.alorma.apploteria.domain.repository.CommpletableRepository;
import com.alorma.apploteria.domain.repository.GetGamesRepository;
import com.alorma.apploteria.domain.repository.RemoveAllGamesRepository;
import com.alorma.apploteria.domain.repository.Repository;
import com.alorma.apploteria.domain.usecase.impl.AddGameUseCase;
import com.alorma.apploteria.domain.usecase.CompletableUseCase;
import com.alorma.apploteria.domain.usecase.impl.GetGamesUseCase;
import com.alorma.apploteria.domain.usecase.UseCase;
import com.alorma.apploteria.inject.named.IOScheduler;
import com.alorma.apploteria.inject.named.MainScheduler;
import com.alorma.apploteria.inject.named.RemoveItems;
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
  ResourceCleanUp getCleanUp(Inquiry database) {
    return database::destroyInstance;
  }

  @Provides
  @PerActivity
  GetGamesDataSource providesGetGamesDataSource(Inquiry database) {
    return new GetGamesSqlDataSource(database);
  }

  @Provides
  @PerActivity
  AddGameDataSource providesAddGameDataSource(Inquiry database) {
    return new AddGameSqlDataSource(database);
  }

  @Provides
  @PerActivity
  RemoveGamesDataSource providesRemoveAllGamesDataSource(Inquiry database) {
    return new RemoveAllGamesSqlDataSource(database);
  }

  @Provides
  @PerActivity
  Repository<Void, List<Game>> getGamesRepository(GetGamesDataSource dataSource) {
    return new GetGamesRepository(dataSource);
  }

  @Provides
  @PerActivity
  Repository<Game, Boolean> getAddGameRepository(AddGameDataSource dataSource) {
    return new AddGameRepository(dataSource);
  }

  @Provides
  @PerActivity
  @RemoveItems
  CommpletableRepository getRemoveGamesRepository(RemoveGamesDataSource dataSource) {
    return new RemoveAllGamesRepository(dataSource);
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
  CompletableUseCase providesRemoveAllItemsUseCase(@RemoveItems CommpletableRepository repository) {
    return new CompletableUseCase(repository);
  }

  @Provides
  @PerActivity
  GamesListPresenter getGamesListPresenter(UseCase<Void, List<Game>> getGamesUseCase, UseCase<Game, Boolean> addGameUseCase,
      CompletableUseCase removeAllGamesUseCase, @IOScheduler Scheduler ioScheduler, @MainScheduler Scheduler mainScheduler) {
    return new GamesListPresenter(getGamesUseCase, addGameUseCase, removeAllGamesUseCase, ioScheduler, mainScheduler);
  }
}
