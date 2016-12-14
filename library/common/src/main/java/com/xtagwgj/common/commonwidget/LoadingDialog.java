package com.xtagwgj.common.commonwidget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xtagwgj.common.R;

/**
 * 自定义的加载框
 * Created by xtagwgj on 2016/12/14.
 */

public class LoadingDialog extends Dialog {
    private TextView loadingTextView;

    public LoadingDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
        setContentView(R.layout.dialog_loading);
        loadingTextView = (TextView) findViewById(R.id.id_tv_loading_dialog_text);
        loadingTextView.setText("加载中...");
    }

    public void setDialogText(String strId) {
        if (loadingTextView != null) {
            loadingTextView.setVisibility(View.VISIBLE);
            loadingTextView.setText(strId);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();

    }
}
