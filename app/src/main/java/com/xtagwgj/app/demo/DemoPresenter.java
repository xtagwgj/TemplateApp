package com.xtagwgj.app.demo;

/**
 * Created by xtagwgj on 2016/12/11.
 */

public class DemoPresenter implements DemoContract.Presenter {

    private DemoContract.View mView;

    DemoPresenter(String title, DemoContract.View mView) {
        this.mView = mView;
        mView.setTitle(title);
    }


    @Override
    public void saveTask(String title) {
        mView.setTitle(title);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
