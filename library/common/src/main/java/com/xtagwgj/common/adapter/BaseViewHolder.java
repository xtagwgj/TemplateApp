package com.xtagwgj.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ViewHolder类
 * Created by zy on 2016/7/1.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private SparseArray<View> mViews;
    private OnItemViewClickListener onItemViewClickListener;

    public BaseViewHolder(View itemView, OnItemViewClickListener onItemViewClickListener) {
        super(itemView);
        mViews = new SparseArray<>();
        this.onItemViewClickListener = onItemViewClickListener;
        itemView.setOnClickListener(this);
    }

    private <T extends View> T findView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

    public View getView(int id) {
        return findView(id);
    }

    public TextView getTextView(int id) {
        return findView(id);
    }

    public ImageView getImageView(int id) {
        return findView(id);
    }

    public Button getButton(int id) {
        return findView(id);
    }

    public LinearLayout getLinearLayout(int id) {
        return findView(id);
    }



    @Override
    public void onClick(View view) {
        if (onItemViewClickListener != null)
            onItemViewClickListener.onItemClick(view, getLayoutPosition());
    }
}
