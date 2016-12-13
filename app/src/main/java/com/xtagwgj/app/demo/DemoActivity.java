package com.xtagwgj.app.demo;

import android.widget.TextView;

import com.xtagwgj.app.R;
import com.xtagwgj.common.base.BaseActivity;
import com.xtagwgj.common.mvp.ActivityUtils;

import butterknife.BindView;

public class DemoActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private DemoPresenter demoPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_demo;
    }


    @Override
    public void initView() {
        DemoFragment demoFragment =
                (DemoFragment) getFragmentManager().findFragmentById(R.id.contentFrame);

        if (demoFragment == null) {
            demoFragment = DemoFragment.newInstance();

            if (getIntent().hasExtra(DemoFragment.Arguement_task)) {
                tvTitle.setText("hasExtra");
            } else {
                tvTitle.setText("none");
            }

            ActivityUtils.addFragmentToActivity(getFragmentManager(),
                    demoFragment, R.id.contentFrame);

        }

        // Create the presenter
        demoPresenter = new DemoPresenter(tvTitle.getText().toString(), "", demoFragment);

        demoFragment.setPresenter(demoPresenter);


    }

    @Override
    public void initEventListener() {

    }
}
