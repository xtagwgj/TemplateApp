package com.zyu.app.http.rx;

/**
 * Created by xtagwgj on 2016/12/13.
 */

public interface HttpOnNextListener<T> {
    void onNext(T t);
    void onError(Throwable throwable);
}
