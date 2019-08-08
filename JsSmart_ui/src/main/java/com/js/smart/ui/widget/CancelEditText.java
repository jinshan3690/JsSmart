package com.js.smart.ui.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.TextKeyListener;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.js.smart.common.util.DensityUtil;
import com.js.smart.common.util.T;
import com.js.smart.ui.R;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class CancelEditText extends LinearLayout implements OnFocusChangeListener, TextWatcher, OnClickListener, TextView.OnEditorActionListener {

    private TextView labelText;
    private CancelIco cancelBtn;
    private CancelDown downBtn;
    private CheckBox switchBtn;
    private CancelEdit editText;
    private CancelEditTextChangedListener cancelEditTextListener;
    private FuzzyQueryListener fuzzyQueryListener;
    private SearchListener searchListener;
    private String historyName = "SearchHistory";
    private String historyKey = "SearchHistoryKey";
    private int length = -1;
    private int selectBackground;
    private int background;
    private int cancelIco;
    private int downIco;
    private int switchIco;
    private int cancelIcoWidth;
    private int cancelIcoHeight;
    private int downIcoWidth;
    private int downIcoHeight;
    private int switchIcoWidth;
    private int switchIcoHeight;
    private SwitchChangeListener switchChangeListener;


    public CancelEditText(Context context) {
        this(context, null);
    }

    public CancelEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CancelEditText);

        cancelIcoWidth = (int) typedArray.getDimension(R.styleable.CancelEditText_cet_cancelIcoWidth, DensityUtil.dp2px(context, 15));
        cancelIcoHeight = (int) typedArray.getDimension(R.styleable.CancelEditText_cet_cancelIcoHeight, DensityUtil.dp2px(context, 15));
        downIcoWidth = (int) typedArray.getDimension(R.styleable.CancelEditText_cet_downIcoWidth, LayoutParams.WRAP_CONTENT);
        downIcoHeight = (int) typedArray.getDimension(R.styleable.CancelEditText_cet_downIcoHeight, DensityUtil.dp2px(context, 15));
        switchIcoWidth = (int) typedArray.getDimension(R.styleable.CancelEditText_cet_switchIcoWidth, LayoutParams.WRAP_CONTENT);
        switchIcoHeight = (int) typedArray.getDimension(R.styleable.CancelEditText_cet_switchIcoHeight, DensityUtil.dp2px(context, 22));

        initView(context);

        length = typedArray.getInt(R.styleable.CancelEditText_cet_length, -1);
        background = typedArray.getResourceId(R.styleable.CancelEditText_cet_background, -1);
        if (background != -1)
            setBackgroundResource(background);
        selectBackground = typedArray.getResourceId(R.styleable.CancelEditText_cet_selectBackground, -1);
        cancelIco = typedArray.getResourceId(R.styleable.CancelEditText_cet_cancelIco, -1);
        if (cancelIco != -1)
            cancelBtn.setBackgroundResource(cancelIco);
        downIco = typedArray.getResourceId(R.styleable.CancelEditText_cet_downIco, -1);
        if (downIco != -1) {
            downBtn.setBackgroundResource(downIco);
            downBtn.setVisibility(VISIBLE);
        }
        switchIco = typedArray.getResourceId(R.styleable.CancelEditText_cet_switchIco, -1);
        if (switchIco != -1) {
            switchBtn.setBackgroundResource(switchIco);
            switchBtn.setVisibility(VISIBLE);
        }

        editText.setText(typedArray.getText(R.styleable.CancelEditText_cet_text));
        editText.setSelection(editText.length());
        editText.setTextColor(
                typedArray.getColor(R.styleable.CancelEditText_cet_textColor, Color.parseColor("#212121")));
        editText.setTextSize(DensityUtil.px2sp(context,
                typedArray.getDimension(R.styleable.CancelEditText_cet_textSize, DensityUtil.sp2px(context, 14))));
        labelText.setText(typedArray.getText(R.styleable.CancelEditText_cet_label));
        labelText.setTextColor(
                typedArray.getColor(R.styleable.CancelEditText_cet_labelColor, Color.parseColor("#212121")));
        labelText.setTextSize(DensityUtil.px2sp(context,
                typedArray.getDimension(R.styleable.CancelEditText_cet_textSize, DensityUtil.sp2px(context, 14))));

        editText.setHint(typedArray.getString(R.styleable.CancelEditText_cet_hint));
        editText.setHintTextColor(
                typedArray.getColor(R.styleable.CancelEditText_cet_hintColor, Color.parseColor("#a1a1a1")));

        switch (typedArray.getInt(R.styleable.CancelEditText_cet_inputType, -1)) {
            case 1:
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case 2:
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                break;
            case 3:
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS | InputType.TYPE_CLASS_TEXT);
                break;
            default:
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
        }


        boolean singleLine = typedArray.getBoolean(R.styleable.CancelEditText_cet_singleLine,true);
        if(!singleLine) {
            editText.setInputType(editText.getInputType() | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            editText.setGravity(Gravity.START);
            labelText.setGravity(Gravity.START);
        }

        String digits = typedArray.getString(R.styleable.CancelEditText_cet_digits);
        if (!StringUtils.isEmpty(digits)) {
            editText.setKeyListener(DigitsKeyListener.getInstance(digits));
        }

        setEnabled(typedArray.getBoolean(R.styleable.CancelEditText_cet_enabled, true));
        setTextEnabled(typedArray.getBoolean(R.styleable.CancelEditText_cet_textEnabled, true));

        switch (typedArray.getInt(R.styleable.CancelEditText_cet_imeOptions, -1)) {
            case 1:
                editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                break;
            case 2:
                editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                break;
            case 3:
                editText.setImeOptions(EditorInfo.IME_ACTION_SEND);
                break;
            case 4:
                editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                break;
            case 5:
                editText.setImeOptions(EditorInfo.IME_ACTION_GO);
                break;
        }

        LayoutParams params = (LayoutParams) editText.getLayoutParams();
        params.leftMargin = (int) typedArray.getDimension(R.styleable.CancelEditText_cet_textPaddingLeft, 0);
        params.rightMargin = (int) typedArray.getDimension(R.styleable.CancelEditText_cet_textPaddingRight, 0);
        params.topMargin = (int) typedArray.getDimension(R.styleable.CancelEditText_cet_textPaddingTop, 0);
        params.bottomMargin = (int) typedArray.getDimension(R.styleable.CancelEditText_cet_textPaddingBottom, 0);
        params.weight = 1;
        params.height = LayoutParams.MATCH_PARENT;
        params.width = LayoutParams.MATCH_PARENT;

        typedArray.recycle();

    }

    /**
     * 初始化view
     */
    private void initView(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        cancelBtn = new CancelIco(context);
        cancelBtn.setOnClickListener(this);

        downBtn = new CancelDown(context);
        downBtn.setOnClickListener(this);

        switchBtn = new CheckBox(context);
        switchBtn.setButtonDrawable(null);
        switchBtn.setLayoutParams(new LinearLayout.LayoutParams(switchIcoWidth, switchIcoHeight));
        switchBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchChangeListener != null) switchChangeListener.change(isChecked);
        });

        editText = new CancelEdit(context);
        editText.setOnFocusChangeListener(this);
        editText.addTextChangedListener(this);
        editText.setOnEditorActionListener(this);
//        setProhibitEmoji();

        labelText = new TextView(context);
        labelText.setAutoLinkMask(Linkify.ALL);
        labelText.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

        addView(labelText);
        addView(editText);
        addView(cancelBtn);
        addView(downBtn);
        addView(switchBtn);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.showDropDown();
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {// 焦点变化时
        if (background != -1)
            if (hasFocus && selectBackground != -1 && isEnabled() && editText.isEnabled()) {
                setBackgroundResource(selectBackground);
            } else {
                setBackgroundResource(background);
            }
        if (cancelIco != -1)
            if (hasFocus && editText.getText().length() > 0 && isEnabled() && editText.isEnabled()) {
                cancelBtn.setVisibility(View.VISIBLE);
            } else {
                cancelBtn.setVisibility(View.GONE);
            }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, // text发生变化时
                                  int after) {
        if (cancelEditTextListener != null) {
            cancelEditTextListener.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (length != -1 && length < s.length()) {
            editText.setText(s.subSequence(0, length));
            editText.setSelection(editText.getText().length());
        }

        if (cancelIco != -1) {
            if (s.length() > 0 && isEnabled() && editText.isEnabled()) {
                cancelBtn.setVisibility(View.VISIBLE);
            } else {
                cancelBtn.setVisibility(View.GONE);
            }
        }
        if (cancelEditTextListener != null) {
            cancelEditTextListener.onTextChanged(s, start, before, count);
        }

        String newText = s.toString().trim();
        if (fuzzyQueryListener != null) {
            fuzzyQueryListener.onTextChanged(this, newText);
        }
        if (searchListener != null && !TextUtils.isEmpty(newText)) {
            List<String> history = getHistory();
//            L.e("dddd"+history);
//            ((SearchAdapter)editText.getAdapter()).clearList();
//            ((SearchAdapter)editText.getAdapter()).setList(history);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (cancelEditTextListener != null) {
            cancelEditTextListener.afterTextChanged(s);
        }
    }

    @Override
    public void onClick(View v) {// 点击按钮时
        switch ((Integer) v.getTag()) {
            case 1:
                editText.setText(null);
                clearHistory();
                break;
            case 2:
                editText.showDropDown();
                break;
        }

    }

    /**
     * px转dp
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    public void setThreshold(int threshold) {
        editText.setThreshold(threshold);
    }

    public <T extends ListAdapter & Filterable> void setAdapter(T adapter) {
        editText.setAdapter(adapter);
    }

    public void setLabelText(CharSequence text) {
        labelText.setText(text);
    }

    public void setSupportChinese(boolean isChinese) {
        if (!isChinese) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    public void setDigits(String digits) {
        if (!StringUtils.isEmpty(digits)) {
            editText.setKeyListener(DigitsKeyListener.getInstance(digits));
        } else {
            editText.setKeyListener(TextKeyListener.getInstance());
        }
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void showDropDown() {
        editText.showDropDown();
    }

    public void setCancelEditTextChangedListener(CancelEditTextChangedListener cancelEditTextListener) {
        this.cancelEditTextListener = cancelEditTextListener;
    }

    /**
     * 搜索历史
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            if (searchListener != null) {
                if (v.getText().toString().length() > 0) {
                    List<String> history = getHistory();
                    history.add(v.getText().toString());

                    SharedPreferences preferences = getContext().getSharedPreferences(historyName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putStringSet(historyKey, new HashSet<>(history));
                    editor.apply();//无返回值  异步操作
                }
                searchListener.search(v.getText().toString());
            }
//            InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
//            mInputMethodManager.hideSoftInputFromWindow(((Activity)getContext()).getCurrentFocus().getWindowToken(), 0);
        }
        return false;
    }

    public interface SearchListener {

        void search(String text);

    }

    public SearchListener getSearchListener() {
        return searchListener;
    }

    public void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    public String getHistoryKey() {
        return historyKey;
    }

    public void showPassword(boolean show) {
        if (show)
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        else
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText.setSelection(editText.length());
    }

    public void setHistoryKey(String historyKey) {
        this.historyKey = historyKey;
    }

    public List<String> getHistory() {
        SharedPreferences preferences = getContext().getSharedPreferences(historyName, Context.MODE_PRIVATE);
        return new ArrayList<>(preferences.getStringSet(historyKey, new HashSet<String>()));
    }

    public void clearHistory() {
        SharedPreferences preferences = getContext().getSharedPreferences(historyName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putStringSet(historyKey, new HashSet<>(new ArrayList<String>()));
        editor.apply();//无返回值  异步操作
    }

    public interface CancelEditTextChangedListener {

        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);

    }

    public void setFuzzyQueryListener(FuzzyQueryListener fuzzyQueryListener) {
        this.fuzzyQueryListener = fuzzyQueryListener;
    }

    public interface FuzzyQueryListener {

        void onTextChanged(View view, String text);

    }

    public AutoCompleteTextView getEditText() {
        return editText;
    }

    public void setText(String text) {
        editText.setText(text);
        if (StringUtils.isNotEmpty(text))
            setSelection();
    }

    public String getText() {
        return editText.getText().toString();
    }

    public View getCancelBtn() {
        return cancelBtn;
    }

    public CancelDown getDownBtn() {
        return downBtn;
    }

    public void setSelection() {
        getEditText().setSelection(getText().length());
    }

    @Override
    public void setEnabled(boolean enabled) {
        cancelBtn.setEnabled(enabled);
        downBtn.setEnabled(enabled);
        switchBtn.setEnabled(enabled);
        cancelBtn.setVisibility(View.GONE);
        editText.setEnabled(enabled);
        setClickable(enabled && !editText.isEnabled());

        super.setEnabled(enabled);
    }

    public void setTextEnabled(boolean enabled) {
        editText.setEnabled(enabled);
        setClickable(!enabled);
        if (enabled) {
            editText.requestFocus();
            if (StringUtils.isNotEmpty(getText()))
            setSelection();
        } else {
            editText.clearFocus();
        }
    }

    /**
     * 过滤字符
     */
    public void setProhibitEmoji() {
        InputFilter[] filters = {getInputFilterProhibitEmoji(), getInputFilterProhibitSP()};
        getEditText().setFilters(filters);
    }

    private InputFilter getInputFilterProhibitEmoji() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuffer buffer = new StringBuffer();
                for (int i = start; i < end; i++) {
                    char codePoint = source.charAt(i);
                    if (!getIsEmoji(codePoint)) {
                        buffer.append(codePoint);
                    } else {
                        T.showWarning(getContext().getResources().getString(R.string.emoticons_supported));
                        i++;
                        continue;
                    }
                }
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(buffer);
                    TextUtils.copySpansFrom((Spanned) source, start, end, null,
                            sp, 0);
                    return sp;
                } else {
                    return buffer;
                }
            }
        };
        return filter;
    }

    private boolean getIsEmoji(char codePoint) {
        if ((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)))
            return false;
        return true;
    }

    private InputFilter getInputFilterProhibitSP() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuffer buffer = new StringBuffer();
                for (int i = start; i < end; i++) {
                    char codePoint = source.charAt(i);
                    if (!getIsSp(codePoint)) {
                        buffer.append(codePoint);
                    } else {
                        T.showWarning(getContext().getResources().getString(R.string.special_characters_supported));
                        i++;
                        continue;
                    }
                }
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(buffer);
                    TextUtils.copySpansFrom((Spanned) source, start, end, null,
                            sp, 0);
                    return sp;
                } else {
                    return buffer;
                }
            }
        };
        return filter;
    }

    private boolean getIsSp(char codePoint) {
        if (Character.getType(codePoint) > Character.LETTER_NUMBER) {
            return true;
        }
        return false;
    }

    public class CancelEdit extends AppCompatAutoCompleteTextView {

        public CancelEdit(Context context) {
            super(context);

//            setAutoLinkMask(Linkify.ALL);
            setBackgroundColor(Color.parseColor("#00000000"));

            setPadding(0, 0, 0, 0);
            setThreshold(1);//第一个字提示
            setDropDownVerticalOffset(25);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (!editText.isEnabled()) {
                return false;
            }
            return super.onTouchEvent(event);
        }
    }

    public class CancelIco extends RelativeLayout {

        private ImageView image;
        private Button btn;

        public CancelIco(Context context) {
            super(context);

            image = new ImageView(context);
            btn = new Button(context);

            LayoutParams params = new LayoutParams(cancelIcoWidth, cancelIcoHeight);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);

            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setAdjustViewBounds(true);
            image.setLayoutParams(params);
            image.setTag(1);

            btn.setLayoutParams(new LayoutParams(DensityUtil.dp2px(context, 27), LayoutParams.MATCH_PARENT));
            btn.setTag(1);
            btn.setBackgroundColor(Color.parseColor("#00000000"));

            addView(image);
            addView(btn);
            setVisibility(View.GONE);
            setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        }

        @Override
        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);
            image.setEnabled(enabled);
            btn.setEnabled(enabled);
        }

        @Override
        public void setOnClickListener(@Nullable OnClickListener l) {
            image.setOnClickListener(l);
            btn.setOnClickListener(l);
        }

        @Override
        public void setBackgroundResource(int resId) {
            image.setBackgroundResource(resId);
        }

    }

    public class CancelDown extends RelativeLayout {

        private ImageView image;
        private Button btn;

        public CancelDown(Context context) {
            super(context);

            image = new ImageView(context);
            btn = new Button(context);

            LayoutParams params = new LayoutParams(downIcoWidth, downIcoHeight);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);

            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setAdjustViewBounds(true);

            image.setLayoutParams(params);
            image.setTag(2);

            btn.setLayoutParams(new LayoutParams(DensityUtil.dp2px(context, 27), LayoutParams.MATCH_PARENT));
            btn.setTag(2);
            btn.setBackgroundColor(Color.parseColor("#00000000"));

            addView(image);
            addView(btn);
            setVisibility(View.GONE);
            setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        }

        @Override
        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);
            image.setEnabled(enabled);
            btn.setEnabled(enabled);
        }

        @Override
        public void setOnClickListener(@Nullable OnClickListener l) {
            image.setOnClickListener(l);
            btn.setOnClickListener(l);
        }

        @Override
        public void setBackgroundResource(int resId) {
            image.setBackgroundResource(resId);
        }

    }

    public SwitchChangeListener getSwitchChangeListener() {
        return switchChangeListener;
    }

    public void setSwitchChangeListener(SwitchChangeListener switchChangeListener) {
        this.switchChangeListener = switchChangeListener;
    }

    public interface SwitchChangeListener {
        void change(boolean checked);
    }

}
