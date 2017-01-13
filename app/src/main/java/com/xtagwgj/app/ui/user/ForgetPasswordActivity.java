package com.xtagwgj.app.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.xtagwgj.app.R;
import com.xtagwgj.app.base.Constant;
import com.xtagwgj.app.ui.user.contract.ForgetPasswordContract;
import com.xtagwgj.app.ui.user.model.UserModel;
import com.xtagwgj.app.ui.user.presenter.ForgetPasswordPresenter;
import com.xtagwgj.common.base.BaseMvpActivity;
import com.xtagwgj.common.commonutils.ToastUtils;
import com.xtagwgj.common.commonwidget.NormalTitleBar;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

/**
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseMvpActivity<ForgetPasswordPresenter, UserModel> implements ForgetPasswordContract.View {

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.et_verifyCode)
    EditText et_verifyCode;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.et_rePassword)
    EditText et_rePassword;


    @BindView(R.id.titleBar)
    NormalTitleBar titleBar;

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ForgetPasswordActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }


    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void sendVerifyCodeSuccess() {
        ToastUtils.showShortToast(this, "");
    }

    @Override
    public void sendVerifyCodeFail(String errMsg) {
        ToastUtils.showShortToast(this, errMsg);
    }

    @Override
    public void resetPasswordSuccess() {
        setResult(RESULT_OK);
        finishActivity(getClass());
    }

    @Override
    public void resetPasswordFail(String errMsg) {
        ToastUtils.showShortToast(this, errMsg);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleBar.setTitleText(getString(R.string.title_forget_pwd));
        titleBar.setOnBackListener(view -> {
            setResult(RESULT_CANCELED);
            finishActivity(ForgetPasswordActivity.this.getClass());
        });

    }

    @Override
    public void initEventListener() {
        /**
         * 获取验证码
         */
        RxView.clicks(findViewById(R.id.bt_get_verify))
                .throttleFirst(Constant.THROTTLE_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    mPresenter.getVerifyCode(et_phone.getText().toString().trim());
                });

        /**
         * 重置密码
         */
        RxView.clicks(findViewById(R.id.bt_ok))
                .throttleFirst(Constant.THROTTLE_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    mPresenter.resetPassword(
                            et_phone.getText().toString().trim(),
                            et_verifyCode.getText().toString().trim(),
                            et_password.getText().toString().trim(),
                            et_rePassword.getText().toString());
                });
    }
}
