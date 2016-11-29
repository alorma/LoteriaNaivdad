package com.alorma.apploteria.inject.component;

import com.alorma.apploteria.inject.module.GamesModule;
import com.alorma.apploteria.inject.scope.PerActivity;
import com.alorma.apploteria.ui.fragment.GamesListFragment;
import com.alorma.apploteria.ui.presenter.impl.GamesListPresenter;
import dagger.Component;

@PerActivity @Component(modules = GamesModule.class, dependencies = ApplicationComponent.class) public interface GamesComponent {

  GamesListPresenter getGamesListPresenter();

  void inject(GamesListFragment gamesListFragment);
}
