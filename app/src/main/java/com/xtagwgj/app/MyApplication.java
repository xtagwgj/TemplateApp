package com.xtagwgj.app;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.tencent.bugly.crashreport.CrashReport;
import com.xtagwgj.app.base.Constant;
import com.xtagwgj.common.BaseApplication;
import com.xtagwgj.common.loadinglayout.LoadingLayout;
import com.xtagwgj.retrofitutils.http.ApiRequest;

/**
 * Created by xtagwgj on 2016/12/10.
 */

public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        initNet();
        initLoadingLayout();
        initLogger();
    }

    private void initNet() {
        /**
         *
         */
        ApiRequest.instance.initRetrofit("http://iyuns.ylxmall.com:8080/property/", true);
    }

    private void initLogger() {
        //初始化bugly日志
        CrashReport.initCrashReport(getApplicationContext(), Constant.CrashAppId, BuildConfig.DEBUG);

        //初始化其他日志
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE)
                .b()
                .build();

        XLog.init(config);
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
