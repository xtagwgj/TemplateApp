package com.zyu.app.base;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.tencent.bugly.Bugly;
import com.xtagwgj.common.loadinglayout.LoadingLayout;
import com.zyu.app.BuildConfig;
import com.zyu.app.R;

import java.util.logging.Level;

import cn.jpush.android.api.JPushInterface;

/**
 * 初始化服务
 * Created by xtagwgj on 2017/2/6.
 */
public class InitializeService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.xtagwgj.service.action.INIT";

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit() {
        initNet();
        initPush();
        initLoadingLayout();
        initLog();
    }

    private void initLog() {
        //初始化bugly日志
        Bugly.init(getApplicationContext(), Constant.CrashAppId, BuildConfig.DEBUG);
    }

    private void initPush() {
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
        Log.e("[PUSH]", "pushKey=" + JPushInterface.getUdid(this) +
                ";RegistrationId=" + JPushInterface.getRegistrationID(this));
    }

    private void initNet() {
        OkGo.init(getApplication());

        OkGo.getInstance()
                .debug("POS", Level.INFO, BuildConfig.DEBUG)

                //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                .setCacheMode(CacheMode.NO_CACHE)
                //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                .setRetryCount(3)

                //使用预埋证书，校验服务端证书（自签名证书）
                //.setCertificates(getAssets().open("srca.cer"))

                //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(new PersistentCookieStore());
    }


    private void initLoadingLayout() {
        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setErrorImage(R.mipmap.define_error)
                .setEmptyImage(R.mipmap.define_empty)
                .setNoNetworkImage(R.mipmap.define_nonetwork)
                .setAllTipTextColor(R.color.gray)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.gray)
                .setReloadButtonWidthAndHeight(150, 40);
    }
}
