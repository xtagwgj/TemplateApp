package com.zyu.app.demo;

import com.elvishew.xlog.XLog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xtagwgj.common.commonutils.ToastUtils;
import com.zyu.app.demo.api.ApiDemo;
import com.zyu.app.demo.model.AdvertisementResponse;
import com.zyu.app.demo.model.FileResponse;

import java.util.List;

import rx.Subscriber;

/**
 * Created by xtagwgj on 2016/12/11.
 */

class DemoPresenter implements DemoContract.Presenter {

    private DemoContract.View mView;
    private RxAppCompatActivity context;
    private ApiDemo apiDemo;

    DemoPresenter(RxAppCompatActivity context, String title, DemoContract.View mView) {
        this.context = context;
        this.mView = mView;
        mView.setTitle(title);
    }


    @Override
    public void saveTask(String title) {
        mView.setTitle(title);
    }

    /**
     * 请求登陆
     */
    @Override
    public void login(String username, String password) {

//        apiDemo.login(username, password,
//                new ProgressSubscriber<>(new HttpOnNextListener<List<LoginInfoResponse>>() {
//                    @Override
//                    public void onNext(List<LoginInfoResponse> o) {
//                        if (o != null) {
//                            LoginInfoResponse loginInfoResponse = o.get(0);
//                            XLog.d(loginInfoResponse);
//                            mView.setLoginInfo(loginInfoResponse.toString());
//                        } else {
//                            ToastUtils.showLongToast(context, "o is null");
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        ToastUtils.showLongToast(context, throwable == null ? "throwable is null" : throwable.getMessage());
//                    }
//                }, context));
    }

    /**
     * 请求获取广告数据
     */
    @Override
    public void getAd(String code, int type) {
        apiDemo.getAd(context, code, type, new Subscriber<List<AdvertisementResponse>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showLongToast(context, e.getMessage());
            }

            @Override
            public void onNext(List<AdvertisementResponse> advertisementResponses) {
                mView.setAdInfo(advertisementResponses.toString());
            }
        });
    }

    @Override
    public void uploadPic(String file) {
        apiDemo.uploadPic(context, file, new Subscriber<List<FileResponse>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                XLog.e(e);
            }

            @Override
            public void onNext(List<FileResponse> fileResponses) {
                XLog.e(fileResponses);
//                if (fileResponses != null && fileResponses.size() > 0)
//                    mView.showPic(ApiRequest.instance.getBASE_URL() + fileResponses.get(0).getSavePath());
            }
        });
    }

    @Override
    public void subscribe() {
        if (apiDemo == null)
            apiDemo = new ApiDemo();
    }

    @Override
    public void unSubscribe() {
        apiDemo = null;
    }
}
