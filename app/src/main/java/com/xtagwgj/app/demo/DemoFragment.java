package com.xtagwgj.app.demo;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.xtagwgj.app.R;
import com.xtagwgj.common.base.BaseFragment;
import com.xtagwgj.common.commonutils.ImageLoaderUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import pub.devrel.easypermissions.EasyPermissions;
import rx.functions.Action1;

/**
 * Created by xtagwgj on 2016/12/11.
 */

public class DemoFragment extends BaseFragment implements DemoContract.View, EasyPermissions.PermissionCallbacks {
    public static final String Arguement_task = "task";
    private static int PERMISSION_UPLOAD_FILE = 123;

    private DemoContract.Presenter mPresenter;

    @BindView(R.id.add_task_title)
    TextView mTitle;

    @BindView(R.id.btn_showText)
    Button btnShowText;

    @BindView(R.id.et_input)
    EditText etInputShowText;

    @BindView(R.id.btn_changeTitle)
    Button btn_changeTitle;

    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.btn_getAd)
    Button btn_getAd;

    @BindView(R.id.iv_showPic)
    ImageView iv_showPic;

    TextView mainTitle;


    public static DemoFragment newInstance() {
        return new DemoFragment();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        mainTitle = (TextView) getActivity().findViewById(R.id.tvTitle);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.addtask_frag;
    }

    @Override
    protected void initView() {
        mPresenter.saveTask("testTitle");
        showPic("http://qpic.588ku.com/58pic/21/47/80/43y58PICvzn.jpg");
    }

    @Override
    protected void initEventListener() {

        RxView.clicks(btnShowText)
                .throttleFirst(1, TimeUnit.SECONDS)//防抖处理,解决按钮多次响应的问题
                .subscribe(aVoid -> {
                    //edittext中的内容变化的时候，异步更新textview的显示
                    RxTextView.textChanges(etInputShowText).subscribe(charSequence -> {
                        mPresenter.saveTask(charSequence.toString());
                    });
                });

        RxView.clicks(btn_changeTitle)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    RxTextView.textChanges(etInputShowText).subscribe(charSequence -> {
                        mainTitle.setText(charSequence);
                    });
                });

        RxView.clicks(btn_login)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.login("18566077938", "e0eaca43a1c2a57221cac6d0f8758e88");
                    }
                });

        RxView.clicks(btn_getAd)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.getAd("0", 0);
                    }
                });
    }

    /**
     * 加载图片
     *
     * @param url
     */
    @Override
    public void showPic(String url) {
        setTitle("图片地址：" + url);
        ImageLoaderUtils.display(getActivity(), iv_showPic, url);
    }

    /**
     * 动态申请权限
     */
    @OnClick(R.id.btn_upFile)
    public void uploadFile() {
        //请求权限
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            MultiImageSelector.create()
                    .single()
                    .start(getActivity(), 111);
        } else {
            EasyPermissions.requestPermissions(this, "拍照需要摄像头权限",
                    PERMISSION_UPLOAD_FILE, perms);
        }

    }

    @Override
    public void setTitle(String title) {
        if (mTitle != null)
            mTitle.setText(title);
    }

    @Override
    public void setLoginInfo(String loginInfo) {
        mTitle.setText(loginInfo);
    }

    @Override
    public void setAdInfo(String adInfo) {
        mTitle.setText(adInfo);
    }

    @Override
    public void setPresenter(@NonNull DemoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unSubscribe();
    }

    private void openGallery() {
        MultiImageSelector.create()
                .single()
                .start(getActivity(), 111);
    }

    //请求权限成功 处理
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == PERMISSION_UPLOAD_FILE)
            openGallery();
    }


    //请求权限失败 弹出提示
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
