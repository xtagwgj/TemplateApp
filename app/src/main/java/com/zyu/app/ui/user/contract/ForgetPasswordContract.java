package com.zyu.app.ui.user.contract;


import com.zyu.app.ui.user.model.UserModel;
import com.xtagwgj.common.base.BaseMvpPresenter;
import com.xtagwgj.common.base.BaseMvpView;

/**
 * 用户忘记密码
 * Created by xtagwgj on 2016/12/15.
 */

public class ForgetPasswordContract {

    public interface View extends BaseMvpView {
        void sendVerifyCodeSuccess();

        void sendVerifyCodeFail(String errMsg);

        void resetPasswordSuccess();

        void resetPasswordFail(String errMsg);
    }

    public abstract static class Presenter extends BaseMvpPresenter<View, UserModel> {
        public abstract void getVerifyCode(String phone);

        public abstract void resetPassword(String phone, String verifyCode, String newPassword1, String newPassword2);
    }
}
