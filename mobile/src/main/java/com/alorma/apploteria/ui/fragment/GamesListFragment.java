package com.alorma.apploteria.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alorma.apploteria.R;
import com.alorma.apploteria.domain.ResourceCleanUp;
import com.alorma.apploteria.domain.bean.Game;
import com.alorma.apploteria.domain.bean.GamePart;
import com.alorma.apploteria.domain.bean.GamePlace;
import com.alorma.apploteria.inject.component.ApplicationComponent;
import com.alorma.apploteria.inject.component.DaggerGamesComponent;
import com.alorma.apploteria.inject.module.GamesModule;
import com.alorma.apploteria.ui.presenter.impl.GamesListPresenter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class GamesListFragment extends BaseFragment implements com.alorma.apploteria.ui.presenter.View<List<Game>> {

  @Inject ResourceCleanUp resourceCleanUp;
  @Inject GamesListPresenter gamesListPresenter;

  @BindView(R.id.text) TextView textView;

  @Override
  protected void injectComponents(ApplicationComponent applicationComponent) {
    super.injectComponents(applicationComponent);

    DaggerGamesComponent.builder().applicationComponent(applicationComponent)
        .gamesModule(new GamesModule()).build().inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    return inflater.inflate(R.layout.fragment_games_list, null, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
  }

  @Override
  public void onStart() {
    super.onStart();

    gamesListPresenter.attachView(this);
    gamesListPresenter.execute(null);
  }

  @Override
  public void onStop() {
    gamesListPresenter.detachView();
    super.onStop();
  }

  @OnClick(R.id.button)
  public void onButtonClick() {
    gamesListPresenter.addGame(generateRandomGame());
  }

  private Game generateRandomGame() {
    Game game = new Game();
    game.setColor(Color.CYAN);
    game.setNumber("89891");
    game.setParts(new ArrayList<>());
    GamePart gamePart = new GamePart();
    gamePart.setTitle("Loteria del curro");
    gamePart.setAmount(25.0);
    gamePart.setCurrency("€");

    GamePlace place = new GamePlace();
    place.setLatitude(41.2);
    place.setLongitude(2.1);
    place.setName("Curro");
    gamePart.setPlace(place);

    game.getParts().add(gamePart);
    return game;
  }

  @Override
  public void showLoading() {

  }

  @Override
  public void hideLoading() {

  }

  @Override
  public void onPause() {
    super.onPause();

    if (getActivity() != null && getActivity().isFinishing()) {
      resourceCleanUp.clean();
    }
  }

  @Override
  public void onDataReceived(List<Game> data) {
    if (data != null) {
      textView.setText("Data received: " + data.size());
    } else {
      textView.setText("Data received is null");
    }
  }

  @Override
  public void onDataEmpty() {
    textView.setText("NO DATA");
  }

  @Override
  public void showError(Throwable throwable) {

  }
}
