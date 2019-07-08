package com.js.smart.common.app.ac_anim.mode;


import com.js.smart.common.R;
import com.js.smart.common.app.ac_anim.AcAnimInterface;

/**
 * Created by Js on 2016/6/28.
 */
public class AcAnimFast implements AcAnimInterface {

    @Override
    public int enterAnim() {
        return R.anim.ac_enter_fast;
    }

    @Override
    public int exitAnim() {
        return R.anim.ac_exit_fast;
    }

    public static AcAnimFast get(){
        return new AcAnimFast();
    }

}
