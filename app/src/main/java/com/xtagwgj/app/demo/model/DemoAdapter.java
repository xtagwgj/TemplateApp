package com.xtagwgj.app.demo.model;

import android.content.Context;

import com.xtagwgj.common.adapter.BaseAdapter;
import com.xtagwgj.common.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by xtagwgj on 2016/12/26.
 */

public class DemoAdapter extends BaseAdapter<String, BaseViewHolder> {
    public DemoAdapter(Context mContext, List mDatas, int itemViewID) {
        super(mContext, mDatas, itemViewID);
    }


    @Override
    protected void bindData(BaseViewHolder holder, int position, String data) {

    }
}
