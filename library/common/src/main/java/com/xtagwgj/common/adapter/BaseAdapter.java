package com.xtagwgj.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Recycler的基本包装类
 * Created by zy on 2016/7/1.
 */
public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    private int itemViewID;
    protected Context mContext;
    private List<T> mDatas;
    private LayoutInflater mInflater;
    protected OnItemViewClickListener onItemViewClickListener;

    public BaseAdapter(Context mContext, List<T> mDatas, int itemViewID) {
        this.mContext = mContext;
        this.mDatas = (mDatas == null ? new ArrayList<T>() : mDatas);
        this.itemViewID = itemViewID;
        mInflater = LayoutInflater.from(mContext);
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mInflater.inflate(itemViewID, null, false);
        /**
         * RecyclerView对父布局进行点击的监听
         * 对布局中某个元素的监听可以通过
         * holder.getButton(...).setOnClickListener(
         *     view->onItemViewClickListener(view,position));
         * 来使用
         */
        return new BaseViewHolder(mView, onItemViewClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, position, getItem(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public List<T> getDatas() {
        return mDatas;
    }


    public void addData(List<T> datas) {
        if (datas == null)
            return;

        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void removeItem(T t) {
        int position = mDatas.indexOf(t);
        if (position > -1) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void ClearData() {
        if (mDatas == null || mDatas.size() <= 0)
            return;

        mDatas.clear();
        notifyDataSetChanged();
    }


    public void refreshData(List<T> data) {
        if (data == null)
            return;

        ClearData();
        addData(data);
    }

    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    protected abstract void bindData(BaseViewHolder holder, int position, T data);
}
