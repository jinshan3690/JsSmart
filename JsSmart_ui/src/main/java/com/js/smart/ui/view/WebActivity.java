package com.js.smart.ui.view;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.js.smart.ui.R;
import com.js.smart.ui.R2;
import com.js.smart.ui.UIRoute;
import com.js.smart.ui.app.UIBaseActivity;
import com.js.smart.ui.app.UILoadingActivity;
import com.js.smart.ui.widget.X5WebView;
import com.js.smart.common.util.WebViewJavaScriptFunction;

import butterknife.BindView;

@Route(path = UIRoute.ui_web)
public class WebActivity extends UIBaseActivity {

    @BindView(R2.id.webView1)
    X5WebView webView;

    @Autowired
    String title;
    @Autowired
    String url;
    @Autowired
    boolean isLoading;

    @Override
    public int createView(Bundle savedInstanceState) {
        return R.layout.ac_web;
    }

    @Override
    public void initView() {
        titleUtil.setTitle(title).setDefaultLeftClick();

        webView.loadUrl(url);
        webView.addJavascriptInterface(new WebViewJavaScriptFunction(context),"App");
        webView.setLoading(isLoading);

    }

    /**
     * Bind
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clear();
        webView = null;
    }
    /**
     * Action
     */

}
