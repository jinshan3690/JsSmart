package com.js.smart.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by baoyunlong on 16/6/8.
 */
public class SimpleScrollView extends ScrollView{

    private static String TAG= SimpleScrollView.class.getName();
    private boolean isFling = true;

    public void setScrollListener(ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    private ScrollListener mScrollListener;

    public SimpleScrollView(Context context) {
        this(context, null);
    }

    public SimpleScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFillViewport(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:

                if(mScrollListener!=null){
                    int contentHeight=getChildAt(0).getHeight();
                    int scrollHeight=getHeight();
//                    Log.d(TAG,"scrollY:"+getScrollY()+"contentHeight:"+contentHeight+" scrollHeight"+scrollHeight+"object:"+this);

                    int scrollY=getScrollY();
                    mScrollListener.onScroll(scrollY);

                    if(scrollY+scrollHeight>=contentHeight||contentHeight<=scrollHeight){
                        mScrollListener.onScrollToBottom();
                    }else {
                        mScrollListener.notBottom();
                    }

                    if(scrollY==0){
                        mScrollListener.onScrollToTop();
                    }

                }

                break;
        }
        boolean result=super.onTouchEvent(ev);
        requestDisallowInterceptTouchEvent(false);

        return result;
    }

    @Override
    public void fling(int velocity) {
        if (!isFling) {
            super.fling(velocity / 1000);
        }else{
            super.fling(velocity);
        }
    }

    public void setFling(boolean fling) {
        isFling = fling;
    }

    public interface ScrollListener{
        void onScrollToBottom();
        void onScrollToTop();
        void onScroll(int scrollY);
        void notBottom();
    }
}
