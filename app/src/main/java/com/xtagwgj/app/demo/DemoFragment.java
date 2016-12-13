package com.xtagwgj.app.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xtagwgj.app.R;
import com.xtagwgj.common.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by xtagwgj on 2016/12/11.
 */

public class DemoFragment extends BaseFragment implements DemoContract.View {
    public static final String Arguement_task = "task";

    private DemoContract.Presenter mPresenter;

    @BindView(R.id.add_task_title)
    TextView mTitle;

    @BindView(R.id.btn_showText)
    Button btnShowText;

    @BindView(R.id.et_input)
    EditText etInputShowText;


    public static DemoFragment newInstance() {
        return new DemoFragment();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.addtask_frag;
    }

    @Override
    protected void initView() {
        mPresenter.saveTask("testTitle", "");
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);

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
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }
}
