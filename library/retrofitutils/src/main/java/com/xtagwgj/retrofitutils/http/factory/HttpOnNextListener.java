package com.xtagwgj.retrofitutils.http.factory;

/**
 * Created by xtagwgj on 2016/12/13.
 */

public interface HttpOnNextListener<T> {
    void onNext(T t);
    void onError(Throwable throwable);
}
