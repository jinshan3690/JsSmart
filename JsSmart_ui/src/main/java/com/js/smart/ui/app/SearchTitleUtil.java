package com.js.smart.ui.app;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.js.smart.common.app.AntiShakeOnClickListener;
import com.js.smart.common.app.BaseCompatActivity;
import com.js.smart.ui.R;
import com.js.smart.ui.widget.CancelEditText;


public class SearchTitleUtil extends AntiShakeOnClickListener {

    private BaseCompatActivity context;
    private View view;
    private View layout;
    private View titleLeft;
    private View titleRight;
    private CancelEditText titleEt;
    private ImageView leftIv;
    private TextView rightTv;

    private SearchTitleUtil(BaseCompatActivity context, View view) {
        this.context = context;
        this.view = view;
        init();
        setDefaultLeftClick();
    }

    public static SearchTitleUtil build(BaseCompatActivity context) {
        return new SearchTitleUtil(context, null);
    }

    public static SearchTitleUtil build(BaseCompatActivity context, View view) {
        return new SearchTitleUtil(context, view);
    }

    /**
     * 初始化组件
     */
    private void init() {
        if (view != null)
            layout = view.findViewById(R.id.title);
        else
            layout = context.findViewById(R.id.title);
        try {
            if (layout != null) {
                titleLeft = layout.findViewById(R.id.titleLeft);
                titleRight = layout.findViewById(R.id.titleRight);
                titleEt = layout.findViewById(R.id.titleEdit);
                leftIv = layout.findViewById(R.id.titleLeftImage);
                rightTv = layout.findViewById(R.id.titleRightText);
            }
        } catch (NoSuchFieldError e) {

        }
    }

    /**
     * 设置标题
     */
    public SearchTitleUtil setTitle(String text) {
        titleEt.setText(text);
        return this;
    }

    /**
     * 设置right文字
     */
    public SearchTitleUtil setRightText(String text) {
        rightTv.setText(text);
        rightTv.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 设置监听
     */
    public SearchTitleUtil setLeftOnRightClick(AntiShakeOnClickListener listener) {
        titleRight.setOnClickListener(listener);
        titleLeft.setOnClickListener(listener);
        return this;
    }

    public SearchTitleUtil setRightClick(AntiShakeOnClickListener listener) {
        titleRight.setOnClickListener(listener);
        return this;
    }

    public SearchTitleUtil setLeftClick(AntiShakeOnClickListener listener) {
        titleLeft.setOnClickListener(listener);
        return this;
    }

    public SearchTitleUtil setDefaultLeftClick() {
        titleLeft.setOnClickListener(this);
        return this;
    }


    /**
     * title背景颜色
     */
    public SearchTitleUtil setBackgroundResource(int res) {
        layout.setBackgroundResource(res);
        return this;
    }

    /**
     * title字体颜色
     */
    public SearchTitleUtil setTextColorResource(int color) {
        titleEt.getEditText().setTextColor(ContextCompat.getColor(context, color));
        rightTv.setTextColor(ContextCompat.getColor(context, color));
        return this;
    }

    public View getLayout() {
        return layout;
    }

    public CancelEditText getTitleEt() {
        return titleEt;
    }

    public ImageView getLeftIv() {
        return leftIv;
    }

    public TextView getRightTv() {
        return rightTv;
    }

    @Override
    protected void antiShakeOnClick(View v) {
        int i = v.getId();
        if (i == R.id.titleLeft) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.finishAfterTransition();
            } else {
                context.finish();
            }
        }
    }
}
