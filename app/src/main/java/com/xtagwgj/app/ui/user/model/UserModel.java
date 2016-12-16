package com.xtagwgj.app.ui.user.model;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xtagwgj.app.http.BaseDealApi;
import com.xtagwgj.app.model.LoginInfoResponse;
import com.xtagwgj.app.model.RequestResult;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;

/**
 * 用户类请求的api
 * Created by xtagwgj on 2016/12/15.
 */

public class UserModel extends BaseDealApi {

    interface ApiStore {
        @POST("authLogin.do")
        Observable<RequestResult<List<LoginInfoResponse>>> login(@Query("username") String username,
                                                                 @Query("password") String password,
                                                                 @Query("deviceType") int deviceType,
                                                                 @Query("deviceToken") String deviceToken);

        @POST("authSmsCode.do")
        Observable<RequestResult<List<String>>> getVerifyCode(@Query("phoneNumber") String phone, @Query("type") int type);


    }

    private ApiStore apiStore;

    public UserModel() {
        apiStore = create(ApiStore.class);
    }

    /**
     * 用户登录
     */
    public void login(String account, String password, Subscriber<List<LoginInfoResponse>> subscriber) {
        request(apiStore.login(account, password, 0, "0"), subscriber);
    }

    /**
     * 获取验证码
     *
     * @param activity
     * @param phone      手机号
     * @param type       短信验证码类型
     * @param subscriber
     */

    public void getVerifyCode(RxAppCompatActivity activity, String phone, int type, Subscriber<List<String>> subscriber) {
        requestBindCycle(activity, apiStore.getVerifyCode(phone, type), subscriber);
    }


}
