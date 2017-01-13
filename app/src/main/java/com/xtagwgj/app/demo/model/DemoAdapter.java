package com.xtagwgj.app.demo.model;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by xtagwgj on 2016/12/26.
 */

public class DemoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DemoAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
