package com.xtagwgj.app.http;

import com.trello.rxlifecycle.components.RxFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xtagwgj.app.domain.RequestResult;
import com.xtagwgj.common.base.BaseMvpModel;
import com.xtagwgj.retrofitutils.http.api.DealBaseApi;
import com.xtagwgj.retrofitutils.http.exception.RetryWhenNetworkException;

import rx.Observable;
import rx.Subscriber;

/**
 * 请求处理的父类，添加失败重试和自动获取需要的数据
 * Created by xtagwgj on 2016/12/13.
 */

public class BaseDealApi extends DealBaseApi implements BaseMvpModel{
    /**
     * 在activity的生命周期内处理返回的数据
     */
    public <T> void requestBindCycle(RxAppCompatActivity context,
                                     Observable<RequestResult<T>> observable,
                                     Subscriber<T> subscriber) {
        observable.retryWhen(new RetryWhenNetworkException())
                .compose(context.bindToLifecycle())
                .map(new HttpResultFunc<T>())
                .compose(this.<T>applySchedulers())
                .subscribe(subscriber);

    }

    /**
     * 在fragment的生命周期内请求数据
     */
    public <T> void requestBindCycle(RxFragment context,
                                     Observable<RequestResult<T>> observable,
                                     Subscriber<T> subscriber) {
        observable.retryWhen(new RetryWhenNetworkException())
                .compose(context.bindToLifecycle())
                .map(new HttpResultFunc<T>())
                .compose(this.<T>applySchedulers())
                .subscribe(subscriber);

    }

    /**
     * 在fragment的生命周期内请求数据
     */
    public <T> void requestBindCycle(com.trello.rxlifecycle.components.support.RxFragment context,
                                     Observable<RequestResult<T>> observable,
                                     Subscriber<T> subscriber) {
        observable.retryWhen(new RetryWhenNetworkException())
                .compose(context.bindToLifecycle())
                .map(new HttpResultFunc<T>())
                .compose(this.<T>applySchedulers())
                .subscribe(subscriber);

    }

    /**
     * 请求数据，忽略生命周期
     */
    public <T> void request(Observable<RequestResult<T>> observable,
                            Subscriber<T> subscriber) {
        observable.retryWhen(new RetryWhenNetworkException())
                .map(new HttpResultFunc<T>())
                .compose(this.<T>applySchedulers())
                .subscribe(subscriber);

    }
}
