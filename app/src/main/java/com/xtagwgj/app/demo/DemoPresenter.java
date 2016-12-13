package com.xtagwgj.app.demo;

/**
 * Created by xtagwgj on 2016/12/11.
 */

public class DemoPresenter implements DemoContract.Presenter {

    private String title;
    private String description;
    private DemoContract.View mView;

    DemoPresenter(String title, String description, DemoContract.View mView) {
        this.title = title;
        this.description = description;
        this.mView = mView;
    }


    @Override
    public void saveTask(String title, String description) {
        mView.setTitle(title + description);

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
