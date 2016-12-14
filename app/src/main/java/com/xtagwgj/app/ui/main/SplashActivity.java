package com.xtagwgj.app.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xtagwgj.app.R;
import com.xtagwgj.app.base.Constant;
import com.xtagwgj.common.commonutils.AppUtils;
import com.xtagwgj.common.commonutils.SPUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SPUtils.getSharedIntData(this, "versionCode") < AppUtils.getLocalVersion(this)) {
            //当前版本大于前一个版本，去往引导页面
            GuideActivity.startAction(this);

        } else {
            Observable
                    .create((Subscriber<? super Void> subscriber) -> {
                        subscriber.onNext(null);
                    })
                    .delay(Constant.SPLASH_TIME, TimeUnit.SECONDS)
                    .subscribe(aVoid -> {
                        MainActivity.startAction(SplashActivity.this);
                    });
        }

    }

}
