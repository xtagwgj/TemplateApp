package com.zyu.app.ui.user.model;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okrx.RxAdapter;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xtagwgj.common.base.BaseMvpModel;
import com.xtagwgj.common.security.Md5Security;
import com.zyu.app.base.ApiPath;
import com.zyu.app.domain.RequestResult;
import com.zyu.app.http.JsonCallback;
import com.zyu.app.http.rx.HttpResultFunc;
import com.zyu.app.http.rx.RxBaseModel;

import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * 用户类请求的api
 * Created by xtagwgj on 2016/12/15.
 */

public class UserModel extends RxBaseModel implements BaseMvpModel {


    public UserModel() {

    }

    /**
     * 用户登录
     */
    public <T> void login(RxAppCompatActivity activity,String account, String password, Subscriber<T> subscriber) {

        OkGo.post(ApiPath.callUrl(ApiPath.UserController.LOGIN))
//                .tag(this)
                .params("username", account)
                .params("password", getMd5Password(password))
                .getCall(new JsonCallback<RequestResult<T>>() {
                }, RxAdapter.<RequestResult<T>>create())

                .subscribeOn(Schedulers.io())
                .compose(activity.bindToLifecycle())
                .map(new HttpResultFunc<T>())
                .compose(this.<T>applySchedulers())

                .subscribe(subscriber);
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

    }

    /**
     * 密码加密
     */
    private String getMd5Password(String password) {
        String md5Password = Md5Security.getMD5(Md5Security.getMD5(password) + "lorent");

        Log.i("UserModel md5Pwd: ", md5Password);
        return md5Password;
    }

}
