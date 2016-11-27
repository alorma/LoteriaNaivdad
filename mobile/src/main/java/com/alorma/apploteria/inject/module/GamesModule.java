package com.alorma.apploteria.inject.module;

import android.graphics.Color;
import com.alorma.apploteria.domain.GamesDatasource;
import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.bean.GamePart;
import com.alorma.apploteria.domain.repository.GetGamesRepository;
import com.alorma.apploteria.domain.repository.Repository;
import com.alorma.apploteria.domain.usecase.GetGamesUseCase;
import com.alorma.apploteria.domain.usecase.UseCase;
import com.alorma.apploteria.inject.named.IOScheduler;
import com.alorma.apploteria.inject.named.MainScheduler;
import com.alorma.apploteria.inject.scope.PerActivity;
import com.alorma.apploteria.ui.presenter.impl.GamesListPresenter;
import dagger.Module;
import dagger.Provides;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Scheduler;

@Module public class GamesModule {

  @Provides
  @PerActivity
  GamesDatasource getGamesDatasource() {
    return () -> {
      List<Game> gameList = new ArrayList<>();

      Game game1 = new Game();
      game1.setColor(Color.CYAN);
      game1.setNumber("89891");
      game1.setParts(new ArrayList<>());
      GamePart gamePart1 = new GamePart();
      gamePart1.setTitle("Loteria del curro");
      gamePart1.setAmount(20.0);
      gamePart1.setCurrency("€");
      game1.getParts().add(gamePart1);

      gameList.add(game1);

      Game game2 = new Game();
      game2.setColor(Color.GREEN);
      game2.setNumber("71891");
      game2.setParts(new ArrayList<>());
      GamePart gamePart2 = new GamePart();
      gamePart2.setTitle("Loteria del curro");
      gamePart2.setAmount(20.0);
      gamePart2.setCurrency("€");
      game2.getParts().add(gamePart2);
      gameList.add(game2);

      return Observable.just(gameList);
    };
  }

  @Provides
  @PerActivity
  Repository<Void, List<Game>> getGamesRepository(GamesDatasource datasource) {
    return new GetGamesRepository(datasource);
  }

  @Provides
  @PerActivity
  UseCase<Void, List<Game>> provideGetGamesUseCase(Repository<Void, List<Game>> repository) {
    return new GetGamesUseCase(repository);
  }

  @Provides
  @PerActivity
  GamesListPresenter getGamesListPresenter(UseCase<Void, List<Game>> useCase, @IOScheduler Scheduler ioScheduler,
      @MainScheduler Scheduler mainScheduler) {
    return new GamesListPresenter(useCase, ioScheduler, mainScheduler);
  }
}
