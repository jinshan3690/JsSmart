package com.js.smart.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.js.smart.common.util.DensityUtil;
import com.js.smart.ui.R;

/**
 * 带搜索条的listView
 */
public class SideBarListView extends RelativeLayout {

    private ListView listView;
    private SideBar sideBar;
    private TextView hint;
    private Context context;
    private OnTouchingLetterChangedListener onTouchingLetterChanged;
    private boolean isHint = false;

    public SideBarListView(Context context) {
        this(context, null);
    }

    public SideBarListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        listView = new ListView(context);
        listView.setDivider(null);
        listView.setVerticalScrollBarEnabled(false);
        hint = new TextView(context);
        LayoutParams hintParams = new LayoutParams(
                DensityUtil.dp2px(context, 80), DensityUtil.dp2px(context, 80));
        hintParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        hint.setLayoutParams(hintParams);
        hint.setTextColor(ContextCompat.getColor(context, R.color.white));
        hint.setTextSize(DensityUtil.sp2px(context, 25));
        hint.setGravity(Gravity.CENTER);
        hint.setVisibility(View.GONE);
//        hint.setBackgroundResource(R.drawable.btn_shape_gray);
        sideBar = new SideBar(context);
        LayoutParams sideBarParams = new LayoutParams(
                DensityUtil.dp2px(context, 30), LayoutParams.MATCH_PARENT);
        sideBarParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        sideBar.setLayoutParams(sideBarParams);
        addView(listView);
        addView(hint);
        addView(sideBar);
    }

    /**
     * 添加适配器
     */
    public void setAdapter(ListAdapter adapter) {
        listView.setAdapter(adapter);
    }

    /**
     * 点击监听
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            listener.onItemClick(parent, view, position, id);
        });
    }

    /**
     * 移动指定位置
     */
    public void setSelection(int position) {
        listView.setSelection(position);
    }

    public void setOnTouchingLetterChanged(OnTouchingLetterChangedListener onTouchingLetterChanged) {
        this.onTouchingLetterChanged = onTouchingLetterChanged;
    }

    public ListView getListView() {
        return listView;
    }

    public SideBar getSideBar() {
        return sideBar;
    }

    public TextView getHint() {
        return hint;
    }

    /**
     * 侧边条
     */
    public class SideBar extends View {
        // 触摸事件
        private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
        // 26个字母
        public String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z", "#"};
        private int choose = -1;// 选中
        private Paint paint = new Paint();

        public SideBar(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        public SideBar(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public SideBar(Context context) {
            super(context);
        }

        /**
         * 重写这个方法
         */
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            // 获取焦点改变背景颜色.
            int height = getHeight();// 获取对应高度
            int width = getWidth(); // 获取对应宽度
            int singleHeight = height / b.length;// 获取每一个字母的高度

            for (int i = 0; i < b.length; i++) {
                paint.setColor(context.getResources().getColor(R.color.textColor));
                // paint.setColor(Color.WHITE);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
                paint.setAntiAlias(true);
                paint.setTextSize(DensityUtil.sp2px(context, 40));
                // 选中的状态
                if (i == choose) {
                    paint.setColor(context.getResources().getColor(R.color.colorPrimary));
                    paint.setFakeBoldText(true);
                }
                // x坐标等于中间-字符串宽度的一半.
                float xPos = width / 2 - paint.measureText(b[i]) / 2;
                float yPos = singleHeight * i + singleHeight;
                canvas.drawText(b[i], xPos, yPos, paint);
                paint.reset();// 重置画笔
            }
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            final int action = event.getAction();
            final float y = event.getY();// 点击y坐标
            final int oldChoose = choose;
            final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
            switch (action) {
                case MotionEvent.ACTION_UP:
                    setBackgroundResource(R.color.colorTransparent);
                    choose = -1;//
                    invalidate();
                    if (hint != null) {
                        hint.setVisibility(View.GONE);
                    }
                    break;

                default:
//                    setBackgroundResource(R.drawable.btn_shape_gray);
                    if (oldChoose != c) {
                        if (c >= 0 && c < b.length) {
                            if (onTouchingLetterChanged != null) {
                                onTouchingLetterChanged.onTouchingLetterChanged(b[c]);
                            }
                            if (hint != null && isHint) {
                                hint.setText(b[c]);
                                hint.setVisibility(View.VISIBLE);
                            }
                            choose = c;
                            invalidate();
                        }
                    }
                    break;
            }
            return true;
        }

    }

    /**
     * 接口
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }

}
