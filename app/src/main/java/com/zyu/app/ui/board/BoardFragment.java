package com.zyu.app.ui.board;

import com.zyu.app.R;
import com.xtagwgj.common.base.BaseFragment;
import com.xtagwgj.common.commonwidget.NormalTitleBar;

/**
 * 公告
 * Created by xtagwgj on 2016/12/14.
 */

public class BoardFragment extends BaseFragment {
    NormalTitleBar normalTitleBar;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_board;
    }

    @Override
    protected void initView() {
        normalTitleBar = (NormalTitleBar) getActivity().findViewById(R.id.titleBar);

    }

    @Override
    protected void initEventListener() {

    }
}
