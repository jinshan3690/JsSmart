package com.js.smart.ui.dialog;


import android.content.Context;
import android.text.Spanned;
import android.text.SpannedString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.js.smart.common.app.AntiShakeOnClickListener;
import com.js.smart.common.ui.dialog.DialogBuilder;
import com.js.smart.ui.R;


/**
 * Created by Js on 2016/6/22.
 */
public class DefaultDialog extends DialogBuilder<DefaultDialog> {

    public DefaultDialog(Context context) {
        super(context);
    }

    public DefaultDialog showDefaultDialog(String content, AntiShakeOnClickListener listener) {
        return showDefaultDialog(new SpannedString(content), listener);
    }

    /**
     * 默认样式
     */
    public DefaultDialog showDefaultDialog(Spanned content, AntiShakeOnClickListener listener) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_default, null);
        ImageView titleIco = view.findViewById(R.id.imageView1);
        TextView contentTv = view.findViewById(R.id.textView1);
        view.findViewById(R.id.layout1).setOnClickListener(new AntiShakeOnClickListener() {
            @Override
            protected void antiShakeOnClick(View v) {
                if(leftClickListener != null)
                    leftClickListener.onClick(view);
                if(rightClickListener != null)
                    rightClickListener.onClick(view);
            }
        });

        contentTv.setText(content);
        setDialog(show());
        if(listener != null)
            setLeftRightClick(listener, listener);

        dialog.setOnDismissListener(dialog -> {
            if(leftClickListener != null)
                leftClickListener.onClick(view);
            if(rightClickListener != null)
                rightClickListener.onClick(view);
        });
        return this;
    }

}
