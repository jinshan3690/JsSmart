package com.js.smart.common.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.js.smart.common.R;
import com.js.smart.common.util.AntiShakeUtils;


/**
 * Created by Js on 2016/6/22.
 */
public class DialogBuilder<T extends DialogBuilder> implements BaseDialog {

    protected Context context;
    protected String title;
    protected String content;
    protected String leftStr;
    protected String rightStr;
    protected DialogInterface.OnClickListener onPositiveClickListener;
    protected DialogInterface.OnClickListener onNegativeClickListener;
    protected View view;
    protected String result;
    protected String[] results;
    protected Dialog dialog;


    public DialogBuilder(Context context) {
        this.context = context;
    }

    /**
     * 构造对象
     */
    private static DialogBuilder build(Context context) {
        return new DialogBuilder(context);
    }

    private static DialogBuilder build(Context context, String title, String content) {
        return new DialogBuilder(context).setTitle(title).setContent(content);
    }

    private static DialogBuilder build(Context context, String title) {
        return build(context, title, null);
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public String[] getResults() {
        return results;
    }

    @Override
    public T setTitle(String title) {
        this.title = title;
        return (T) this;
    }

    @Override
    public T setText(String title, String leftStr, String rightStr) {
        this.title = title;
        this.leftStr = leftStr;
        this.rightStr = rightStr;
        return (T) this;
    }

    @Override
    public T setContent(String content) {
        this.content = content;
        return (T) this;
    }

    @Override
    public T setLeft(String str, DialogInterface.OnClickListener listener) {
        this.leftStr = str;
        this.onPositiveClickListener = listener;
        return (T) this;
    }

    @Override
    public T setRight(String str, DialogInterface.OnClickListener listener) {
        this.rightStr = str;
        this.onNegativeClickListener = listener;
        return (T) this;
    }

    /**
     * 创建自定义dialog
     */
    @Override
    public Dialog show() {
        if(context instanceof Activity && AntiShakeUtils.isInvalidClick(((Activity)context).getWindow().getDecorView()))
            return dialog;
        dialog = new Dialog(context, R.style.CustomDialogTransparent);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    /**
     * 创建系统默认dialog
     */
    public AlertDialog showDefault() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.create();
        builder.setTitle(title);
        builder.setMessage(content);
        if (view != null)
            builder.setView(view);
        builder.setPositiveButton(leftStr, onPositiveClickListener);
        if (onNegativeClickListener != null)
            builder.setNegativeButton(rightStr, onNegativeClickListener);
        AlertDialog dialog = builder.show();
        return dialog;
    }

    public Dialog setDialog(Dialog dialog) {
        this.dialog = dialog;
        return dialog;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public Dialog dismiss() {
        if (dialog != null)
            dialog.dismiss();
        return dialog;
    }
}
