package com.js.smart.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.js.smart.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 流动布局
 */
public class MultiImageView extends ViewGroup {

    private int lineCount;
    private int imagePadding;
    private int ico;
    private int count;
    private int maxCount;

    private int width;

    private OnMultiItemImageView onMultiItemImageView;

    public MultiImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public MultiImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiImageView(Context context) {
        this(context, null);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MultiImageView);
        lineCount = typedArray.getInt(R.styleable.MultiImageView_miv_lineCount, 0);
        ico = typedArray.getResourceId(R.styleable.MultiImageView_miv_ico, 0);
        imagePadding = (int) typedArray.getDimension(R.styleable.MultiImageView_miv_padding, 0);
        count = typedArray.getInt(R.styleable.MultiImageView_miv_count, 0);
        maxCount = typedArray.getInt(R.styleable.MultiImageView_miv_maxCount, -1);

        initItem();
    }

    private void initItem() {
        for (int i = 0; i < count; i++) {
            if (maxCount == -1 || maxCount > i) {
                addView(createImage(i));
            }
        }
    }

    public ImageView addImageView() {
        if (maxCount == -1 || maxCount > getChildCount()) {
            ImageView imageView = createImage(getChildCount());
            addView(imageView);
            return imageView;
        }
        return null;
    }

    private ImageView createImage(int position) {
        ImageView imageView = new ImageView(getContext());
        imageView.setTag(R.id.imageView1, position);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (Build.VERSION.SDK_INT >= 21) {
            imageView.setTransitionName("shareView1");
        }
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMultiItemImageView != null && isEnabled())
                    onMultiItemImageView.onMultiItem(MultiImageView.this, v, (Integer) v.getTag(R.id.imageView1));
            }
        });
        imageView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        imageView.setImageResource(ico);
        return imageView;
    }

    public ImageView getImageView(int position) {
        return (ImageView) getChildAt(position);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        int cCount = getChildCount();
        boolean isSingle = false;
        boolean isNewLine = false;

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            lp.width = (sizeWidth - imagePadding * (lineCount - 1) - getPaddingLeft() - getPaddingRight()) / lineCount;
            lp.height = (sizeWidth - imagePadding * (lineCount - 1) - getPaddingLeft() - getPaddingRight()) / lineCount;

            int childWidth = lp.width + lp.leftMargin
                    + lp.rightMargin;
            int childHeight = lp.height + lp.topMargin
                    + lp.bottomMargin;
            this.width = lp.width;

            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;

                if (!isNewLine) {
                    height += imagePadding;
                    isNewLine = true;
                }
                isSingle = true;
                lp.topMargin = imagePadding;
            } else {
                if (isSingle) {
                    lp.topMargin = imagePadding;
                }
                if (isNewLine) {
                    height -= imagePadding;
                    isNewLine = false;
                }
                if (i != 0) {
                    if (lp.leftMargin == 0)
                        width += imagePadding;
                    lp.leftMargin = imagePadding;
                }
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );

    }

    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<>();

        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);

                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                lineViews = new ArrayList<>();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);

        }
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        int left = getPaddingLeft();
        int top = getPaddingTop();

        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin
                        + lp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public interface OnMultiItemImageView {
        void onMultiItem(View parent, View v, int position);
    }

    public void setOnMultiItemImageView(OnMultiItemImageView onMultiItemImageView) {
        this.onMultiItemImageView = onMultiItemImageView;
    }

    public void setCount(int count) {
        removeAllViews();
        this.count = count;
        initItem();
    }
}
