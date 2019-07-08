package com.js.smart.ui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.js.smart.common.ui.dialog.DialogBuilder;
import com.js.smart.ui.R;


/**
 * Created by Js on 2016/6/22.
 */
public class DefaultDialog extends DialogBuilder<DefaultDialog> {

    public DefaultDialog(Context context) {
        super(context);
    }

    /**
     * 默认样式
     */
    public Dialog showDefaultDialog(String content, DialogInterface.OnDismissListener listener) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_default, null);
        ImageView titleIco = view.findViewById(R.id.imageView1);
        TextView contentTv = view.findViewById(R.id.textView2);
        view.findViewById(R.id.layout1).setOnClickListener(v -> {
            dismiss();
        });

        contentTv.setText(content);
        Dialog dialog = show();
        dialog.setOnDismissListener(listener);
        return dialog;
    }

}
