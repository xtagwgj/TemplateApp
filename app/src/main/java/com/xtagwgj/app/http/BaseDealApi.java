package com.xtagwgj.app.http;

import com.trello.rxlifecycle.components.RxFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xtagwgj.app.model.RequestResult;
import com.xtagwgj.retrofitutils.http.api.DealBaseApi;
import com.xtagwgj.retrofitutils.http.factory.RetryWhenNetworkException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by xtagwgj on 2016/12/13.
 */

public class BaseDealApi extends DealBaseApi {
    <T> void requestBindCycle(RxAppCompatActivity context,
                              Observable<RequestResult<T>> observable,
                              Subscriber<T> subscriber) {
        observable.retryWhen(new RetryWhenNetworkException())
                .compose(context.bindToLifecycle())
                .map(new HttpResultFunc<T>())
                .compose(this.<T>applySchedulers())
                .subscribe(subscriber);

    }

    <T> void requestBindCycle(RxFragment context,
                              Observable<RequestResult<T>> observable,
                              Subscriber<T> subscriber) {
        observable.retryWhen(new RetryWhenNetworkException())
                .compose(context.bindToLifecycle())
                .map(new HttpResultFunc<T>())
                .compose(this.<T>applySchedulers())
                .subscribe(subscriber);

    }

    <T> void request(Observable<RequestResult<T>> observable,
                     Subscriber<T> subscriber) {
        observable.retryWhen(new RetryWhenNetworkException())
                .map(new HttpResultFunc<T>())
                .compose(this.<T>applySchedulers())
                .subscribe(subscriber);

    }
}
