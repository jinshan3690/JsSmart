package com.js.smart.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.js.smart.ui.R;


public class Indicator extends LinearLayout {

    private int count = 1;
    private Paint paint;
    private int radius;
    private int checkBackground = 0x60ffffff;
    private int defaultBackground = 0x30ffffff;
    private int position = 0;

    public Indicator(Context context) {
        this(context,null);
    }

    public Indicator(Context context, AttributeSet attrs) {
        super(context, null);
        init();
    }

    private void init() {
        radius = dp2px(3);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(radius * 2, radius * 2);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(0, 0, 0, 50);
        setLayoutParams(params);
        setBackgroundResource(R.color.colorTransparent);

        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int offset = radius;
        if (count == 0) {
            paint.setColor(checkBackground);
            canvas.drawCircle(radius , radius , radius, paint);
            return;
        }

        for (int i = 0; i < count; i++) {
            paint.setColor(defaultBackground);
            if (position == i) {
                paint.setColor(checkBackground);
            }
            canvas.drawCircle(offset , radius, radius, paint);
            offset += radius*4;
        }
    }

    public void setPager(int count) {
        this.count = count;
        //圆点10px
        if (count == 0) {
            getLayoutParams().width = radius * 2;
        } else {
            getLayoutParams().width = count * radius * 2 + (count - 1) * radius * 2;
        }
        invalidate();
    }

    public void setPosition(int position) {
        this.position = position;
        invalidate();
    }

    /**
     * dp转px
     */
    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getContext().getResources().getDisplayMetrics());
    }

}
