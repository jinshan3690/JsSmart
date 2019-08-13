package com.js.smart.ui.app;


import android.os.Bundle;

import com.js.smart.common.app.BaseFragment;
import com.js.smart.ui.R;

public abstract class UIBaseFragment extends BaseFragment   {

    protected TitleUtil titleUtil;

    @Override
    protected void initViewBefore(Bundle savedInstanceState) {
        super.initViewBefore(savedInstanceState);
        titleUtil = TitleUtil.build(context, view);
    }

    protected String getVerifyHint(CharSequence hint){
        return String.format("%s %s", getResources().getString(R.string.please_enter), hint);
    }

}
