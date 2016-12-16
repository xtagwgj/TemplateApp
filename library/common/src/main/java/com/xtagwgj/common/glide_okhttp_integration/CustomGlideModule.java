package com.xtagwgj.common.glide_okhttp_integration;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

/**
 * Glide加载https图片
 * Created by xtagwgj on 2016/12/15.
 * <p>
 * <meta-data
 * android:name="com.xtagwgj.common.glide_okhttp_integration.CustomGlideModule"
 * android:value="GlideModule" />
 */

public class CustomGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(UnsafeOkHttpClient.getUnsafeOkHttpClient()));

    }

}
