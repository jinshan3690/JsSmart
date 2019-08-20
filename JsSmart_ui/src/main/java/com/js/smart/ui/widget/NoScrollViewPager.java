package com.js.smart.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.js.smart.ui.R;


public class NoScrollViewPager extends ViewPager {

    private boolean noScroll = false;
    private boolean intercept = false;
    private int lastX, lastY;
    private boolean autoHeight;
    
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NoScrollViewPager);

        autoHeight = typedArray.getBoolean(R.styleable.NoScrollViewPager_no_scroll_view_pager_auto_height, false);
    }
 
    public NoScrollViewPager(Context context) {
        this(context, null);
    }
 
    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }
 
    public boolean getNoScroll() {
    	return noScroll;
    }
    
    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = lastX - x;
                int dy = lastY - y;

                //判断是否是向上滑动和是否在第一屏
                if (Math.abs(dx) > Math.abs(dy) || intercept) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else{
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        if (noScroll)
            return false;
		else
			try {
			return super.onTouchEvent(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
            return false;
    }

	@Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
		else
			try {
				return super.onInterceptTouchEvent(arg0);
			} catch (Exception e) {
				e.printStackTrace();
			}
            return false;
    }

    public void setIntercept(boolean intercept) {
        this.intercept = intercept;
    }

    /**
     * 自适应高度
     */
    private int height = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(getCurrentItem());
        if (child != null && autoHeight) {
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            height = child.getMeasuredHeight();

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void resetHeight() {
        if(!autoHeight)
            return;

        View child = getChildAt(getCurrentItem());
        if (child != null) {
            View view = getChildAt(getCurrentItem());
            int height = view.getMeasuredHeight();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
            layoutParams.height = height;
            setLayoutParams(layoutParams);
        }
    }

}