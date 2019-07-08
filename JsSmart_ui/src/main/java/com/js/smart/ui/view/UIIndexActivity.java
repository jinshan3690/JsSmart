package com.js.smart.ui.view;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.js.smart.common.app.AcManager;
import com.js.smart.common.util.AntiShakeUtils;
import com.js.smart.common.util.WebViewJavaScriptFunction;
import com.js.smart.ui.R;
import com.js.smart.ui.R2;
import com.js.smart.ui.UIRoute;
import com.js.smart.ui.app.UIBaseActivity;
import com.js.smart.ui.widget.X5WebView;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = UIRoute.ui_index)
public class UIIndexActivity extends UIBaseActivity {

    @Override
    public int createView(Bundle savedInstanceState) {
        return R.layout.ac_ui_index;
    }

    @Override
    public void initView() {
        titleUtil.setTitle("UIIndex");

    }

    /**
     * Bind
     */
    @OnClick({R2.id.btn1, R2.id.btn2 , R2.id.btn3, R2.id.btn4})
    public void click(View v){
        if (R.id.btn1 == v.getId()){
            toActivityForData(v, UIRoute.ui_start_page, postcard -> postcard.withString("nextRoute", UIRoute.ui_index).navigation(context));
        }else if (R.id.btn2 == v.getId()){
            toActivityForData(v, UIRoute.ui_guide_page, postcard -> postcard.withString("nextRoute", UIRoute.ui_index).navigation(context));
        }else if (R.id.btn3 == v.getId()){
            toActivityForData(v, UIRoute.ui_web, postcard -> postcard.withString("title", "百度").withString("url", "https://github.com/").navigation(context));
        }else if (R.id.btn4 == v.getId()){
//            toActivityForData(v, UIRoute.ui_web, postcard -> postcard.withString("title", "百度").withString("url", "www.baidu.com").navigation(context));
        }
    }
    /**
     * Action
     */

}
