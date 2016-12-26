package com.xtagwgj.app.demo;

import com.xtagwgj.common.mvp.BasePresenter;
import com.xtagwgj.common.mvp.BaseView;

/**
 * Created by xtagwgj on 2016/12/11.
 */

class DemoContract {
    interface View extends BaseView<Presenter> {

        void setTitle(String title);

        void setLoginInfo(String loginInfo);

        void setAdInfo(String adInfo);

        void showPic(String url);
    }

    interface Presenter extends BasePresenter {

        void saveTask(String title);

        void login(String username, String password);

        void getAd(String code, int type);

        void uploadPic(String file);
    }
}
