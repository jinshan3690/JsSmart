package com.js.smart.common.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.js.smart.common.R;


/**
 * Created by Js on 2016/6/22.
 */
public class DialogBuilder<T extends DialogBuilder> implements BaseDialog {

    protected Context context;
    protected String title;
    protected String content;
    protected String leftStr;
    protected String rightStr;
    protected View.OnClickListener leftClickListener;
    protected View.OnClickListener rightClickListener;
    protected DialogInterface.OnShowListener showListener;
    protected View view;
    protected String result;
    protected String[] results;
    protected Dialog dialog;
    protected int theme = R.style.CustomDialogTransparent;


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
    public T setLeftText(String leftStr) {
        this.leftStr = leftStr;
        return (T) this;
    }

    @Override
    public T setRightText(String rightStr) {
        this.rightStr = rightStr;
        return (T) this;
    }

    @Override
    public T setLeftOnClickListener(View.OnClickListener leftClickListener) {
        this.leftClickListener = leftClickListener;
        return (T) this;
    }

    @Override
    public T setRightOnClickListener(View.OnClickListener rightListener) {
        this.rightClickListener = rightListener;
        return (T) this;
    }

    public T setLeft(String leftStr, View.OnClickListener leftClickListener) {
        setLeftText(leftStr);
        setLeftOnClickListener(leftClickListener);
        return (T) this;
    }

    public T setRight(String rightStr, View.OnClickListener rightClickListener) {
        setRightText(rightStr);
        setLeftOnClickListener(rightClickListener);
        return (T) this;
    }

    public T setLeftRightClick(View.OnClickListener leftClickListener, View.OnClickListener rightClickListener) {
        setLeftOnClickListener(leftClickListener);
        setRightOnClickListener(rightClickListener);
        return (T) this;
    }

    public T setTheme(int theme){
        this.theme = theme;
        return (T) this;
    }

    /**
     * 创建自定义dialog
     */
    @Override
    public Dialog show() {
        dialog = new Dialog(context, theme);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnShowListener(dialog -> {
            if(showListener != null)
                showListener.onShow(dialog);
        });
        dialog.setOnDismissListener(d -> {
            dialog.setOnShowListener(null);
            dialog.setOnCancelListener(null);
            dialog.setOnDismissListener(null);
        });
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
        builder.setPositiveButton(leftStr, (dialog, which) -> {
            if(leftClickListener != null)
                leftClickListener.onClick(null);
        });
        if (rightClickListener != null)
            builder.setNegativeButton(rightStr, (dialog, which) -> rightClickListener.onClick(null));
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

    public View getView() {
        return view;
    }

    public void setShowListener(DialogInterface.OnShowListener showListener) {
        this.showListener = showListener;
    }
}
