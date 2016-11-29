package com.alorma.apploteria.inject.component;

import android.content.Context;
import com.alorma.apploteria.inject.module.ApplicationModule;
import com.alorma.apploteria.inject.module.GamesModule;
import com.alorma.apploteria.inject.named.ComputationScheduler;
import com.alorma.apploteria.inject.named.IOScheduler;
import com.alorma.apploteria.inject.named.MainScheduler;
import com.alorma.apploteria.ui.activity.BaseActivity;
import com.alorma.apploteria.ui.fragment.BaseFragment;
import dagger.Component;
import javax.inject.Singleton;
import rx.Scheduler;

@Singleton @Component(modules = ApplicationModule.class) public interface ApplicationComponent {

  Context getContext();

  @MainScheduler
  Scheduler provideMainScheduler();

  @IOScheduler
  Scheduler provideIOScheduler();

  @ComputationScheduler
  Scheduler provideComputationScheduler();

  void inject(BaseActivity baseActivity);

  void inject(BaseFragment baseFragment);
}
