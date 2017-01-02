package com.xtagwgj.common.commonwidget.toprightmenu;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.xtagwgj.common.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 手机QQ界面右上角弹出菜单
 * Created by xtagwgj on 2017/1/1.
 */
/*

TopRightMenu mTopRightMenu = new TopRightMenu(MainActivity.this);

//添加菜单项
List<MenuItem> menuItems = new ArrayList<>();
menuItems.add(new MenuItem(R.mipmap.multichat, "发起多人聊天"));
menuItems.add(new MenuItem(R.mipmap.addmember, "加好友"));
menuItems.add(new MenuItem(R.mipmap.qr_scan, "扫一扫"));

mTopRightMenu
        .setHeight(480)     //默认高度480
        .setWidth(320)      //默认宽度wrap_content
        .showIcon(true)     //显示菜单图标，默认为true
        .dimBackground(true)        //背景变暗，默认为true
        .needAnimationStyle(true)   //显示动画，默认为true
        .setAnimationStyle(R.style.TRM_ANIM_STYLE)
        .addMenuList(menuItems)
        .addMenuItem(new MenuItem(R.mipmap.facetoface, "面对面快传"))
        .addMenuItem(new MenuItem(R.mipmap.pay, "付款"))
        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
              @Override
              public void onMenuItemClick(int position) {
                  Toast.makeText(MainActivity.this, "点击菜单:" + position, Toast.LENGTH_SHORT).show();
              }
        })
        .showAsDropDown(moreBtn, -225, 0);    //带偏移量
//              .showAsDropDown(moreBtn)

 */

public class TopRightMenu {
    private Activity mContext;
    private PopupWindow mPopupWindow;
    private RecyclerView mRecyclerView;
    private View content;

    private TRMenuAdapter mAdapter;
    private List<MenuItem> menuItemList;

    private static final int DEFAULT_HEIGHT = 480;
    private int popHeight = DEFAULT_HEIGHT;
    private int popWidth = RecyclerView.LayoutParams.WRAP_CONTENT;
    private boolean showIcon = true;
    private boolean dimBackground = true;
    private boolean needAnimationStyle = true;

    private static final int DEFAULT_ANIM_STYLE = R.style.TRM_ANIM_STYLE;
    private int animationStyle;

    private float alpha = 0.75f;

    public TopRightMenu(Activity context) {
        this.mContext = context;
        init();
    }

    private void init() {
        content = LayoutInflater.from(mContext).inflate(R.layout.trm_popup_menu, null);
        mRecyclerView = (RecyclerView) content.findViewById(R.id.trm_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        menuItemList = new ArrayList<>();
        mAdapter = new TRMenuAdapter(mContext, this, menuItemList, showIcon);
    }

    private PopupWindow getPopupWindow() {
        mPopupWindow = new PopupWindow(mContext);
        mPopupWindow.setContentView(content);
        mPopupWindow.setHeight(popHeight);
        mPopupWindow.setWidth(popWidth);
        if (needAnimationStyle) {
            mPopupWindow.setAnimationStyle(animationStyle <= 0 ? DEFAULT_ANIM_STYLE : animationStyle);
        }

        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (dimBackground) {
                    setBackgroundAlpha(alpha, 1f, 300);
                }
            }
        });

        mAdapter.setData(menuItemList);
        mAdapter.setShowIcon(showIcon);
        mRecyclerView.setAdapter(mAdapter);
        return mPopupWindow;
    }

    public TopRightMenu setHeight(int height) {
        if (height <= 0 && height != RecyclerView.LayoutParams.MATCH_PARENT
                && height != RecyclerView.LayoutParams.WRAP_CONTENT) {
            this.popHeight = DEFAULT_HEIGHT;
        } else {
            this.popHeight = height;
        }
        return this;
    }

    public TopRightMenu setWidth(int width) {
        if (width <= 0 && width != RecyclerView.LayoutParams.MATCH_PARENT) {
            this.popWidth = RecyclerView.LayoutParams.WRAP_CONTENT;
        } else {
            this.popWidth = width;
        }
        return this;
    }

    /**
     * 是否显示菜单图标
     *
     * @param show
     * @return
     */
    public TopRightMenu showIcon(boolean show) {
        this.showIcon = show;
        return this;
    }

    /**
     * 添加单个菜单
     *
     * @param item
     * @return
     */
    public TopRightMenu addMenuItem(MenuItem item) {
        menuItemList.add(item);
        return this;
    }

    /**
     * 添加多个菜单
     *
     * @param list
     * @return
     */
    public TopRightMenu addMenuList(List<MenuItem> list) {
        menuItemList.addAll(list);
        return this;
    }

    /**
     * 是否让背景变暗
     *
     * @param b
     * @return
     */
    public TopRightMenu dimBackground(boolean b) {
        this.dimBackground = b;
        return this;
    }

    /**
     * 否是需要动画
     *
     * @param need
     * @return
     */
    public TopRightMenu needAnimationStyle(boolean need) {
        this.needAnimationStyle = need;
        return this;
    }

    /**
     * 设置动画
     *
     * @param style
     * @return
     */
    public TopRightMenu setAnimationStyle(int style) {
        this.animationStyle = style;
        return this;
    }

    public TopRightMenu setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        mAdapter.setOnMenuItemClickListener(listener);
        return this;
    }

    public TopRightMenu showAsDropDown(View anchor) {
        showAsDropDown(anchor, 0, 0);
        return this;
    }

    public TopRightMenu showAsDropDown(View anchor, int xoff, int yoff) {
        if (mPopupWindow == null) {
            getPopupWindow();
        }
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAsDropDown(anchor, xoff, yoff);
            if (dimBackground) {
                setBackgroundAlpha(1f, alpha, 240);
            }
        }
        return this;
    }

    private void setBackgroundAlpha(float from, float to, int duration) {
        final WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.alpha = (float) animation.getAnimatedValue();
                mContext.getWindow().setAttributes(lp);
            }
        });
        animator.start();
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(int position);
    }
}