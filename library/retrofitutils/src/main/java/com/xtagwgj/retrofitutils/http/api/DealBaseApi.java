package com.xtagwgj.retrofitutils.http.api;


import com.xtagwgj.retrofitutils.http.ApiRequest;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xtagwgj on 16/9/25.
 */

public class DealBaseApi {

    public <T> T create(Class<T> cls) {
        return ApiRequest.instance.create(cls);
    }

   static final Observable.Transformer schedulersTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    protected static <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }


    protected static <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


}
