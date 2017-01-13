package com.zyu.app.ui.main;

import android.os.Bundle;
import android.view.WindowManager;

import com.zyu.app.R;
import com.zyu.app.base.Constant;
import com.zyu.app.ui.user.LoginActivity;
import com.xtagwgj.common.base.BaseActivity;
import com.xtagwgj.common.commonutils.AppUtils;
import com.xtagwgj.common.commonutils.SPUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

public class SplashActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (SPUtils.getInstance().getInt("versionCode", 1) < AppUtils.getAppVersionCode(this)) {
            //当前版本大于前一个版本，去往引导页面
            GuideActivity.startAction(this);

        } else {
            Observable
                    .create((Subscriber<? super Void> subscriber) -> {
                        subscriber.onNext(null);
                    })
                    .delay(Constant.SPLASH_TIME, TimeUnit.SECONDS)
                    .subscribe(aVoid -> {
                        LoginActivity.startAction(SplashActivity.this);
//                        MainActivity.startAction(SplashActivity.this);
                    });
        }
    }

    @Override
    public void initEventListener() {

    }
}
