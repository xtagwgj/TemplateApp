package com.xtagwgj.retrofitutils.http;


import com.xtagwgj.common.BaseApplication;
import com.xtagwgj.common.commonutils.NetWorkUtils;
import com.xtagwgj.retrofitutils.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求
 * Created by xtagwgj on 16/9/25.
 */

public enum ApiRequest {
    instance;

    private Retrofit retrofit;
    private static final String BASE_URL = "http:/183.61.80.249:8080/property/";

    private static final int DEFAULT_TIMEOUT = 5;

    ApiRequest() {

        OkHttpClient client = createOkHttpBuilder().build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
    }

    /**
     * 创建OkHttp的客户端 如果存在用户并进行身份验证
     *
     * @return builder
     */
    private OkHttpClient.Builder createOkHttpBuilder() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//        httpClientBuilder.addInterceptor(new com.xtagwgj.optimal.http.factory.OkHttpInterceptor().Bu)
        /**
         * 超时重连
         */
        httpClientBuilder.retryOnConnectionFailure(true);

        /**
         * 设置缓存
         */
        File cacheFile = new File(BaseApplication.getAppContext().getExternalCacheDir(), "cacheFile");

        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        httpClientBuilder.cache(cache).addInterceptor(getCacheInterceptor());

//        httpClientBuilder.addInterceptor(getPublicParameter());
//        httpClientBuilder.addInterceptor(getHeaderParameter());

        if (BuildConfig.DEBUG) {
            /**
             * 设置日志
             */
            httpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            // httpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
        }

        return httpClientBuilder;
    }

    /**
     * 缓存机制
     * 无网络时，也能显示数据
     *
     * @return 缓存拦截器
     */
    private Interceptor getCacheInterceptor() {

        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("JavaDemo")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("nyn")
                            .build();
                }
                return response;
            }
        };
        return cacheInterceptor;
    }

    /**
     * 公共参数
     *
     * @return
     */
    private Interceptor getPublicParameter() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                String method = originalRequest.method();
                Headers headers = originalRequest.headers();
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
                        .addQueryParameter("platform", "android")
                        .addQueryParameter("version", "1.0.0")
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    /**
     * 头部参数
     *
     * @return
     */
    private Interceptor getHeaderParameter() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .header("AppType", "TPOS")
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }

    /**
     * create api instance
     *
     * @param service api class
     * @return
     */
    public <T> T create(Class<T> service) {
        if (retrofit == null)
            throw new NullPointerException("retrofit is null");
        return retrofit.create(service);
    }

}
