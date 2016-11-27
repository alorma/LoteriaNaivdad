package com.alorma.apploteria.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.alorma.apploteria.LoteriaApplication;
import com.alorma.apploteria.inject.component.ApplicationComponent;

public class BaseFragment extends Fragment {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    injectComponents();
  }
  private void injectComponents() {
    LoteriaApplication application = (LoteriaApplication) getContext().getApplicationContext();
    ApplicationComponent applicationComponent = application.getApplicationComponent();

    applicationComponent.inject(this);

    injectComponents(applicationComponent);
  }

  protected void injectComponents(ApplicationComponent applicationComponent) {

  }
}
