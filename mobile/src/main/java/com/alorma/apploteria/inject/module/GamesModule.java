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
import com.alorma.apploteria.domain.repository.CompletableRepository;
import com.alorma.apploteria.domain.repository.SingleRepository;
import com.alorma.apploteria.domain.repository.impl.AddGameRepository;
import com.alorma.apploteria.domain.repository.impl.GetGamesRepository;
import com.alorma.apploteria.domain.repository.impl.RemoveAllGamesRepository;
import com.alorma.apploteria.domain.usecase.CompletableUseCase;
import com.alorma.apploteria.domain.usecase.SingleUseCase;
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
  SingleRepository<List<Game>> getGamesRepository(GetGamesDataSource dataSource) {
    return new GetGamesRepository(dataSource);
  }

  @Provides
  @PerActivity
  CompletableRepository<Game> getAddGameRepository(AddGameDataSource dataSource) {
    return new AddGameRepository(dataSource);
  }

  @Provides
  @PerActivity
  @RemoveItems
  CompletableRepository<Void> getRemoveGamesRepository(RemoveGamesDataSource dataSource) {
    return new RemoveAllGamesRepository(dataSource);
  }

  @Provides
  @PerActivity
  SingleUseCase<List<Game>> provideGetGamesUseCase(SingleRepository<List<Game>> repository) {
    return new SingleUseCase<>(repository);
  }

  @Provides
  @PerActivity
  CompletableUseCase<Game> getAddGameUseCase(CompletableRepository<Game> repository) {
    return new CompletableUseCase<>(repository);
  }

  @Provides
  @PerActivity
  CompletableUseCase<Void> providesRemoveAllItemsUseCase(@RemoveItems CompletableRepository<Void> repository) {
    return new CompletableUseCase<>(repository);
  }

  @Provides
  @PerActivity
  GamesListPresenter getGamesListPresenter(SingleUseCase<List<Game>> getGamesUseCase, CompletableUseCase<Game> addGameUseCase,
      CompletableUseCase<Void> removeAllGamesUseCase, @IOScheduler Scheduler ioScheduler, @MainScheduler Scheduler mainScheduler) {
    return new GamesListPresenter(getGamesUseCase, addGameUseCase, removeAllGamesUseCase, ioScheduler, mainScheduler);
  }
}
