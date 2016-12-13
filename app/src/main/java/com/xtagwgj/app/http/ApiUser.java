package com.xtagwgj.app.http;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xtagwgj.app.model.LoginInfoResponse;
import com.xtagwgj.app.model.RequestResult;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;

/**
 * 用户请求的示例
 * Created by xtagwgj on 16/9/25.
 */

public class ApiUser extends BaseDealApi {

    private ApiStore apiStore;

    public ApiUser() {
        apiStore = create(ApiStore.class);
    }

    public void login(RxAppCompatActivity context, String username, String password,
                      Subscriber<List<LoginInfoResponse>> subscriber) {
        if (null == apiStore)
            throw new RuntimeException("apiStore is null");

        requestBindCycle(context, apiStore.login(username, password, 0, "0"), subscriber);
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
