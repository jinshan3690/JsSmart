package com.js.smart.common.app.ac_anim.mode;


import com.js.smart.common.R;
import com.js.smart.common.app.ac_anim.AcAnimInterface;

/**
 * Created by Js on 2016/6/28.
 */
public class AcAnimBottom implements AcAnimInterface {

    @Override
    public int enterAnim() {
        return R.anim.ac_bottom;
    }

    @Override
    public int exitAnim() {
        return R.anim.ac_none;
    }

    public static AcAnimBottom get(){
        return new AcAnimBottom();
    }

}
