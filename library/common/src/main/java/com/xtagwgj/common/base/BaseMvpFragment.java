package com.xtagwgj.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.RxFragment;
import com.xtagwgj.common.commonutils.TUtil;
import com.xtagwgj.common.commonutils.ToastUtils;
import com.xtagwgj.common.commonwidget.LoadingDialogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * des:基类fragment
 * Created by xsf
 * on 2016.07.12:38
 */

/***************
 * 使用例子
 *********************/
//mvp模式
//public class SampleFragment extends BaseFragment<NewsChanelPresenter, NewsChannelModel>implements NewsChannelContract.View {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//        mPresenter.setVM(this, mModel);
//    }
//
//    @Override
//    public void initView() {
//    }
//}
public abstract class BaseMvpFragment<T extends BaseMvpPresenter, E extends BaseMvpModel> extends RxFragment {
    public T mPresenter;
    public E mModel;
    protected View rootView;
    public RxManager mRxManager;
    private Unbinder unbind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        mRxManager = new RxManager();
        unbind = ButterKnife.bind(this, rootView);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }
        initPresenter();
        initView();
        return rootView;
    }

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();
    //获取布局文件
    protected abstract int getLayoutResource();
    //初始化view
    protected abstract void initView();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 开启加载进度条
     */
    public void startProgressDialog() {
        LoadingDialogUtils.showDialogForLoading(getActivity());
    }

    /**
     * 开启加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialogUtils.showDialogForLoading(getActivity(), msg, true);
    }

    /**
     * 停止加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialogUtils.cancelDialogForLoading();
    }


    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUtils.showShortToastSafe(getActivity(), text);
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
        ToastUtils.showShortToastSafe(getActivity(), resId);
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    public void showLongToast(int resId) {
        ToastUtils.showLongToastSafe(getActivity(), resId);
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUtils.showLongToastSafe(getActivity(), text);
    }


}
