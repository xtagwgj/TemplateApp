package com.zyu.app.ui.user.contract;


import com.zyu.app.ui.user.model.UserModel;
import com.xtagwgj.common.base.BaseMvpPresenter;
import com.xtagwgj.common.base.BaseMvpView;

/**
 * 用户登录
 * Created by xtagwgj on 2016/12/15.
 */

public class LoginContract {

    public interface View extends BaseMvpView {
        void loginFail(String errMsg);

        void loginSuccess();

        void displayAccountAndPassword(String account, String password);
    }

     public abstract static class Presenter extends BaseMvpPresenter<View, UserModel> {
        public abstract  void toLogin(String account, String password, boolean rememberPwd);

        public abstract  void showLastLoginInfo();
    }
}
