package com.alorma.apploteria;

import android.app.Application;
import com.alorma.apploteria.inject.component.ApplicationComponent;
import com.alorma.apploteria.inject.component.DaggerApplicationComponent;
import com.alorma.apploteria.inject.module.ApplicationModule;

public class LoteriaApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    initInjectors();
  }

  private void initInjectors() {
    applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }
}
