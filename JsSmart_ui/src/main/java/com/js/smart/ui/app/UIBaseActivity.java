package com.js.smart.ui.app;

import android.os.Bundle;

import com.js.smart.common.app.BaseCompatActivity;
import com.js.smart.common.util.LocalManager;
import com.js.smart.http.AppInfo;
import com.js.smart.ui.R;

import org.apache.commons.lang.StringUtils;

import me.jessyan.autosize.internal.CustomAdapt;

public abstract class UIBaseActivity extends BaseCompatActivity implements CustomAdapt {

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
    protected void initViewBefore() {
        super.initViewBefore();
        titleUtil = TitleUtil.build(context, view);
    }

    protected String getVerifyHint(CharSequence hint){
        return String.format("%s %s", getResources().getString(R.string.please_enter), hint);
    }

    protected boolean isLogin(){
        return StringUtils.isNotBlank(LocalManager.getInstance().getShareString(AppInfo.TOKEN));
    }



}
