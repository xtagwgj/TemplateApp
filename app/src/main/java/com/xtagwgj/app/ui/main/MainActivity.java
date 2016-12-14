package com.xtagwgj.app.ui.main;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.elvishew.xlog.XLog;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.xtagwgj.app.R;
import com.xtagwgj.app.base.Constant;
import com.xtagwgj.app.model.TabEntity;
import com.xtagwgj.app.ui.board.BoardFragment;
import com.xtagwgj.app.ui.maintain.MaintainFragment;
import com.xtagwgj.app.ui.management.ManagementFragment;
import com.xtagwgj.app.ui.me.MeFragment;
import com.xtagwgj.common.base.AppManager;
import com.xtagwgj.common.base.BaseActivity;
import com.xtagwgj.common.commonutils.ToastUtils;
import com.xtagwgj.common.commonwidget.NormalTitleBar;

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

    //    private String[] mTitles = {"维修", "公告", "管理", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_normal, R.mipmap.ic_girl_normal, R.mipmap.ic_video_normal, R.mipmap.ic_care_normal};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected, R.mipmap.ic_girl_selected, R.mipmap.ic_video_selected, R.mipmap.ic_care_selected};

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
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            maintainFragment = (MaintainFragment) getFragmentManager().findFragmentByTag("maintainFragment");
            boardFragment = (BoardFragment) getFragmentManager().findFragmentByTag("boardFragment");
            managementFragment = (ManagementFragment) getFragmentManager().findFragmentByTag("maintainFragment");
            meFragment = (MeFragment) getFragmentManager().findFragmentByTag("meFragment");
            currentTabPosition = savedInstanceState.getInt(Constant.HOME_CURRENT_TAB_POSITION);
        } else {
            maintainFragment = new MaintainFragment();
            boardFragment = new BoardFragment();
            managementFragment = new ManagementFragment();
            meFragment = new MeFragment();

            transaction.add(R.id.fl_body, maintainFragment, "maintainFragment");
            transaction.add(R.id.fl_body, boardFragment, "boardFragment");
            transaction.add(R.id.fl_body, managementFragment, "managementFragment");
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
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.hide(maintainFragment);
        transaction.hide(boardFragment);
        transaction.hide(managementFragment);
        transaction.hide(meFragment);

        switch (position) {
            //维修
            case 0:

                transaction.show(maintainFragment);
                break;
            //公告
            case 1:
                transaction.show(boardFragment);
                break;
            //管理
            case 2:
                transaction.show(managementFragment);
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
            ToastUtils.showShortToastSafe(this, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }

        return true;
    }
}
