package com.xtagwgj.app;

import android.os.Environment;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.formatter.message.json.DefaultJsonFormatter;
import com.elvishew.xlog.formatter.message.throwable.DefaultThrowableFormatter;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.FileSizeBackupStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.tencent.bugly.Bugly;
import com.xtagwgj.app.base.Constant;
import com.xtagwgj.common.BaseApplication;
import com.xtagwgj.common.loadinglayout.LoadingLayout;
import com.xtagwgj.retrofitutils.http.ApiRequest;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

/**
 * 应用
 * Created by xtagwgj on 2016/12/10.
 */

public class MyApplication extends BaseApplication {

    private static MyApplication baseApplication;

    public static MyApplication getAppContext() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;

        initNet();
        initPush();
        initLoadingLayout();
        initLogger();
    }

    private void initPush() {
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
    }

    private void initNet() {
        ApiRequest.instance
                .initRetrofit("https://iyuns.ylxmall.com/property/")
//                .setCertificates(certificatesStream)
                .showLog(BuildConfig.DEBUG)
                .build();
    }

    private void initLogger() {
        //初始化bugly日志
        Bugly.init(getApplicationContext(), Constant.CrashAppId, BuildConfig.DEBUG);

        //初始化其他日志
        if (BuildConfig.DEBUG) {

            XLog.init(new LogConfiguration.Builder()
                    .logLevel(LogLevel.ALL)
                    .b()
                    .build());
        } else

            XLog.init(
                    LogLevel.ALL,
                    new LogConfiguration                                             // 如果没有指定 LogConfiguration，会默认使用 new LogConfiguration.Builder().build()
                            .Builder()                                               // 打印日志时会用到的配置
                            .tag("MY_TAG")                                           // 默认: "XLOG"
                            .jsonFormatter(new DefaultJsonFormatter())               // 默认: DefaultJsonFormatter
                            .throwableFormatter(new DefaultThrowableFormatter())     // 默认: DefaultThrowableFormatter
                            .b()
                            .build(),
                    new FilePrinter                                                  // 打印日志到文件。如果没有指定，则不会使用
                            .Builder(getLogFile().getPath())                         // 保存日志文件的路径
                            .fileNameGenerator(new DateFileNameGenerator())          // 默认: ChangelessFileNameGenerator("log")
                            .backupStrategy(new FileSizeBackupStrategy(1024 * 1024)) // 默认: FileSizeBackupStrategy(1024 * 1024)
                            .build());
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

    /**
     * android 6.0以上要注意动态申请权限，否则不回打印日志到文件（日志文件保存在download的文件夹中）
     * 在GuideActivity中申请权限
     *
     * @return
     */
    public File getLogFile() {
        File rootFile =
                Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED) ?
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) :
                        Environment.getRootDirectory();

        File cacheDir = new File(rootFile, "pos");

        if (!cacheDir.exists())
            cacheDir.mkdir();

        return cacheDir;
    }
}
