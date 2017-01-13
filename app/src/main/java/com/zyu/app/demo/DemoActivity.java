package com.zyu.app.demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.zyu.app.R;
import com.xtagwgj.common.base.BaseActivity;
import com.xtagwgj.common.commonutils.ActivityUtils;

import java.util.List;

import butterknife.BindView;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class DemoActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private DemoPresenter demoPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_demo;
    }


    @Override
    public void initView(Bundle saveInstantState) {
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        DemoFragment demoFragment =
                (DemoFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (demoFragment == null) {
            demoFragment = DemoFragment.newInstance();

            if (getIntent().hasExtra(DemoFragment.Arguement_task)) {
                tvTitle.setText("hasExtra");
            } else {
                tvTitle.setText("none");
            }

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    demoFragment, R.id.contentFrame);

        }

        // Create the presenter
        demoPresenter = new DemoPresenter(this, tvTitle.getText().toString(), demoFragment);

        demoFragment.setPresenter(demoPresenter);


    }

    @Override
    public void initEventListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK) {
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            String imagePath = path.get(0);
            XLog.e(imagePath);
            demoPresenter.uploadPic(imagePath);
        }
    }
}
