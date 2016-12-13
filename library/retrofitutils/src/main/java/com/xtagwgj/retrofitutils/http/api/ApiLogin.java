package com.xtagwgj.retrofitutils.http.api;


import com.xtagwgj.retrofitutils.http.HttpResultFunc;
import com.xtagwgj.retrofitutils.http.response.LoginInfoResponse;
import com.xtagwgj.retrofitutils.http.response.RequestResult;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;

/**
 * 登陆的示例
 * Created by xtagwgj on 16/9/25.
 */

public class ApiLogin extends BaseApi {

    private ApiStore apiStore;

    public ApiLogin() {
        apiStore = create(ApiStore.class);
    }

    public void login(String username, String password, Subscriber<List<LoginInfoResponse>> subscriber) {
        if (null == apiStore)
            throw new RuntimeException("apiStore is null");

//        Observable map = apiStore.login(username, password, 0, "0")
//                .map(new HttpResultFunc<List<LoginInfoResponse>>());
//
//        toSubscribe(map, subscriber);
//
        apiStore.login(username, password, 0, "0")
                .map(new HttpResultFunc<List<LoginInfoResponse>>())
                .compose(this.<List<LoginInfoResponse>>applySchedulers())
                .subscribe(subscriber);
    }

    private interface ApiStore {
        @POST("authLogin.do")
        Observable<RequestResult<List<LoginInfoResponse>>> login(
                @Query("username") String username,
                @Query("password") String password,
                @Query("deviceType") int deviceType,
                @Query("deviceToken") String deviceToken);
    }
}
