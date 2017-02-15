package com.zyu.app.base;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.bugly.Bugly;
import com.xtagwgj.common.loadinglayout.LoadingLayout;
import com.xtagwgj.retrofitutils.http.ApiRequest;
import com.zyu.app.BuildConfig;
import com.zyu.app.R;

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
        ApiRequest.instance
                .initRetrofit(ApiPath.getBaseUrl())
//                .setCertificates(certificatesStream)
                .showLog(BuildConfig.DEBUG)
//                .setPublicMap()
                .build();
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
