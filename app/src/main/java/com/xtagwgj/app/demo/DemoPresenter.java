package com.xtagwgj.app.demo;

import com.elvishew.xlog.XLog;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xtagwgj.app.demo.api.ApiUser;
import com.xtagwgj.app.demo.model.AdvertisementResponse;
import com.xtagwgj.app.demo.model.FileResponse;
import com.xtagwgj.app.model.LoginInfoResponse;
import com.xtagwgj.common.commonutils.ToastUtils;
import com.xtagwgj.retrofitutils.http.ApiRequest;
import com.xtagwgj.retrofitutils.http.factory.ProgressSubscriber;
import com.xtagwgj.retrofitutils.http.listener.HttpOnNextListener;

import java.util.List;

import rx.Subscriber;

/**
 * Created by xtagwgj on 2016/12/11.
 */

public class DemoPresenter implements DemoContract.Presenter {

    private DemoContract.View mView;
    private RxAppCompatActivity context;
    private ApiUser apiUser;

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

        apiUser.login(username, password,
                new ProgressSubscriber<List<LoginInfoResponse>>(new HttpOnNextListener<List<LoginInfoResponse>>() {
                    @Override
                    public void onNext(List<LoginInfoResponse> o) {
                        if (o != null) {
                            LoginInfoResponse loginInfoResponse = o.get(0);
                            XLog.d(loginInfoResponse);
                            mView.setLoginInfo(loginInfoResponse.toString());
                        } else {
                            ToastUtils.showLongToast(context, "o is null");
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.showLongToast(context, throwable == null ? "throwable is null" : throwable.getMessage());
                    }
                }, context));
    }

    /**
     * 请求获取广告数据
     */
    @Override
    public void getAd(String code, int type) {
        apiUser.getAd(context, code, type, new Subscriber<List<AdvertisementResponse>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showLongToastSafe(context, e.getMessage());
            }

            @Override
            public void onNext(List<AdvertisementResponse> advertisementResponses) {
                mView.setAdInfo(advertisementResponses.toString());
            }
        });
    }

    @Override
    public void uploadPic(String file) {
        apiUser.uploadPic(context, file, new Subscriber<List<FileResponse>>() {
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
                if (fileResponses != null && fileResponses.size() > 0)
                    mView.showPic(ApiRequest.instance.getBASE_URL() + fileResponses.get(0).getSavePath());
            }
        });
    }

    @Override
    public void subscribe() {
        if (apiUser == null)
            apiUser = new ApiUser();
    }

    @Override
    public void unSubscribe() {
        apiUser = null;
    }
}
