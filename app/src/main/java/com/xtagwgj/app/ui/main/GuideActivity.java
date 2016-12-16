package com.xtagwgj.app.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xtagwgj.app.R;
import com.xtagwgj.common.base.AppManager;
import com.xtagwgj.common.base.BaseActivity;
import com.xtagwgj.common.commonutils.AppUtils;
import com.xtagwgj.common.commonutils.SPUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class GuideActivity extends BaseActivity {

    //引导图片的数组
    private int[] INDICATOR_IDS = {R.mipmap.welcome2, R.mipmap.welcome1};

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, GuideActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
        AppManager.getAppManager().finishActivity(activity);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        loadIndicatorPage();
    }

    @Override
    public void initEventListener() {

    }

    //加载引导页面
    private void loadIndicatorPage() {

        final ImagePagerAdapter adapter = new ImagePagerAdapter(this, ImageView.ScaleType.FIT_XY);
        viewPager.setAdapter(adapter);

        adapter.getView(INDICATOR_IDS.length - 1).setOnClickListener(v -> {
            SPUtils.getInstance().putInt( "versionCode", AppUtils.getAppVersionCode(GuideActivity.this));
            MainActivity.startAction(GuideActivity.this);
        });
    }


    class ImagePagerAdapter extends PagerAdapter {

        private ArrayList<View> imageViews = new ArrayList<>();

        public ImagePagerAdapter(final Activity context, ImageView.ScaleType scaleType) {
            for (int id : INDICATOR_IDS) {
                View view = View.inflate(context, R.layout.photo_item,
                        null);
                ImageView imageView = (ImageView) view.findViewById(R.id.iconImageView1);
                imageView.setScaleType(scaleType);
                imageView.setBackgroundResource(id);
                imageViews.add(view);
            }
        }

        @Override
        public int getCount() {
            return INDICATOR_IDS.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getView(position);
            container.addView(view);
            return view;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public View getView(int index) {
            return imageViews.get(index);
        }

    }
}
