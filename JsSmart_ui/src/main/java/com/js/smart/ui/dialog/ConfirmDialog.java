package com.js.smart.ui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.js.smart.common.ui.dialog.DialogBuilder;
import com.js.smart.ui.R;

import org.apache.commons.lang.StringUtils;


/**
 * Created by Js on 2016/6/22.
 */
public class ConfirmDialog extends DialogBuilder<ConfirmDialog> {

    public ConfirmDialog(Context context) {
        super(context);
    }


    public Dialog showConfirmDialog(String title, String content, View.OnClickListener listener) {
        return showConfirmDialog(title, new SpannedString(content), listener);
    }

    public Dialog showConfirmDialog(String title, Spanned content, View.OnClickListener listener) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null);
        TextView titleTv = view.findViewById(R.id.textView1);
        TextView contentTv = view.findViewById(R.id.textView2);

        if(StringUtils.isBlank(title))
            titleTv.setVisibility(View.GONE);
        titleTv.setText(title);
        contentTv.setText(content);
        TextView rightBt = view.findViewById(R.id.btn2);
        TextView leftBt = view.findViewById(R.id.btn1);
        View right = view.findViewById(R.id.layout2);
        leftBt.setOnClickListener(listener);
        rightBt.setOnClickListener(listener);
        if (!TextUtils.isEmpty(leftStr)) {
            leftBt.setText(leftStr);
        }
        if (!TextUtils.isEmpty(rightStr)) {
            rightBt.setText(rightStr);
        }

        return show();
    }

}
