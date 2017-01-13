package com.zyu.app.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.elvishew.xlog.XLog;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义的极光推送广播接收器
 * Created by xtagwgj on 2016/12/16.
 */

//<!--User defined.用户自定义的广播接收器-->
//        <receiver
//        android:name="${JPUSH_RECEIVER_NAME}"
//        android:enabled="true">
//        <intent-filter>
//        <!--Required 用户注册SDK的intent-->
//        <action android:name="cn.jpush.android.intent.REGISTRATION"/>
//        <!--Required 用户接收SDK消息的intent-->
//        <action android:name="cn.jpush.android.intent.MESSAGE_RECEIVED"/>
//        <!--Required 用户接收SDK通知栏信息的intent-->
//        <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED"/>
//        <!--Required 用户打开自定义通知栏的intent-->
//        <action android:name="cn.jpush.android.intent.NOTIFICATION_OPENED"/>
//        <!--接收网络变化 连接/断开 since1.6.3-->
//        <action android:name="cn.jpush.android.intent.CONNECTION"/>
//        <category android:name="${JPUSH_PKGNAME}"/>
//        </intent-filter>
//        </receiver>

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        XLog.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + bundle.toString());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            XLog.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            XLog.d(TAG, "接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            XLog.d(TAG, "接受到推送下来的通知");
            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            XLog.d(TAG, "用户点击打开了通知");

            openNotification(context, bundle);

        } else {
            XLog.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        XLog.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        XLog.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        XLog.d(TAG, "extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("myKey");
        } catch (Exception e) {
            XLog.w(TAG, "Unexpected: extras is not a valid json", e);
            return;
        }
//        if (TYPE_THIS.equals(myValue)) {
//            Intent mIntent = new Intent(context, ThisActivity.class);
//            mIntent.putExtras(bundle);
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mIntent);
//        } else if (TYPE_ANOTHER.equals(myValue)){
//            Intent mIntent = new Intent(context, AnotherActivity.class);
//            mIntent.putExtras(bundle);
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mIntent);
//        }
    }
}
