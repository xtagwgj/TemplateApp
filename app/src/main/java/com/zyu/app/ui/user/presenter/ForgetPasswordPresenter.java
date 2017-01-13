package com.zyu.app.ui.user.presenter;

import com.elvishew.xlog.XLog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zyu.app.R;
import com.zyu.app.ui.user.contract.ForgetPasswordContract;
import com.xtagwgj.common.commonutils.RegexUtils;
import com.xtagwgj.common.commonutils.StringUtils;

import java.util.List;

import rx.Subscriber;


/**
 * 忘记密码
 * Created by xtagwgj on 2016/12/15.
 */

public class ForgetPasswordPresenter extends ForgetPasswordContract.Presenter {

    @Override
    public void getVerifyCode(String phone) {
        if (!checkPhone(phone)) {
            mView.sendVerifyCodeFail(mContext.getString(R.string.error_phone));
            return;
        }

        XLog.d("获取验证码");
        //发送验证码

        mModel.getVerifyCode((RxAppCompatActivity) mContext, phone, 0, new Subscriber<List<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.sendVerifyCodeFail(e != null ? e.getMessage() : "获取验证码失败");
            }

            @Override
            public void onNext(List<String> strings) {
                mView.sendVerifyCodeSuccess();
            }
        });

    }

    @Override
    public void resetPassword(String phone, String verifyCode, String newPassword1, String newPassword2) {

        if (!checkPhone(phone)) {
            mView.resetPasswordFail(mContext.getString(R.string.error_phone));
            return;
        }

        if (!checkVerify(verifyCode)) {
            mView.resetPasswordFail(mContext.getString(R.string.error_verify_code));
            return;
        }


        if (!checkPassword(newPassword1, newPassword2)) {
            mView.resetPasswordFail(mContext.getString(R.string.error_two_password));
            return;
        }

        //重置手机号
        XLog.d("重置密码");

        mView.resetPasswordSuccess();
    }

    private boolean checkPhone(String phone) {
        XLog.d("检查手机号码是否正确");
        return RegexUtils.isMobileExact(phone);
    }

    private boolean checkVerify(String verifyCode) {
        XLog.d("检查验证码是否正确");
        return verifyCode.length() == 6;
    }

    private boolean checkPassword(String pwd1, String pwd2) {
        XLog.d("检查两次密码输入是否正确");
        return StringUtils.equals(pwd1, pwd2) && pwd1.length() >= 6;
    }
}
