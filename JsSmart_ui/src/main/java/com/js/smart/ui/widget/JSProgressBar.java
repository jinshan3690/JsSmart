package com.js.smart.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.js.smart.common.util.L;
import com.js.smart.common.util.T;

/**
 * 自定义绚丽的ProgressBar.
 */
public class JSProgressBar extends View {
    /**
     * 进度条所占用的角度
     */
    private int ARC_FULL_DEGREE = 270;
    /**
     * 弧线的宽度
     */
    private int STROKE_WIDTH;
    /**
     * 进度条最大值和当前进度值
     */
    private float max = 10000, progress;
    /**
     * 是否允许拖动进度条
     */
    private boolean draggingEnabled = true;
    /**
     * 进度条颜色
     */
    private int[] progressColors;
    /**
     * 进度条颜色发光
     */
    private boolean progressLight;
    /**
     * 进度条颜色圆角
     */
    private boolean progressRound;
    /**
     * 进度条颜色
     */
    private int[] progressBackgroundColors;
    /**
     * 进度条文字颜色
     */
    private int[] progressTextColors;
    /**
     * 进度条文字大小
     */
    private int progressTextSize;
    /**
     * 进度条提示文字
     */
    private String progressTextHint;
    /**
     * 进度条提示文字大小
     */
    private int progressTextHintSize;

    /**
     * 组件的宽，高
     */
    private int width, height;
    /**
     * 当前角度
     */
    private float lastDegree;
    /**
     * 绘制弧线的矩形区域
     */
    private RectF circleRectF;
    /**
     * 绘制弧线的画笔
     */
    private Paint progressPaint;
    /**
     * 绘制弧线背景的画笔
     */
    private Paint progressBackgroundPaint;
    /**
     * 绘制文字的画笔
     */
    private Paint textPaint;
    /**
     * 绘制当前进度值的画笔
     */
    private Paint thumbPaint;
    /**
     * 圆弧的半径
     */
    private int circleRadius;
    /**
     * 圆弧圆心位置
     */
    private int centerX, centerY;


    public JSProgressBar(Context context) {
        this(context, null);

    }


    public JSProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }


    public JSProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        lastDegree = ARC_FULL_DEGREE * (progress / max);
        ARC_FULL_DEGREE = ARC_FULL_DEGREE >= 360 ? 359 : ARC_FULL_DEGREE;
        setProgress(2000);
    }


    private void init() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(STROKE_WIDTH);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        int[] colors = {Color.parseColor("#ff0000")
                , Color.parseColor("#415382")
                , Color.parseColor("#EE6911")};

        LinearGradient gradient = new LinearGradient(STROKE_WIDTH / 2f, 0, STROKE_WIDTH / 2f, height,
                colors, new float[]{0f, ARC_FULL_DEGREE / 360f / 2, ARC_FULL_DEGREE / 360f}, Shader.TileMode.REPEAT);

        progressPaint.setShader(gradient);
        progressPaint.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.SOLID));//设置发光

        progressBackgroundPaint = new Paint();
        progressBackgroundPaint.setAntiAlias(true);
        progressBackgroundPaint.setStyle(Paint.Style.STROKE);
        progressBackgroundPaint.setStrokeWidth(STROKE_WIDTH);
        progressBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        int[] colors1 = {Color.parseColor("#40ff0000")
                , Color.parseColor("#40415382")
                , Color.parseColor("#40EE6911")};

        LinearGradient gradient1 = new LinearGradient(STROKE_WIDTH / 2f, 0, STROKE_WIDTH / 2f, height,
                colors1, new float[]{0f, ARC_FULL_DEGREE / 360f / 2, ARC_FULL_DEGREE / 360f}, Shader.TileMode.REPEAT);
        T.showSuccess("a:" + (ARC_FULL_DEGREE / 360f / 2));
        progressBackgroundPaint.setShader(gradient1);

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);

        textPaint.setShader(gradient);

        thumbPaint = new Paint();
        thumbPaint.setAntiAlias(true);


        //使用自定义字体
//        textPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fangz.ttf"));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (width == 0 || height == 0) {
            int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
            int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

            width = sizeWidth;
            height = sizeHeight;

            //计算圆弧半径和圆心点
            circleRadius = Math.min(width, height) / 2;
            STROKE_WIDTH = circleRadius / 12;
            circleRadius -= STROKE_WIDTH;

            centerX = width / 2;
            centerY = height / 2;

            //圆弧所在矩形区域
            circleRectF = new RectF();
            circleRectF.left = centerX - circleRadius;
            circleRectF.top = centerY - circleRadius;
            circleRectF.right = centerX + circleRadius;
            circleRectF.bottom = centerY + circleRadius;

            init();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private Rect textBounds = new Rect();


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float start = 90 + ((360 - ARC_FULL_DEGREE) >> 1); //进度条起始点
        float sweep1 = ARC_FULL_DEGREE * (progress / max); //进度划过的角度
        float sweep2 = ARC_FULL_DEGREE - sweep1; //剩余的角度

        //绘制进度条
        canvas.drawArc(circleRectF, start, sweep1, false, progressPaint);
        //绘制进度条背景
        canvas.drawArc(circleRectF, start + sweep1, sweep2, false, progressBackgroundPaint);

        //上一行文字
        textPaint.setTextSize(circleRadius >> 1);
        String text = (int) (100 * progress / max) + "";
        float textLen = textPaint.measureText(text);
        //计算文字高度
        textPaint.getTextBounds("8", 0, 1, textBounds);
        float h1 = textBounds.height();
        //% 前面的数字水平居中，适当调整
        float extra = text.startsWith("1") ? -textPaint.measureText("1") / 2 : 0;
        canvas.drawText(text, centerX - textLen / 2 + extra, centerY - 30 + h1 / 2, textPaint);

        //百分号
        textPaint.setTextSize(circleRadius >> 2);
        canvas.drawText("%", centerX + textLen / 2 + extra + 5, centerY - 30 + h1 / 2, textPaint);

        //下一行文字
        textPaint.setTextSize(circleRadius / 5);
        text = "可用内存充足";
        textLen = textPaint.measureText(text);
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        float h2 = textBounds.height();
        canvas.drawText(text, centerX - textLen / 2, centerY + h1 / 2 + h2, textPaint);

        //绘制进度位置，也可以直接替换成一张图片
        if(draggingEnabled) {
            float progressRadians = (float) (((360.0f - ARC_FULL_DEGREE) / 2 + sweep1) / 180 * Math.PI);
            float thumbX = centerX - circleRadius * (float) Math.sin(progressRadians);
            float thumbY = centerY + circleRadius * (float) Math.cos(progressRadians);
            thumbPaint.setColor(Color.parseColor("#33d64444"));
            canvas.drawCircle(thumbX, thumbY, STROKE_WIDTH * 2.0f, thumbPaint);
            thumbPaint.setColor(Color.parseColor("#99d64444"));
            canvas.drawCircle(thumbX, thumbY, STROKE_WIDTH * 1.4f, thumbPaint);
            thumbPaint.setColor(Color.WHITE);
            canvas.drawCircle(thumbX, thumbY, STROKE_WIDTH * 0.8f, thumbPaint);
        }
    }


    private boolean isDragging = false;


    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (!draggingEnabled) {
            return super.onTouchEvent(event);
        }


        //处理拖动事件
        float currentX = event.getX();
        float currentY = event.getY();


        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //判断是否在进度条thumb位置
                if (checkOnArc(currentX, currentY)) {
                    float newProgress = calDegreeByPosition(currentX, currentY) / ARC_FULL_DEGREE * max;
                    setProgressSync(newProgress);
                    isDragging = true;
                }
                break;


            case MotionEvent.ACTION_MOVE:
                if (isDragging) {
                    //判断拖动时是否移出去了
                    if (checkOnArc(currentX, currentY)) {
                        float currentDegree = calDegreeByPosition(currentX, currentY);

                        if (Math.abs(currentDegree - lastDegree) < 180) {
                            setProgressSync(currentDegree / ARC_FULL_DEGREE * max);
                            lastDegree = currentDegree;
                        } else {
                            L.e(""+(lastDegree / ARC_FULL_DEGREE));
                            setProgressSync(Math.round(lastDegree / ARC_FULL_DEGREE) >= 1 ? 359* max : 0);
                        }
                    } else {
//                        isDragging = false;
                    }
                }
                break;


            case MotionEvent.ACTION_UP:
                isDragging = false;
                break;
        }


        return true;
    }


    private float calDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }


    /**
     * 判断该点是否在弧线上（附近）
     */
    private boolean checkOnArc(float currentX, float currentY) {
        float distance = calDistance(currentX, currentY, centerX, centerY);
        float degree = calDegreeByPosition(currentX, currentY);
        return distance > circleRadius - STROKE_WIDTH * 5 && distance < circleRadius + STROKE_WIDTH * 5
                && (degree >= -8 && degree <= ARC_FULL_DEGREE + 8);
    }


    /**
     * 根据当前位置，计算出进度条已经转过的角度。
     */
    private float calDegreeByPosition(float currentX, float currentY) {
        float a1 = (float) (Math.atan(1.0f * (centerX - currentX) / (currentY - centerY)) / Math.PI * 180);
        if (currentY < centerY) {
            a1 += 180;
        } else if (currentY > centerY && currentX > centerX) {
            a1 += 360;
        }


        return a1 - (360 - ARC_FULL_DEGREE) / 2;
    }


    public void setMax(int max) {
        this.max = max;
        invalidate();
    }


    public void setProgress(float progress) {
        final float validProgress = checkProgress(progress);

        startAnimation(validProgress);
    }


    public void setProgressSync(float progress) {
        this.progress = checkProgress(progress);
        invalidate();
    }


    //保证progress的值位于[0,max]
    private float checkProgress(float progress) {
        if (progress < 0) {
            return 0;
        }

        return progress > max ? max : progress;
    }


    public void setDraggingEnabled(boolean draggingEnabled) {
        this.draggingEnabled = draggingEnabled;
    }

    private void startAnimation(float per) {
        float diff = per - progress;
        ValueAnimator valueAnimator = ValueAnimator
                .ofFloat(progress, progress + diff)
                .setDuration(1000);
        valueAnimator.addUpdateListener(animation -> {
            progress = (float) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.start();
    }

    public float getMax() {
        return max;
    }

    public float getProgress() {
        return progress;
    }
}