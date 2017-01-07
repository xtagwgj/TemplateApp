package com.xtagwgj.common.commonwidget;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * des:双击事件
 * Created by xsf
 * on 2016.07.10:58
 */
public abstract class OnDoubleClickListener implements View.OnClickListener {
//    private int count = 0;
//    private long firClick = 0;
//    private long secClick = 0;

    @Override
    public void onClick(final View v) {
//        count++;
//        if (count == 1) {
//            firClick = System.currentTimeMillis();
//
//        } else if (count == 2) {
//            secClick = System.currentTimeMillis();
//            if (secClick - firClick < 1000) {
//                //双击事件
//                onDoubleClick(v);
//            }
//            count = 0;
//            firClick = 0;
//            secClick = 0;
//        }

        RxView.clicks(v)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        onDoubleClick(v);
                    }
                });
    }

    protected abstract void onDoubleClick(View v);
}