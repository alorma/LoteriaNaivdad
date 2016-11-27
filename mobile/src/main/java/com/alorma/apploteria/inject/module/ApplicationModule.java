package com.alorma.apploteria.inject.module;

import android.content.Context;
import com.alorma.apploteria.inject.named.ComputationScheduler;
import com.alorma.apploteria.inject.named.IOScheduler;
import com.alorma.apploteria.inject.named.MainScheduler;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module public class ApplicationModule {
  private Context application;

  public ApplicationModule(Context application) {
    this.application = application;
  }

  @Provides
  @Singleton
  @MainScheduler
  Scheduler provideMainScheduler() {
    return AndroidSchedulers.mainThread();
  }

  @Provides
  @Singleton
  @IOScheduler
  Scheduler provideIOScheduler() {
    return Schedulers.io();
  }

  @Provides
  @Singleton
  @ComputationScheduler
  Scheduler provideComputationScheduler() {
    return Schedulers.computation();
  }

  @Provides
  @Singleton
  Context providesApplicationContext() {
    return this.application;
  }
}
