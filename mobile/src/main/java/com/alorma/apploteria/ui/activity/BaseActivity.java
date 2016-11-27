package com.alorma.apploteria.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.alorma.apploteria.LoteriaApplication;
import com.alorma.apploteria.inject.component.ApplicationComponent;

public class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectComponents();
  }

  private void injectComponents() {
    LoteriaApplication application = (LoteriaApplication) getApplication();
    ApplicationComponent applicationComponent = application.getApplicationComponent();

    applicationComponent.inject(this);

    injectComponents(applicationComponent);
  }

  protected void injectComponents(ApplicationComponent applicationComponent) {

  }
}
