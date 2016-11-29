package com.alorma.apploteria.ui.presenter;

import com.alorma.apploteria.domain.usecase.SingleUseCase;
import com.alorma.apploteria.domain.usecase.UseCase;
import com.alorma.apploteria.inject.named.MainScheduler;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Represents base RxJava presenter.
 * Just only one Observable could be subscribed to it.
 */
public abstract class BaseRxPresenter<REQUEST, RESPONSE, VIEW extends View<RESPONSE>> extends BasePresenter<VIEW> {

  protected final Scheduler mainScheduler;
  protected final Scheduler ioScheduler;
  private UseCase<REQUEST, RESPONSE> observableUseCase;
  private SingleUseCase<RESPONSE> singleUseCase;
  protected CompositeSubscription subscription;
  private RESPONSE defaultIfEmpty;

  public BaseRxPresenter(@MainScheduler Scheduler mainScheduler, Scheduler ioScheduler, UseCase<REQUEST, RESPONSE> observableUseCase) {
    this.mainScheduler = mainScheduler;
    this.ioScheduler = ioScheduler;
    this.observableUseCase = observableUseCase;
  }

  public BaseRxPresenter(@MainScheduler Scheduler mainScheduler, Scheduler ioScheduler, SingleUseCase<RESPONSE> singleUseCase) {
    this.mainScheduler = mainScheduler;
    this.ioScheduler = ioScheduler;
    this.singleUseCase = singleUseCase;
  }

  /**
   * Executes request doesn't assign page to request.
   *
   * @param request item to execute
   */
  public void execute(REQUEST request) {
    if (!isViewAttached()) return;

    if (observableUseCase != null) {
      subscribe(observableUseCase.execute(request));
    } else if (singleUseCase != null) {
      subscribe(singleUseCase.execute());
    }
  }

  /**
   * Creates internal subscription and attaches it to observable argument.
   *
   * @param observable the object to subscribe
   */
  protected void subscribe(Observable<RESPONSE> observable) {
    if (!isViewAttached()) return;

    getView().showLoading();

    unsubscribe();

    Subscriber<RESPONSE> subscriber = new Subscriber<RESPONSE>() {
      @Override
      public void onCompleted() {
        BaseRxPresenter.this.onCompleted();
      }

      @Override
      public void onError(Throwable e) {
        BaseRxPresenter.this.onError(e);
      }

      @Override
      public void onNext(RESPONSE response) {
        BaseRxPresenter.this.onNext(response);
      }
    };

    observable.subscribeOn(ioScheduler)
        .observeOn(mainScheduler)
        .switchIfEmpty(defaultIfEmpty != null ? Observable.just(defaultIfEmpty) : Observable.empty())
        .timeout(20, TimeUnit.SECONDS)
        .subscribe(subscriber);

    getSubscription().add(subscriber);
  }

  private CompositeSubscription getSubscription() {
    if (subscription == null) {
      subscription = new CompositeSubscription();
    }
    return subscription;
  }

  /**
   * Creates internal subscription and attaches it to single argument.
   *
   * @param single the object to subscribe
   */
  protected void subscribe(Single<RESPONSE> single) {
    if (!isViewAttached()) return;

    getView().showLoading();

    unsubscribe();

    Subscriber<RESPONSE> subscriber = new Subscriber<RESPONSE>() {
      @Override
      public void onCompleted() {
        BaseRxPresenter.this.onCompleted();
      }

      @Override
      public void onError(Throwable e) {
        BaseRxPresenter.this.onError(e);
      }

      @Override
      public void onNext(RESPONSE response) {
        BaseRxPresenter.this.onNext(response);
      }
    };

    single.toObservable()
        .switchIfEmpty(defaultIfEmpty != null ? Observable.just(defaultIfEmpty) : Observable.empty())
        .onErrorResumeNext(throwable -> {
          if (throwable instanceof NoSuchElementException) {
            return defaultIfEmpty != null ? Observable.just(defaultIfEmpty) : Observable.empty();
          }
          return Observable.error(throwable);
        })
        .subscribeOn(ioScheduler)
        .observeOn(mainScheduler)
        .timeout(20, TimeUnit.SECONDS)
        .subscribe(subscriber);

    getSubscription().add(subscriber);
  }

  /**
   * Unsubscribes internal subscription and set it to null.
   */
  protected void unsubscribe() {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.clear();
    }
  }

  protected void onCompleted() {
    if (isViewAttached()) {
      getView().hideLoading();
    }
    unsubscribe();
  }

  protected void onError(Throwable throwable) {
    if (isViewAttached()) {
      getView().showError(throwable);
      getView().hideLoading();
      throwable.printStackTrace();
    }
    unsubscribe();
  }

  protected void onNext(RESPONSE response) {
    if (isViewAttached()) {
      if (responseIsEmpty(response)) {
        getView().onDataEmpty();
      } else {
        getView().onDataReceived(response);
      }
    }
  }

  protected boolean responseIsEmpty(RESPONSE response) {
    return false;
  }

  @Override
  public void detachView() {
    super.detachView();
    if (isViewAttached()) {
      getView().hideLoading();
    }
    unsubscribe();
  }

  public void setDefaultIfEmpty(RESPONSE defaultIfEmpty) {
    this.defaultIfEmpty = defaultIfEmpty;
  }
}