package com.zyu.app.http.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * rx线程转换的模型
 * Created by xtagwgj on 2017/3/1.
 */

public class RxBaseModel {


    static final Observable.Transformer schedulersTransformer = observable -> ((Observable) observable)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread());

    protected static <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }

}
