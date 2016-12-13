package com.xtagwgj.app.demo;

import com.xtagwgj.common.mvp.BasePresenter;
import com.xtagwgj.common.mvp.BaseView;

/**
 * Created by xtagwgj on 2016/12/11.
 */

public class DemoContract {
    interface View extends BaseView<Presenter> {

        void setTitle(String title);

    }

    interface Presenter extends BasePresenter {

        void saveTask(String title, String description);

    }
}
