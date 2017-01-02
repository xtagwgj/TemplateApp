package com.xtagwgj.retrofitutils.http.interceptor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.xtagwgj.common.BaseApplication;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 缓存机制
 * 无网络时，也能显示数据
 * Created by xtagwgj on 2017/1/1.
 */

public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if (!isNetworkConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (isNetworkConnected()) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置(注掉部分)
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    //.header("Cache-Control", "max-age=3600")
                    .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        } else {
            int maxAge = 60 * 60;
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) BaseApplication.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

}
