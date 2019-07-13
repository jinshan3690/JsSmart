package com.js.smart.ui.view.fm;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.js.smart.ui.R;
import com.js.smart.ui.R2;
import com.js.smart.ui.app.UIBaseActivity;

import butterknife.BindView;

/**
 * Created by Js on 2017/9/4.
 */

public class NoneFragment extends UIBaseActivity {

    @BindView(R2.id.layout2)
    RelativeLayout contentLt;
    @BindView(R2.id.imageView1)
    ImageView imgIv;

    @Override
    public int createView(Bundle savedInstanceState) {
        return R.layout.fm_none;
    }

    @Override
    public void initView() {
    }



    /**
     * Bind
     */

    /**
     * Action
     */


}
