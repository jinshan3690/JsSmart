package com.js.smart.ui.app;


import android.os.Bundle;

import com.js.smart.common.app.BaseFragment;
import com.js.smart.ui.R;

import me.jessyan.autosize.internal.CustomAdapt;

public abstract class UIBaseFragment extends BaseFragment implements CustomAdapt {

    protected TitleUtil titleUtil;

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }

    @Override
    protected void initViewBefore(Bundle savedInstanceState) {
        super.initViewBefore(savedInstanceState);
        titleUtil = TitleUtil.build(context, view);
    }

    protected String getVerifyHint(CharSequence hint){
        return String.format("%s %s", getResources().getString(R.string.please_enter), hint);
    }

}
