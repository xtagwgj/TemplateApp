package com.zyu.app.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.jakewharton.rxbinding.view.RxView;
import com.zyu.app.R;
import com.zyu.app.base.Constant;
import com.zyu.app.ui.main.MainActivity;
import com.zyu.app.ui.user.contract.LoginContract;
import com.zyu.app.ui.user.model.UserModel;
import com.zyu.app.ui.user.presenter.LoginPresenter;
import com.xtagwgj.common.base.AppManager;
import com.xtagwgj.common.base.BaseMvpActivity;
import com.xtagwgj.common.commonutils.ToastUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

/**
 * 登录界面
 * <p>
 * Created by zy on 2016／12／15
 * <p>
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter, UserModel> implements LoginContract.View {

    @BindView(R.id.et_account)
    EditText et_account;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.cb_remember_password)
    CheckBox cb_remember_password;

    @BindView(R.id.tv_forget_password)
    TextView tv_forget_password;


    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
        AppManager.getAppManager().finishActivity(activity);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //强制页面全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    public void initEventListener() {
        /**
         * 点击登录
         */
        RxView.clicks(findViewById(R.id.bt_login))
                .throttleFirst(Constant.THROTTLE_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    mPresenter.toLogin(
                            et_account.getText().toString().trim(),
                            et_password.getText().toString().trim(),
                            cb_remember_password.isChecked());
                });

        /**
         * 点击忘记密码
         */
        RxView.clicks(tv_forget_password)
                .throttleFirst(Constant.THROTTLE_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    ForgetPasswordActivity.startAction(LoginActivity.this);
                });
    }

    @Override
    public void loginFail(String errMsg) {
        ToastUtils.showShortToast(this, errMsg);
       //开发使用
        loginSuccess();
    }

    @Override
    public void loginSuccess() {
        XLog.d("用户登录成功");
        MainActivity.startAction(LoginActivity.this);
    }

    @Override
    public void displayAccountAndPassword(String account, String password) {
        et_account.setText(account);
        et_password.setText(password);
    }


    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }
}
