package com.xtagwgj.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xtagwgj.common.loadinglayout.LoadingLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private LoadingLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        loadingLayout.setStatus(LoadingLayout.Loading);
        loadingLayout.setStatus(LoadingLayout.No_Network);

        Observable
                .just("Loading", "Empty", "Error", "NoNetWork", "Success")
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("Tag",s);
                        switch (s) {
                            case "Loading":
                                loadingLayout.setStatus(LoadingLayout.Loading);//加载中
                                break;
                            case "Empty":
                                loadingLayout.setStatus(LoadingLayout.Empty);//加载中
                                break;
                            case "Error":
                                loadingLayout.setStatus(LoadingLayout.Error);//加载中
                                break;
                            case "NoNetWork":
                                loadingLayout.setStatus(LoadingLayout.No_Network);//加载中
                                break;
                            default:
//                                loadingLayout.setStatus(LoadingLayout.Success);//加载中
                                break;
                        }
                    }
                });

    }
}
