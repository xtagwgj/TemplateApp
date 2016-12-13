package com.xtagwgj.common.rxutils;

import android.support.annotation.NonNull;

import rx.Scheduler;

/**
 * Created by xtagwgj on 2016/12/11.
 */

public interface BaseSchedulerProvider {
    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

}
