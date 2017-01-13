package com.xtagwgj.common.commonwidget;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * des:防止重复点击
 * Created by xsf
 * on 2016.05.9:29
 */

public abstract class OnNoDoubleClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 1500;
//    private long lastClickTime = 0;

    @Override
    public void onClick(final View v) {
//        long currentTime = Calendar.getInstance().getTimeInMillis();
//        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
//            lastClickTime = currentTime;
//            onNoDoubleClick(v);
//        }

        RxView.clicks(v)
                .throttleFirst(MIN_CLICK_DELAY_TIME, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        onNoDoubleClick(v);
                    }
                });

    }

    protected abstract void onNoDoubleClick(View v);

}