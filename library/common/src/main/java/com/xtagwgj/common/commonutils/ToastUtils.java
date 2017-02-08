package com.xtagwgj.common.commonutils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xtagwgj.common.BaseApplication;
import com.xtagwgj.common.R;

import io.github.zhitaocai.toastcompat.ToastCompat;

/**
 * Toast统一管理类
 * Created by xtagwgj on 2016/12/10.
 */

public class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Toast toast2;

    /**
     * 安全地显示短时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void showShortToast(Context context, String text) {
        ToastCompat.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 安全地显示短时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     */
    public static void showShortToast(Context context, int resId) {
        ToastCompat.makeText(context, context.getResources().getString(resId), Toast.LENGTH_SHORT).show();
    }


    /**
     * 安全地显示长时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void showLongToast(final Context context, final String text) {
        ToastCompat.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 安全地显示长时吐司
     *
     * @param context 上下文
     * @param resId   资源Id
     */
    public static void showLongToast(final Context context, final int resId) {
        ToastCompat.makeText(context, context.getResources().getString(resId), Toast.LENGTH_LONG).show();
    }


    /**
     * 显示吐司
     *
     * @param context  上下文
     * @param text     文本
     * @param duration 显示时长
     */
    private static void showToast(Context context, String text, int duration) {
        ToastCompat.makeText(context, text, duration).show();
    }

    /**
     * 显示吐司
     *
     * @param context  上下文
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private static void showToast(Context context, int resId, int duration) {
        ToastCompat.makeText(context, context.getResources().getText(resId).toString(), duration).show();
    }


    /**
     * 显示有image的toast
     *
     * @param tvStr
     * @param imageResource
     * @return
     */
    public static Toast showToastWithImg(final String tvStr, final int imageResource) {
        if (toast2 == null) {
            toast2 = new Toast(BaseApplication.getAppContext());
        }
        View view = LayoutInflater.from(BaseApplication.getAppContext()).inflate(R.layout.toast_custom, null);
        TextView tv = (TextView) view.findViewById(R.id.toast_custom_tv);
        tv.setText(TextUtils.isEmpty(tvStr) ? "" : tvStr);
        ImageView iv = (ImageView) view.findViewById(R.id.toast_custom_iv);
        if (imageResource > 0) {
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(imageResource);
        } else {
            iv.setVisibility(View.GONE);
        }
        toast2.setView(view);
        toast2.setGravity(Gravity.CENTER, 0, 0);
        toast2.show();
        return toast2;

    }

}
