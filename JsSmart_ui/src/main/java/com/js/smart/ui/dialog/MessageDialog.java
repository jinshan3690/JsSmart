package com.js.smart.ui.dialog;


import android.content.Context;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.js.smart.common.app.AntiShakeOnClickListener;
import com.js.smart.common.ui.dialog.DialogBuilder;
import com.js.smart.ui.R;


/**
 * Created by Js on 2016/6/22.
 */
public class MessageDialog extends DialogBuilder<MessageDialog> {

    public MessageDialog(Context context) {
        super(context);
    }

    public MessageDialog showMessageDialog(String content, AntiShakeOnClickListener listener) {
        return showMessageDialog(new SpannedString(content), listener);
    }

    public MessageDialog showMessageDialog(Spanned content, AntiShakeOnClickListener listener) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_message, null);
        TextView contentTv = view.findViewById(R.id.textView1);

        contentTv.setText(content);
        TextView leftBt = view.findViewById(R.id.btn1);

        if (listener != null)
            setLeftRightClick(listener, listener);

        if (leftClickListener != null)
            leftBt.setOnClickListener(leftClickListener);
        else if (rightClickListener != null)
            leftBt.setOnClickListener(rightClickListener);

        if (!TextUtils.isEmpty(leftStr)) {
            leftBt.setText(leftStr);
        } else if (!TextUtils.isEmpty(rightStr)) {
            leftBt.setText(rightStr);
        }

        setDialog(show());

        return this;
    }

}
