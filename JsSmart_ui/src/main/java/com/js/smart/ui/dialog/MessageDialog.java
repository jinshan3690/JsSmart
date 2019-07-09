package com.js.smart.ui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.js.smart.common.ui.dialog.DialogBuilder;
import com.js.smart.ui.R;


/**
 * Created by Js on 2016/6/22.
 */
public class MessageDialog extends DialogBuilder<MessageDialog> {

    public MessageDialog(Context context) {
        super(context);
    }

    public MessageDialog showMessageDialog(String content, View.OnClickListener listener) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_message, null);
        TextView contentTv = view.findViewById(R.id.textView1);

        contentTv.setText(content);
        TextView leftBt = view.findViewById(R.id.btn1);
        leftBt.setOnClickListener(v -> {
            listener.onClick(v);
            dismiss();
        });
        if (!TextUtils.isEmpty(leftStr)) {
            leftBt.setText(leftStr);
        }

        setDialog(show());

        return this;
    }

}
