package com.zyu.app.ui.me;

import android.widget.TextView;

import com.xtagwgj.common.commonutils.AppUtils;
import com.zyu.app.R;
import com.xtagwgj.common.base.BaseFragment;

import butterknife.BindView;

/**
 * 我的
 * Created by xtagwgj on 2016/12/14.
 */

public class MeFragment extends BaseFragment {

    @BindView(R.id.tv_packageName)
    TextView textView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {

        textView.setText(AppUtils.getAppPackageName(getActivity()));
    }

    @Override
    protected void initEventListener() {

    }
}
