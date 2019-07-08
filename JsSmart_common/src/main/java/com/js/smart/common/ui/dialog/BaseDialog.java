package com.js.smart.common.ui.dialog;


import android.app.Dialog;
import android.content.DialogInterface;

/**
 * Created by Js on 2016/6/22.
 */
public interface BaseDialog {

    /**
     * 创建自定义dialog
     */
    Dialog show();

    /**
     * 得到选择结果
     */
    String getResult();

    String[] getResults();

    /**
     * 添加标题
     */
    DialogBuilder setTitle(String title);

    /**
     * 添加标题 按钮名字
     */
    DialogBuilder setText(String title, String leftStr, String rightStr);

    /**
     * 添加内容
     */
    DialogBuilder setContent(String content);

    /**
     * 添加点击确定监听
     */
    DialogBuilder setLeft(String str, DialogInterface.OnClickListener listener);

    /**
     * 添加点击取消监听
     */
    DialogBuilder setRight(String str, DialogInterface.OnClickListener listener);

}
