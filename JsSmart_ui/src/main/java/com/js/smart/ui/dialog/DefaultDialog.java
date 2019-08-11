package com.js.smart.ui.dialog;


import android.content.Context;
import android.os.Handler;
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
        return showDefaultDialog(new SpannedString(content),0, listener);
    }

    public DefaultDialog showDefaultDialog(String content, int delayed, AntiShakeOnClickListener listener) {
        return showDefaultDialog(new SpannedString(content), delayed, listener);
    }

    /**
     * 默认样式
     */
    public DefaultDialog showDefaultDialog(Spanned content,int delayed,  AntiShakeOnClickListener listener) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_default, null);
        ImageView titleIco = view.findViewById(R.id.imageView1);
        TextView contentTv = view.findViewById(R.id.textView1);
        view.findViewById(R.id.layout1).setOnClickListener(new AntiShakeOnClickListener() {
            @Override
            protected void antiShakeOnClick(View v) {
                if(leftClickListener != null)
                    dismiss();
                else if(rightClickListener != null)
                    dismiss();
            }
        });

        if(listener != null)
            setLeftRightClick(listener, listener);

        contentTv.setText(content);

        setDialog(show());

        dialog.setOnDismissListener(dialog -> {
            if(leftClickListener != null)
                leftClickListener.onClick(view);
            else if(rightClickListener != null)
                rightClickListener.onClick(view);
        });

        if (delayed != 0)
            new Handler().postDelayed(() -> dismiss(),delayed);

        return this;
    }

}
