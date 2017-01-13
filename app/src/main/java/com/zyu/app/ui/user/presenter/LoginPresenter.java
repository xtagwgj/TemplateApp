package com.zyu.app.ui.user.presenter;

import com.elvishew.xlog.XLog;
import com.zyu.app.domain.LoginInfoResponse;
import com.zyu.app.ui.user.contract.LoginContract;
import com.xtagwgj.common.commonutils.RegexUtils;
import com.xtagwgj.retrofitutils.http.factory.ProgressSubscriber;
import com.xtagwgj.retrofitutils.http.listener.HttpOnNextListener;

import java.util.List;

/**
 * 登录的处理
 * Created by xtagwgj on 2016/12/15.
 */

public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
        showLastLoginInfo();
    }

    @Override
    public void toLogin(String account, String password, boolean rememberPwd) {

        if (checkParam(account, password)) {
            mModel.login(account, password, new ProgressSubscriber<>(new HttpOnNextListener<List<LoginInfoResponse>>() {

                @Override
                public void onNext(List<LoginInfoResponse> loginInfoResponses) {
                    XLog.d("用户登录信息获取成功:" + loginInfoResponses.toString());
                    if (loginInfoResponses.size() > 0) {
                        saveUserInfo(loginInfoResponses.get(0));

                        if (rememberPwd)
                            dealSavePassword(password);

                        mView.loginSuccess();
                    } else {
                        XLog.e("未获取到用户的账号信息:" + loginInfoResponses.toString());
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    XLog.e("用户登录失败:" + throwable);
                    mView.loginFail("用户登录失败");
                }
            }, mContext));

        } else {
            XLog.e("登录前验证账号密码失败");
            mView.loginFail("账号或者密码错误");
        }

    }

    @Override
    public void showLastLoginInfo() {
        XLog.d("获取上次用户登录的信息");
        mView.displayAccountAndPassword("18566077938", "000000");
    }

    private boolean checkParam(String phone, String password) {
        XLog.d("检查手机号和密码是否正确");
        return RegexUtils.isMobileExact(phone) && password.length() >= 6;
    }

    private void saveUserInfo(LoginInfoResponse loginInfoResponse) {
        XLog.d("保存用户信息");

    }

    private void dealSavePassword(String password) {
        XLog.d("保存用户密码");

    }
}
