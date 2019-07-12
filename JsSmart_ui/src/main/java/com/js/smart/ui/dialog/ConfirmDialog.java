package com.js.smart.ui.dialog;


import android.content.Context;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.js.smart.common.app.AntiShakeOnClickListener;
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


    public ConfirmDialog showConfirmDialog(String title, String content, AntiShakeOnClickListener listener) {
        return showConfirmDialog(title, new SpannedString(content), listener);
    }

    public ConfirmDialog showConfirmDialog(String title, Spanned content, AntiShakeOnClickListener listener) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null);
        TextView titleTv = view.findViewById(R.id.textView1);
        TextView contentTv = view.findViewById(R.id.textView2);

        if(StringUtils.isBlank(title))
            titleTv.setVisibility(View.GONE);
        titleTv.setText(title);
        contentTv.setText(content);
        TextView rightBt = view.findViewById(R.id.btn2);
        TextView leftBt = view.findViewById(R.id.btn1);

        if(listener != null)
            setLeftRightClick(listener, listener);
        leftBt.setOnClickListener(leftClickListener);
        rightBt.setOnClickListener(rightClickListener);

        if (!TextUtils.isEmpty(leftStr)) {
            leftBt.setText(leftStr);
        }
        if (!TextUtils.isEmpty(rightStr)) {
            rightBt.setText(rightStr);
        }

        setDialog(show());
        return this;
    }

}
