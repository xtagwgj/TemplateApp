package com.zyu.app.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.elvishew.xlog.XLog;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.xtagwgj.common.base.AppManager;
import com.xtagwgj.common.base.BaseActivity;
import com.xtagwgj.common.commonutils.ToastUtils;
import com.xtagwgj.common.commonwidget.NormalTitleBar;
import com.zyu.app.R;
import com.zyu.app.base.Constant;
import com.zyu.app.domain.TabEntity;
import com.zyu.app.ui.board.BoardFragment;
import com.zyu.app.ui.maintain.MaintainFragment;
import com.zyu.app.ui.management.ManagementFragment;
import com.zyu.app.ui.me.MeFragment;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;

public class MainActivity extends BaseActivity {
    //最后一次点击后退的时间
    private long exitTime = 0l;

    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    @BindView(R.id.titleBar)
    NormalTitleBar titleBar;

    @BindArray(R.array.navi_bottom_name)
    String[] mTitles;

    //    private String[] mTitles = {"维修", "管理","公告",  "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.nav_maintain_unselected, R.mipmap.nav_management_unselected, R.mipmap.nav_board_unselected, R.mipmap.nav_me_unselected};
    private int[] mIconSelectIds = {
            R.mipmap.nav_maintain_selected, R.mipmap.nav_management_selected, R.mipmap.nav_board_selected, R.mipmap.nav_me_selected};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private MaintainFragment maintainFragment;
    private BoardFragment boardFragment;
    private ManagementFragment managementFragment;
    private MeFragment meFragment;

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
        AppManager.getAppManager().finishActivity(activity);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleBar.setBackVisibility(false);

        initTab();
        tabLayout.measure(0, 0);
        //初始化frament
        initFragment(savedInstanceState);
    }

    @Override
    public void initEventListener() {
//        RxView.clicks(findViewById(R.id.btn_toDemo))
//                .throttleFirst(1, TimeUnit.SECONDS)
//                .subscribe(aVoid -> {
//                    startActivity(DemoActivity.class);
//                });

    }

    /**
     * 初始化底部导航栏
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }


    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            maintainFragment = (MaintainFragment) getSupportFragmentManager().findFragmentByTag("maintainFragment");
            managementFragment = (ManagementFragment) getSupportFragmentManager().findFragmentByTag("maintainFragment");
            boardFragment = (BoardFragment) getSupportFragmentManager().findFragmentByTag("boardFragment");
            meFragment = (MeFragment) getSupportFragmentManager().findFragmentByTag("meFragment");
            currentTabPosition = savedInstanceState.getInt(Constant.HOME_CURRENT_TAB_POSITION);
        } else {
            maintainFragment = new MaintainFragment();
            managementFragment = new ManagementFragment();
            boardFragment = new BoardFragment();
            meFragment = new MeFragment();

            transaction.add(R.id.fl_body, maintainFragment, "maintainFragment");
            transaction.add(R.id.fl_body, managementFragment, "managementFragment");
            transaction.add(R.id.fl_body, boardFragment, "boardFragment");
            transaction.add(R.id.fl_body, meFragment, "meFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.hide(maintainFragment);
        transaction.hide(boardFragment);
        transaction.hide(managementFragment);
        transaction.hide(meFragment);

        switch (position) {
            //维修
            case 0:
                transaction.show(maintainFragment);
                break;
            //管理
            case 1:
                transaction.show(managementFragment);
                break;
            //公告
            case 2:
                transaction.show(boardFragment);
                break;
            //我的
            case 3:
                transaction.show(meFragment);
                break;
        }
        titleBar.setTitleText(mTitles[position]);

        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //奔溃前保存位置
        XLog.e("onSaveInstanceState进来了1");
        if (tabLayout != null) {
            XLog.e("onSaveInstanceState进来了2");
            outState.putInt(Constant.HOME_CURRENT_TAB_POSITION, tabLayout.getCurrentTab());
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return isConsumeBackKey();
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isConsumeBackKey() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {//未处理监听事件，请求后续监听
            ToastUtils.showShortToast(this, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }

        return true;
    }
}
