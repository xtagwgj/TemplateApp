package com.zyu.app.base;

import android.os.Environment;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.formatter.message.json.DefaultJsonFormatter;
import com.elvishew.xlog.formatter.message.throwable.DefaultThrowableFormatter;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.FileSizeBackupStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.xtagwgj.common.BaseApplication;
import com.zyu.app.BuildConfig;

import java.io.File;

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

        initLogger();
        InitializeService.start(this);
    }


    private void initLogger() {

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
