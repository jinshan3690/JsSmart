package com.js.smart.common.app;

import android.os.Bundle;

/**
 * Created by Js on 2016/8/22.
 */
public interface BaseActivityI {

    int createView(Bundle savedInstanceState);

    /**
     * 创建时加载
     */
    void initView();

}
