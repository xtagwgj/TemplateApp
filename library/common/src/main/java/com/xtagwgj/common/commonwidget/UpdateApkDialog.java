package com.xtagwgj.common.commonwidget;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.xtagwgj.common.R;
import com.xtagwgj.common.commonutils.StringUtils;
import com.xtagwgj.common.commonutils.ToastUtils;

import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import zlc.season.rxdownload.RxDownload;
import zlc.season.rxdownload.entity.DownloadEvent;
import zlc.season.rxdownload.entity.DownloadFlag;
import zlc.season.rxdownload.entity.DownloadStatus;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * 下载apk文件的dialog
 * Created by xtagwgj on 2017/1/8.
 */

public class UpdateApkDialog extends AppCompatDialogFragment {

    //最大的尝试次数
    private final int MAX_RETRY_TIMES = 2;

    TextView tv_updateInfo, tv_cancel, tv_update, tv_title;
    //进度条
    ProgressBar progressBar;

    private RxDownload mRxDownload;
    //下载状态的
    private Subscription downloadStatusSubscription;

    //下载地址、apk名称、更新信息
    private String downUrl, apkName, appInfo;
    //保存文件的地址
    private String savePath = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath();
    //是否强制更新
    private boolean isForceUpdate = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_download_apk, null);

        tv_updateInfo = (TextView) view.findViewById(R.id.tv_updateInfo);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_update = (TextView) view.findViewById(R.id.tv_update);
        tv_title = (TextView) view.findViewById(R.id.tv_title);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressBar.setIndeterminate(true);

        //取消更新
        RxView.clicks(tv_cancel)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        dismiss();
                    }
                });

        //下载
        RxView.clicks(tv_update)
                .throttleFirst(2, TimeUnit.SECONDS)
                .filter(new Func1<Void, Boolean>() {
                    @Override
                    public Boolean call(Void aVoid) {
                        return !StringUtils.isEmpty(downUrl) && !StringUtils.isEmpty(apkName);
                    }
                })
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //初始化RxDownload，避免多次创建RxDownload实例
                        if (mRxDownload == null)
                            mRxDownload = RxDownload.getInstance()
                                    .context(getActivity())
                                    .autoInstall(true)
                                    .maxDownloadNumber(1)
                                    .maxRetryCount(MAX_RETRY_TIMES);

                        progressBar.setVisibility(View.VISIBLE);
                        downLoadApkFile();
                    }
                });

        //不能给点击外部取消，只能点击按钮取消
        setCancelable(false);

        //设置要显示的文字
        tv_title.setText(getString(R.string.dialog_title));
        tv_cancel.setVisibility(isForceUpdate ? View.GONE : View.VISIBLE);
        if (StringUtils.isEmpty(appInfo))
            tv_updateInfo.setText(getString(R.string.error_empty_updateinfo));
        else
            tv_updateInfo.setText(appInfo);

        return view;
    }

    /**
     * 设置是否强制更新
     *
     * @param forceUpdate 是否强制更新
     * @return
     */
    public UpdateApkDialog setForceUpdate(boolean forceUpdate) {
        isForceUpdate = forceUpdate;
        return this;
    }

    /**
     * 下载的具体信息
     *
     * @param downUrl 下载地址
     * @param apkName apk名称
     * @param appInfo app更新信息
     * @return
     */
    public UpdateApkDialog setDownLoadInfo(String downUrl, String apkName, String appInfo) {
        this.downUrl = downUrl;
        this.apkName = apkName;
        this.appInfo = appInfo;

        return this;
    }

    /**
     * 下载apk文件
     */
    private void downLoadApkFile() {
        //文件下载的监听
        onDownloadStatusListener(downUrl);
        //申请权限下载文件
        RxPermissions.getInstance(getActivity())
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .doOnNext(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (!granted) {
                            throw new RuntimeException(getString(R.string.error_permission_write_external));
                        }
                    }
                }).compose(mRxDownload.transformService(downUrl, apkName, savePath))
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.d("d", "下载开始");
                        ToastUtils.showShortToastSafe(getActivity(), "下载开始");
                    }
                });
    }

    /**
     * 文件下载的监听
     *
     * @param downLoadUrl 文件下载的地址
     */
    private void onDownloadStatusListener(final String downLoadUrl) {
        downloadStatusSubscription = mRxDownload.receiveDownloadStatus(downLoadUrl)
                .subscribe(new Action1<DownloadEvent>() {
                    @Override
                    public void call(DownloadEvent event) {
                        if (event.getFlag() == DownloadFlag.FAILED) {
                            Throwable throwable = event.getError();
                            Log.w("下载安装文件出错：", throwable);
                        } else if (event.getFlag() == DownloadFlag.COMPLETED) {
                            dismiss();
                        } else if (event.getFlag() == DownloadFlag.INSTALLED) {
                            Log.d("", "安装完成后删除apk文件");
                            mRxDownload.deleteServiceDownload(downLoadUrl).subscribe();

                            //取消订阅
                            if (downloadStatusSubscription != null && !downloadStatusSubscription.isUnsubscribed()) {
                                downloadStatusSubscription.unsubscribe();
                            }
                        }
                        updateProgress(event);
                    }
                });
    }

    /**
     * 更新进度条的显示
     *
     * @param event
     */
    private void updateProgress(DownloadEvent event) {
        DownloadStatus status = event.getDownloadStatus();
        if (progressBar != null) {
            progressBar.setIndeterminate(status.isChunked);
            progressBar.setMax((int) status.getTotalSize());
            progressBar.setProgress((int) status.getDownloadSize());
//            if (mPercent != null)
//                mPercent.setText(status.getPercent());
//            if (mSize != null)
//                mSize.setText(status.getFormatStatusString());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
