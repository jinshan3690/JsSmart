package com.js.smart.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


import com.js.smart.ui.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CountDownView extends View {

    private Context context;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat minuteSdf = new SimpleDateFormat("mm");
    private SimpleDateFormat secondSdf = new SimpleDateFormat("ss");
    private final Paint paint = new Paint();
    private long timestamp;
    private String hour;
    private String minute;
    private String second;
    private int textColor;
    private int blockColor;
    private float paddingTop;
    private float paddingBottom;
    private float paddingLeft;
    private float paddingRight;
    private float space;
    private float round;
    private boolean hasHour;
    private boolean hasMinute;
    private boolean hasSecond;

    private Paint.FontMetrics fontMetrics;
    private float fontHeight;

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);

        hour = "0";
        minute = "0";
        second = "0";

        textColor = typedArray.getColor(R.styleable.CountDownView_cd_textColor, getResources().getColor(R.color.colorWhite));
        blockColor = typedArray.getColor(R.styleable.CountDownView_cd_blockColor, getResources().getColor(R.color.colorPrimary));
        hasHour = typedArray.getBoolean(R.styleable.CountDownView_cd_hour, true);
        hasMinute = typedArray.getBoolean(R.styleable.CountDownView_cd_minute, true);
        hasSecond = typedArray.getBoolean(R.styleable.CountDownView_cd_second, true);

        paint.setColor(textColor);
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, typedArray.getDimension(R.styleable.CountDownView_cd_textSize, 14)
                , context.getResources().getDisplayMetrics()));
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setTextAlign(Paint.Align.LEFT);

        fontMetrics = paint.getFontMetrics();
        fontHeight = Math.abs(fontMetrics.top) - Math.abs(fontMetrics.bottom);

        float padding = typedArray.getDimension(R.styleable.CountDownView_cd_padding, 0);

        paddingTop = typedArray.getDimension(R.styleable.CountDownView_cd_paddingTop, padding);
        paddingBottom = typedArray.getDimension(R.styleable.CountDownView_cd_paddingBottom, padding);
        paddingRight = typedArray.getDimension(R.styleable.CountDownView_cd_paddingRight, padding);
        paddingLeft = typedArray.getDimension(R.styleable.CountDownView_cd_paddingLeft, padding);

        space = typedArray.getDimension(R.styleable.CountDownView_cd_space, 0);
        round = typedArray.getDimension(R.styleable.CountDownView_cd_round, 0);

//        setTimestamp(System.currentTimeMillis() - (1000 * 60 * 60 * 1 + 1000 * 60 * 59 + 1000 * 58));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String[] hours = hour.split("");
        String[] minutes = minute.split("");
        String[] seconds = second.split("");
        float splitWidth = paint.measureText(":");

        int fontY = (int) (getHeight() / 2 + fontHeight / 2);//基线中间点的y轴计算公式
        int left = 0;

        if(hasHour) {
            for (int i = 1; i < hours.length; i++) {
                left = drawText(canvas, true, i == 1, i == hours.length - 1, left, hours[i]);
            }
        }

        if(hasHour && hasMinute) {
            canvas.drawText(":", space + left, fontY, paint);
            left += space + splitWidth + space;
        }

        if(hasMinute) {
            for (int i = 1; i < minutes.length; i++) {
                left = drawText(canvas, false, i == 1, i == minutes.length - 1, left, minutes[i]);
            }

        }

        if(hasMinute && hasSecond || hasHour && hasSecond) {
            canvas.drawText(":", space + left, fontY, paint);
            left += space + splitWidth + space;
        }

        if(hasSecond) {
            for (int i = 1; i < seconds.length; i++) {
                left = drawText(canvas, false, i == 1, i == seconds.length - 1, left, seconds[i]);
            }
        }

        super.onDraw(canvas);
    }

    private int drawText(Canvas canvas, boolean first, boolean start, boolean end, int left, String text) {
        float stringWidth = paint.measureText(text);

        int roundTop = (int) (getHeight() / 2 - fontHeight / 2 - paddingTop);
        int roundBottom = (int) (getHeight() / 2 + fontHeight / 2 + paddingBottom);
        int roundLeft = left;
        int roundRight = (int) (paddingLeft + paddingRight + stringWidth + left);
        if (start) {
            if (first) {
                roundLeft += getPaddingLeft();
                roundRight += getPaddingLeft();
            }
        } else if (end) {
            roundLeft += space;
            roundRight += space;
        } else {
            roundLeft += space;
            roundRight += space;
        }

        paint.setColor(blockColor);
        Rect rect = new Rect(roundLeft, roundTop, roundRight, roundBottom);
        RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF, round, round, paint);

        paint.setColor(textColor);
        float x = paddingLeft;
        int y = (int) (getHeight() / 2 + fontHeight / 2);//基线中间点的y轴计算公式
        if (start) {
            if (first) {
                x += getPaddingLeft();
            }
        } else if (end) {
            x += space;
        } else {
            x += space;
        }
        canvas.drawText(text, x + left, y, paint);

        return roundRight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        String[] hours = hour.split("");
        String[] minutes = minute.split("");
        String[] seconds = second.split("");
        int width = getPaddingLeft() + getPaddingRight();

        if(hasHour) {
            for (int i = 1; i < hours.length; i++) {
                float fontWidth = paint.measureText(hours[i]);
                if (i == 1) {

                } else if (i == hours.length - 1) {
                    width += space;
                } else {
                    width += space;
                }
                width += fontWidth + paddingLeft + paddingRight;
            }
        }

        if(hasHour && hasMinute) {
            float splitWidth = paint.measureText(":");
            width += space + splitWidth + space;
        }


        if(hasMinute) {
            for (int i = 1; i < minutes.length; i++) {
                float fontWidth = paint.measureText(minutes[i]);
                if (i == 1) {

                } else if (i == minutes.length - 1) {
                    width += space;
                } else {
                    width += space;
                }
                width += fontWidth + paddingLeft + paddingRight;
            }
        }

        if(hasMinute && hasSecond || hasHour && hasSecond) {
            float splitWidth = paint.measureText(":");
            width += space + splitWidth + space;
        }

        if(hasSecond) {
            for (int i = 1; i < seconds.length; i++) {
                float fontWidth = paint.measureText(seconds[i]);
                if (i == 1) {

                } else if (i == seconds.length - 1) {
                    width += space;
                } else {
                    width += space;
                }
                width += fontWidth + paddingLeft + paddingRight;
            }
        }

        int height = (int) (getPaddingTop() + fontHeight + paddingTop + paddingBottom + getPaddingBottom());

        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height);
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, hSpecSize);
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSpecSize, height);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                long currentTime = sdf.parse(sdf.format(new Date(System.currentTimeMillis()))).getTime();
                long diff = currentTime - timestamp;

                hour = String.format("%02d", (currentTime - timestamp) / (1000 * 60 * 60));
                if(Integer.valueOf(hour) > 99)
                    hour = "99";
                minute = minuteSdf.format(diff);
                second = secondSdf.format(diff);

                invalidate();
                requestLayout();

                if ("99".equals(hour) && "59".equals(minute) && "59".equals(second))
                    removeMessages(0);
                else
                    handler.sendEmptyMessageDelayed(0, 1000);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    public void cancel() {
        handler.removeMessages(0);
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        if (timestamp < System.currentTimeMillis()) {
            cancel();
            handler.sendEmptyMessageDelayed(0, 0);
        }
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
