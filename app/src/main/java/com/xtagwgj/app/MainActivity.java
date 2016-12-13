package com.xtagwgj.app;

import android.util.Log;
import android.view.KeyEvent;

import com.jakewharton.rxbinding.view.RxView;
import com.xtagwgj.app.demo.DemoActivity;
import com.xtagwgj.common.base.BaseActivity;
import com.xtagwgj.common.commonutils.ToastUtils;
import com.xtagwgj.common.loadinglayout.LoadingLayout;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    //最后一次点击后退的时间
    private long exitTime=0l;
    private LoadingLayout loadingLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
//        loadingLayout.setStatus(LoadingLayout.Loading);
//        loadingLayout.setStatus(LoadingLayout.No_Network);

//        Observable
//                .just("Loading", "Empty", "Error", "NoNetWork", "Success")
//                .delay(3, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s -> {
//                    Log.e("Tag", s);
//                    switch (s) {
//                        case "Loading":
//                            loadingLayout.setStatus(LoadingLayout.Loading);//加载中
//                            break;
//                        case "Empty":
//                            loadingLayout.setStatus(LoadingLayout.Empty);//加载中
//                            break;
//                        case "Error":
//                            loadingLayout.setStatus(LoadingLayout.Error);//加载中
//                            break;
//                        case "NoNetWork":
//                            loadingLayout.setStatus(LoadingLayout.No_Network);//加载中
//                            break;
//                        default:
////                                loadingLayout.setStatus(LoadingLayout.Success);//加载中
//                            break;
//                    }
//                });
        loadingLayout.setStatus(LoadingLayout.Success);
    }

    @Override
    public void initEventListener() {
        RxView.clicks(findViewById(R.id.btn_toDemo))
                .throttleFirst(1,TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    startActivity(DemoActivity.class);
                });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return isConsumeBackKey();
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isConsumeBackKey() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {//未处理监听事件，请求后续监听
            ToastUtils.showShortToastSafe(this,"再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }

        return true;
    }
}
