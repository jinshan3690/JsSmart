package com.js.smart.ui.app;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.js.smart.ui.R;
import com.js.smart.ui.widget.CircleImageView;
import com.js.smart.common.app.BaseCompatActivity;


public class TitleUtil implements View.OnClickListener {

    private BaseCompatActivity context;
    private View view;
    private View layout;
    private View right;
    private View left;
    private TextView titleTv;
    private ImageView titleIv;
    private CircleImageView leftHeadIv;
    private ImageView leftIv;
    private ImageView rightIv;
    private TextView rightTv;
    private TextView leftTv;

    private TitleUtil(BaseCompatActivity context, View view) {
        this.context = context;
        this.view = view;
        init();
    }

    public static TitleUtil build(BaseCompatActivity context) {
        return new TitleUtil(context, null);
    }

    public static TitleUtil build(BaseCompatActivity context, View view) {
        return new TitleUtil(context, view);
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
                right = layout.findViewById(R.id.titleRight);
                left = layout.findViewById(R.id.titleLeft);
                titleTv = layout.findViewById(R.id.titleText);
                titleIv = layout.findViewById(R.id.titleImg);
                leftIv = layout.findViewById(R.id.titleLeftImage);
                leftHeadIv = layout.findViewById(R.id.titleLeftHeadImage);
                rightIv = layout.findViewById(R.id.titleRightImage);
                rightTv = layout.findViewById(R.id.titleRightText);
                leftTv = layout.findViewById(R.id.titleLeftText);
                if (right != null && left != null) {
                    leftHeadIv.setVisibility(View.INVISIBLE);
                    leftIv.setVisibility(View.INVISIBLE);
                    leftTv.setVisibility(View.INVISIBLE);
                    rightTv.setVisibility(View.INVISIBLE);
                }
            }
        } catch (NoSuchFieldError e) {

        }
    }

    /**
     * 设置标题
     */
    public TitleUtil setTitle(String text) {
        titleTv.setText(text);
        titleTv.setVisibility(View.VISIBLE);
        titleIv.setVisibility(View.GONE);
        return this;
    }

    public TitleUtil setTitleSrc(Drawable drawable) {
        titleIv.setImageDrawable(drawable);
        titleIv.setVisibility(View.VISIBLE);
        titleTv.setVisibility(View.GONE);
        return this;
    }

    /**
     * 设置left图片
     */
    public TitleUtil setLeftSrc(Drawable drawable) {
        leftIv.setImageDrawable(drawable);
        leftTv.setVisibility(View.GONE);
        left.setVisibility(View.VISIBLE);
        return this;
    }

    public TitleUtil setLeftSrc(int id) {
        leftIv.setImageResource(id);
        leftTv.setVisibility(View.GONE);
        left.setVisibility(View.VISIBLE);
        return this;
    }

    public TitleUtil setLeftHeadSrc(int id) {
        leftHeadIv.setImageResource(id);
        leftTv.setVisibility(View.GONE);
        leftHeadIv.setVisibility(View.VISIBLE);
        left.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 设置right文字
     */
    public TitleUtil setRightText(String text) {
        rightTv.setText(text);
        rightTv.setVisibility(View.VISIBLE);
        rightIv.setVisibility(View.GONE);
        return this;
    }

    /**
     * 设置left文字
     */
    public TitleUtil setLeftText(String text) {
        leftTv.setText(text);
        leftTv.setVisibility(View.VISIBLE);
        leftIv.setVisibility(View.GONE);
        return this;
    }

    /**
     * 设置right图片
     */
    public TitleUtil setRightSrc(Drawable drawable) {
        rightIv.setImageDrawable(drawable);
        rightTv.setVisibility(View.GONE);
        right.setVisibility(View.VISIBLE);
        return this;
    }

    public TitleUtil setRightSrc(int id) {
        rightIv.setImageResource(id);
        rightTv.setVisibility(View.GONE);
        right.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 设置监听
     */
    public TitleUtil setLeftOnRightClick(View.OnClickListener listener) {
        right.setOnClickListener(listener);
        left.setOnClickListener(listener);
        rightTv.setVisibility(View.VISIBLE);
        leftTv.setVisibility(View.VISIBLE);
        return this;
    }

    public TitleUtil setRightClick(View.OnClickListener listener) {
        right.setOnClickListener(listener);
        return this;
    }

    public TitleUtil setLeftClick(View.OnClickListener listener) {
        left.setOnClickListener(listener);
        return this;
    }

    public TitleUtil setDefaultLeftClick() {
        left.setOnClickListener(this);
        leftIv.setVisibility(View.VISIBLE);
        return this;
    }


    /**
     * title背景颜色
     */
    public TitleUtil setBackgroundResource(int res) {
        layout.setBackgroundResource(res);
        return this;
    }

    /**
     * title字体颜色
     */
    public TitleUtil setTextColorResource(int color) {
        titleTv.setTextColor(ContextCompat.getColor(context, color));
        rightTv.setTextColor(ContextCompat.getColor(context, color));
        leftTv.setTextColor(ContextCompat.getColor(context, color));
        return this;
    }

    public View getLayout() {
        return layout;
    }

    public View getRight() {
        return right;
    }

    public View getLeft() {
        return left;
    }

    public TextView getTitle() {
        return titleTv;
    }

    public ImageView getLeftIv() {
        return leftIv;
    }

    public ImageView getLeftHead() {
        return leftHeadIv;
    }

    public TextView getLeftTv() {
        return leftTv;
    }

    public TextView getRightTv() {
        return rightTv;
    }

    @Override
    public void onClick(View v) {
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
