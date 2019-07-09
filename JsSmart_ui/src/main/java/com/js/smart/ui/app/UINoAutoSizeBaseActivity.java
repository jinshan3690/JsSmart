package com.js.smart.ui.app;

import com.js.smart.common.app.BaseCompatActivity;
import com.js.smart.common.util.LocalManager;
import com.js.smart.ui.R;

import org.apache.commons.lang.StringUtils;

public abstract class UINoAutoSizeBaseActivity extends BaseCompatActivity  {

    protected TitleUtil titleUtil;

    @Override
    protected void initViewBefore() {
        super.initViewBefore();
        titleUtil = TitleUtil.build(context);
    }

    protected String getVerifyHint(CharSequence hint){
        return String.format("%s %s", getResources().getString(R.string.please_enter), hint);
    }

}
