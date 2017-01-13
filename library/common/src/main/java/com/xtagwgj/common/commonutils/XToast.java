package com.xtagwgj.common.commonutils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xtagwgj.common.R;

/**
 * 自定义Toast 解决部分机型不显示系统的Toast
 * Created by xtagwgj on 2017/1/13.
 */

public class XToast {
    private static final int LENGTH_LONG = 3500; // 3.5 seconds
    private static final int LENGTH_SHORT = 2000; // 2 seconds

    private static View mNextView;
    private static int mGravity, mX = 0, mY = 0;
    private static final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private static WindowManager mWM;
    private static Handler mHandler = new Handler();

    /**
     * init
     *
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void init(Context context) {
//        mY = context.getResources().getDimensionPixelSize(
//                R.dimen.toast_y_offset);
//        mGravity = context.getResources().getInteger(
//                R.integer.config_toastDefaultGravity);

        mGravity = Gravity.CENTER;

        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mNextView = inflate.inflate(R.layout.transient_notification, null);
//        TextView tv = (TextView) mNextView.findViewById(android.R.id.message);

        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.windowAnimations = R.style.animation_Toast;
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mParams.setTitle("Toast");
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;


        mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // We can resolve the Gravity here by using the Locale for getting
        // the layout direction
        final Configuration config = mNextView.getContext().getResources().getConfiguration();
        final int gravity = Gravity.getAbsoluteGravity(mGravity, config.getLayoutDirection());
        mParams.gravity = gravity;
        if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
            mParams.horizontalWeight = 1.0f;
        }
        if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
            mParams.verticalWeight = 1.0f;
        }
        mParams.x = mX;
        mParams.y = mY;
//        mParams.verticalMargin = mVerticalMargin;
//        mParams.horizontalMargin = mHorizontalMargin;
        mParams.packageName = context.getPackageName();
    }

    static void showShort(Context context, CharSequence text) {
        show(context, text, LENGTH_SHORT);
    }

    static void showLong(Context context, CharSequence text) {
        show(context, text, LENGTH_LONG);
    }

    /**
     * @param context  上下文
     * @param text     文本
     * @param duration 时长
     */
    static void show(final Context context, final CharSequence text, int duration) {
        if (context == null) {
            throw new RuntimeException("context is null");
        }

        if (mWM == null || mNextView == null) {
            init(context);
        }
        mHandler.removeCallbacks(cancelRunable);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ((TextView) mNextView.findViewById(android.R.id.message)).setText(text);
                if (mNextView.getParent() != null)
                    mWM.removeView(mNextView);
                mWM.addView(mNextView, mParams);
            }
        });
        mHandler.postDelayed(cancelRunable, duration);
    }

    private static Runnable cancelRunable = new Runnable() {
        @Override
        public void run() {
            cancel();
        }
    };

    public void setGravity(int gravity, int x, int y) {
        mParams.gravity = gravity;
        mParams.x = x;
        mParams.y = y;
    }

    /**
     * 取消 toast
     */
    static void cancel() {
        if (mNextView != null && mNextView.getParent() != null)
            mWM.removeViewImmediate(mNextView);
    }
}
