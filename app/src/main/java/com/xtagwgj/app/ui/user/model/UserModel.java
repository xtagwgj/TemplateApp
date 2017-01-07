package com.xtagwgj.app.ui.user.model;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xtagwgj.app.domain.LoginInfoResponse;
import com.xtagwgj.app.http.ApiStore;
import com.xtagwgj.app.http.BaseDealApi;

import java.util.List;

import rx.Subscriber;

/**
 * 用户类请求的api
 * Created by xtagwgj on 2016/12/15.
 */

public class UserModel extends BaseDealApi {

    private ApiStore.UserController apiStore;

    public UserModel() {
        apiStore = create(ApiStore.UserController.class);
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
     * @param activity   绑定周期的activity
     * @param phone      手机号
     * @param type       短信验证码类型
     * @param subscriber 订阅的验证码处理
     */

    public void getVerifyCode(RxAppCompatActivity activity, String phone, int type, Subscriber<List<String>> subscriber) {
        requestBindCycle(activity, apiStore.getVerifyCode(phone, type), subscriber);
    }


}
