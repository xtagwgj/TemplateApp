package com.xtagwgj.common.commonutils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;

/**
 * Created by xtagwgj on 2016/12/11.
 */

public class ActivityUtils {
    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        if(fragment==null || fragmentManager==null){
            throw new NullPointerException("fragment or fragmentManager must not be null");
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void addFragmentToActivity (@NonNull android.support.v4.app.FragmentManager fragmentManager,
                                              @NonNull android.support.v4.app.Fragment fragment, int frameId) {
        if(fragment==null || fragmentManager==null){
            throw new NullPointerException("fragment or fragmentManager must not be null");
        }

        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
