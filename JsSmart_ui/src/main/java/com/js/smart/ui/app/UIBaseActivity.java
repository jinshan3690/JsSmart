package com.js.smart.ui.app;

import com.js.smart.common.app.BaseCompatActivity;
import com.js.smart.ui.R;

public abstract class UIBaseActivity extends BaseCompatActivity  {

    public TitleUtil titleUtil;

    @Override
    protected void initViewBefore() {
        super.initViewBefore();
        titleUtil = TitleUtil.build(context, view);
    }

    protected String getVerifyHint(CharSequence hint){
        return getVerifyHint("%s %s", hint);
    }

    protected String getVerifyHint(String format,CharSequence hint){
        return String.format(format, getResources().getString(R.string.please_enter), hint);
    }


}
