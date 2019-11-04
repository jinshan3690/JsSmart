package com.js.smart.ui.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.js.smart.common.util.DensityUtil;
import com.js.smart.common.util.L;
import com.js.smart.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 7/1/14.
 */
public class WheelView extends ScrollView {

    private float textSize;
    private int textPadding;
    private int minDivider;
    private int maxWidth;
    private int textColor;
    private int borderColor;

    public static class OnWheelViewListener {
        public void onSelected(int selectedIndex, String item) {
        }
    }

    private Context context;
//    private ScrollView scrollView;

    private LinearLayout views;

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WheelView);

        textSize = DensityUtil.px2sp(context,
                typedArray.getDimension(R.styleable.WheelView_wheel_text_size, DensityUtil.sp2px(context, 20)));
        textPadding = (int) typedArray.getDimension(R.styleable.WheelView_wheel_text_padding, DensityUtil.dp2px(context, 15));
        minDivider = (int) typedArray.getDimension(R.styleable.WheelView_wheel_min_divider_width, DensityUtil.dp2px(context, 70));
        maxWidth = (int) typedArray.getDimension(R.styleable.WheelView_wheel_max_width, DensityUtil.dp2px(context, 0));
        textColor = typedArray.getResourceId(R.styleable.WheelView_wheel_text_color, R.color.colorPrimary);
        borderColor = typedArray.getResourceId(R.styleable.WheelView_wheel_border_color, R.color.colorPrimary);

        init(context);
    }

    //    String[] items;
    private List<String> items;

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> list) {
        if (null == items) {
            items = new ArrayList<String>();
        }
        views.removeAllViews();
        items.clear();
        items.addAll(list);

        // 前面和后面补全
        for (int i = 0; i < offset; i++) {
            items.add(0, "");
            items.add("");
        }

        initData();

    }


    public static final int OFF_SET_DEFAULT = 1;
    int offset = OFF_SET_DEFAULT; // 偏移量（需要在最前面和最后面补全）

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        selectedIndex = selectedIndex + offset - this.offset;
        this.offset = offset;
    }

    int displayItemCount; // 每页显示的数量

    int selectedIndex = offset;


    private void init(Context context) {
        this.context = context;

        L.d("parent: " + this.getParent());
//        this.setOrientation(VERTICAL);
        this.setVerticalScrollBarEnabled(false);

        views = new LinearLayout(context);
        views.setOrientation(LinearLayout.VERTICAL);
        this.addView(views);

        scrollerTask = new Runnable() {

            public void run() {

                int newY = getScrollY();
                if (initialY - newY == 0) { // stopped
                    final int remainder = initialY % itemHeight;
                    final int divided = initialY / itemHeight;
                    if (remainder == 0) {
                        selectedIndex = divided + offset;

                        onSelectedCallBack();
                    } else {
                        if (remainder > itemHeight / 2) {
                            WheelView.this.post(new Runnable() {
                                @Override
                                public void run() {
                                    WheelView.this.smoothScrollTo(0, initialY - remainder + itemHeight);
                                    selectedIndex = divided + offset + 1;
                                    onSelectedCallBack();
                                }
                            });
                        } else {
                            WheelView.this.post(new Runnable() {
                                @Override
                                public void run() {
                                    WheelView.this.smoothScrollTo(0, initialY - remainder);
                                    selectedIndex = divided + offset;
                                    onSelectedCallBack();
                                }
                            });
                        }
                    }

                } else {
                    initialY = getScrollY();
                    WheelView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };


    }

    int initialY;

    Runnable scrollerTask;
    int newCheck = 50;

    public void startScrollerTask() {
        removeCallbacks(scrollerTask);
        initialY = getScrollY();
        this.postDelayed(scrollerTask, newCheck);
    }

    private void initData() {
        displayItemCount = offset * 2 + 1;

        for (int i = 0; i < items.size(); i++) {
            views.addView(createView(i, items.get(i)));
        }

        refreshItemView((selectedIndex - offset) * itemHeight);
    }

    int itemHeight = 0;

    private TextView createView(int index, String item) {
        TextView tv = new TextView(context);
        tv.setTag(index - offset);
        if (index - offset >= 0 && index - offset < items.size() - getOffset() * 2)
            tv.setOnClickListener(v -> {
                setSelection((Integer) v.getTag(), false);
                onSelectedCallBack();
            });
        tv.setSingleLine(true);
        tv.setTextSize(textSize);
        tv.setText(item);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int padding = textPadding;
        tv.setPadding(0, padding, 0, padding);
        if (0 == itemHeight) {
            itemHeight = getViewMeasuredHeight(tv);
            views.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * displayItemCount));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.getLayoutParams();
            lp.height = itemHeight * displayItemCount;
        }
        return tv;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        refreshItemView(t);

        if (t > oldt) {
            scrollDirection = SCROLL_DIRECTION_DOWN;
        } else {
            scrollDirection = SCROLL_DIRECTION_UP;

        }
    }

    private void refreshItemView(int y) {
        int position = y / itemHeight + offset;
        int remainder = y % itemHeight;
        int divided = y / itemHeight;

        if (remainder == 0) {
            position = divided + offset;
        } else {
            if (remainder > itemHeight / 2) {
                position = divided + offset + 1;
            }
        }

        int childSize = views.getChildCount();
        for (int i = 0; i < childSize; i++) {
            TextView itemView = (TextView) views.getChildAt(i);
            if (null == itemView) {
                return;
            }
            if (position == i) {
                itemView.setTextColor(context.getResources().getColor(textColor));
            } else {
                itemView.setTextColor(context.getResources().getColor(R.color.textColorTint));
            }
        }
    }

    /**
     * 获取选中区域的边界
     */
    int[] selectedAreaBorder;

    private int[] obtainSelectedAreaBorder() {
        if (null == selectedAreaBorder) {
            selectedAreaBorder = new int[2];
            selectedAreaBorder[0] = itemHeight * offset;
            selectedAreaBorder[1] = itemHeight * (offset + 1);
        }
        return selectedAreaBorder;
    }


    private int scrollDirection = -1;
    private static final int SCROLL_DIRECTION_UP = 0;
    private static final int SCROLL_DIRECTION_DOWN = 1;

    Paint paint;
    int viewWidth;
    int dividerWidth = 0;

    @Override
    public void setBackgroundDrawable(Drawable background) {
        if (minDivider != 0) {
            dividerWidth = minDivider;
        } else {
            dividerWidth = viewWidth * 4 / 6;
        }

        if (null == paint) {
            paint = new Paint();
            paint.setColor(context.getResources().getColor(borderColor));
            paint.setStrokeWidth(dip2px(1f));
        }

        background = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                float offset = 0;
                if (viewWidth > dividerWidth)
                    offset = (viewWidth - dividerWidth) / 2;
                canvas.drawLine(offset, obtainSelectedAreaBorder()[0], viewWidth - offset, obtainSelectedAreaBorder()[0], paint);
                canvas.drawLine(offset, obtainSelectedAreaBorder()[1], viewWidth - offset, obtainSelectedAreaBorder()[1], paint);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter cf) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.UNKNOWN;
            }
        };


        super.setBackgroundDrawable(background);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.getLayoutParams();
        if ((maxWidth != 0 && w > maxWidth) || w < minDivider) {
            w = maxWidth > minDivider ? maxWidth : minDivider;
            lp.weight = 0;
            lp.width = w;
        }
        viewWidth = w;
        super.onSizeChanged(w, h, oldw, oldh);
        L.e("w: " + w + ", h: " + h + ", oldw: " + oldw + ", oldh: " + oldh);
        setBackgroundDrawable(null);
    }

    /**
     * 选中回调
     */
    private void onSelectedCallBack() {
        if (null != onWheelViewListener) {
            onWheelViewListener.onSelected(selectedIndex - offset, items.get(selectedIndex));
        }

    }

    public void setSelection(int position) {
        setSelection(position, true);
    }

    public void setSelection(int position, boolean hasSmooth) {
        if (position < 0)
            position = 0;
        else if (position > items.size() - 1 - getOffset() * 2) {
            position = items.size() - 1 - getOffset() * 2;
        }
        final int p = position;
        selectedIndex = p + offset;
        if (hasSmooth)
            this.post(() -> WheelView.this.smoothScrollTo(0, p * itemHeight));
        else
            this.post(() -> WheelView.this.scrollTo(0, p * itemHeight));

    }

    public String getSelectedItem() {
        return items.get(selectedIndex);
    }

    public int getSeletedIndex() {
        return selectedIndex - offset;
    }


    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            startScrollerTask();
        }
        return super.onTouchEvent(ev);
    }

    private OnWheelViewListener onWheelViewListener;

    public OnWheelViewListener getOnWheelViewListener() {
        return onWheelViewListener;
    }

    public void setOnWheelViewListener(OnWheelViewListener onWheelViewListener) {
        this.onWheelViewListener = onWheelViewListener;
    }

    private int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int getViewMeasuredHeight(View view) {
        int width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
        return view.getMeasuredHeight();
    }

    public void setMinDivider(int minDivider) {
        this.minDivider = minDivider;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }
}
