package com.xtagwgj.common.base;


import android.os.Bundle;

import com.xtagwgj.common.BaseCompatActivity;
import com.xtagwgj.common.R;

import butterknife.ButterKnife;

/**
 * 基类
 */

/***************
 * 使用例子
 *********************/
//普通模式
//public class SampleActivity extends BaseActivity {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//    }
//
//    @Override
//    public void initView() {
//    }
//}
public abstract class BaseActivity extends BaseCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxManager = new RxManager();
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;

        this.initView();
        this.initEventListener();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRxManager.clear();
        AppManager.getAppManager().finishActivity(this);
        overridePendingTransition(R.anim.in_from_right,
                R.anim.out_to_right);

    }
}
