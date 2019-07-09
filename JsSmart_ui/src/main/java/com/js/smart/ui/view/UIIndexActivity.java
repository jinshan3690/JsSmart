package com.js.smart.ui.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.js.smart.common.app.AcManager;
import com.js.smart.common.ui.dialog.BaseDialog;
import com.js.smart.common.ui.dialog.DialogBuilder;
import com.js.smart.common.util.AntiShakeUtils;
import com.js.smart.common.util.T;
import com.js.smart.common.util.WebViewJavaScriptFunction;
import com.js.smart.ui.R;
import com.js.smart.ui.R2;
import com.js.smart.ui.UIRoute;
import com.js.smart.ui.app.UIBaseActivity;
import com.js.smart.ui.dialog.ConfirmDialog;
import com.js.smart.ui.dialog.DatePickerDialog;
import com.js.smart.ui.dialog.DateTimePickerDialog;
import com.js.smart.ui.dialog.DefaultDialog;
import com.js.smart.ui.dialog.GenderDialog;
import com.js.smart.ui.dialog.MessageDialog;
import com.js.smart.ui.dialog.TimePickerDialog;
import com.js.smart.ui.popup.MonthPopupWindow;
import com.js.smart.ui.popup.MultiplePopupWindow;
import com.js.smart.ui.popup.ThreeWheelPopupWindow;
import com.js.smart.ui.popup.TwoWheelPopupWindow;
import com.js.smart.ui.widget.X5WebView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = UIRoute.ui_index)
public class UIIndexActivity extends UIBaseActivity {

    private DialogBuilder dialog;
    private PopupWindow popupWindow;

    @Override
    public int createView(Bundle savedInstanceState) {
        acManager.setStatusTrans(true);
        return R.layout.ac_ui_index;
    }

    @Override
    public void initView() {
        titleUtil
                .setTitle("JsSmartUI")
                .setRightText("设置");

    }

    /**
     * Bind
     */
    @OnClick({R2.id.btn1, R2.id.btn2, R2.id.btn3, R2.id.btn4, R2.id.btn5, R2.id.btn6, R2.id.btn7, R2.id.btn8, R2.id.btn9,
            R2.id.btn10, R2.id.btn11, R2.id.btn12, R2.id.btn13, R2.id.btn14, R2.id.btn15})
    public void click(View v) {
        if (R.id.btn1 == v.getId()) {
            toActivityForData(v, UIRoute.ui_start_page, postcard -> postcard.withString("nextRoute", UIRoute.ui_index).navigation(context));
        } else if (R.id.btn2 == v.getId()) {
            toActivityForData(v, UIRoute.ui_guide_page, postcard -> postcard.withString("nextRoute", UIRoute.ui_index).navigation(context));
        } else if (R.id.btn3 == v.getId()) {
            toActivityForData(v, UIRoute.ui_web, postcard -> postcard.withString("title", "百度").withString("url", "https://github.com/").navigation(context));
        } else if (R.id.btn4 == v.getId()) {
//            toActivityForData(v, UIRoute.ui_web, postcard -> postcard.withString("title", "百度").withString("url", "www.baidu.com").navigation(context));
        } else if (R.id.btn5 == v.getId()) {
            dialog = new ConfirmDialog(context).showConfirmDialog("标题", "内容", v1 -> {
                if (v1.getId() == R.id.btn2) {
                    T.showSuccess("ok");
                }
                dialog.dismiss();
            });
        } else if (R.id.btn6 == v.getId()) {
            dialog = new DatePickerDialog(context).setTitle("标题").showDatePickerDialog("2018-11-20", v1 -> {
                if (v1.getId() == R.id.btn2) {
                    T.showSuccess("ok:" + dialog.getResult());
                }
                dialog.dismiss();
            });
        } else if (R.id.btn7 == v.getId()) {
            dialog = new DateTimePickerDialog(context).setTitle("标题").showDateTimePickerDialog("2018-11-20 13:15:20", v1 -> {
                if (v1.getId() == R.id.btn2) {
                    T.showSuccess("ok:" + dialog.getResult());
                }
                dialog.dismiss();
            });
        } else if (R.id.btn8 == v.getId()) {
            dialog = new DefaultDialog(context).showDefaultDialog("内容", DialogInterface::dismiss);
        } else if (R.id.btn9 == v.getId()) {
            dialog = new GenderDialog(context).showGenderDialog(getResources().getString(R.string.male), text -> {
                T.showSuccess(text);
                dialog.dismiss();
            });
        } else if (R.id.btn10 == v.getId()) {
            dialog = new MessageDialog(context).showMessageDialog("内容", v1 -> {
                if (v1.getId() == R.id.btn2) {
                    T.showSuccess("ok:");
                }
                dialog.dismiss();
            });
        } else if (R.id.btn11 == v.getId()) {
            dialog = new TimePickerDialog(context).showTimePickerDialog("13:15:20", v1 -> {
                if (v1.getId() == R.id.btn2) {
                    T.showSuccess("ok:" + dialog.getResult());
                }
                dialog.dismiss();
            });
        } else if (R.id.btn12 == v.getId()) {
            popupWindow = new MonthPopupWindow(context, view, "", "").show((MonthPopupWindow.MonthPopupListener) (month, year) -> {
                T.showSuccess("ok:" + month + ":" + year);
                popupWindow.dismiss();
            });
        } else if (R.id.btn13 == v.getId()) {
            popupWindow = new MultiplePopupWindow(context, view, Arrays.asList("a", "fasd", "aaadffff", "ccccc"))
                    .setItems(Arrays.asList("eeeee", "a", "fasd", "aaadffff", "ccccc", "fffffff"))
                    .show(items -> {
                        T.showSuccess("ok:" + new Gson().toJson(items));
                        popupWindow.dismiss();
                    });
        } else if (R.id.btn14 == v.getId()) {
            popupWindow = new ThreeWheelPopupWindow(context, view)
                    .setItemsOne(Arrays.asList("111", "222", "333", "444"))
                    .setItemsTwo(Arrays.asList("aaa", "bbb", "ccc", "ddd"))
                    .setItemsThree(Arrays.asList("!!!", "@@@", "###", "$$$"))
                    .show((one, two, three) -> {
                        T.showSuccess("ok:" + one + ":" + two + ":" + three);
                        popupWindow.dismiss();
                    });
        } else if (R.id.btn15 == v.getId()) {
            popupWindow = new TwoWheelPopupWindow(context, view)
                    .setItemsOne(Arrays.asList("111", "222", "333", "444"))
                    .setItemsTwo(Arrays.asList("aaa", "bbb", "ccc", "ddd"))
                    .show((one, two) -> {
                        T.showSuccess("ok:" + one + ":" + two);
                        popupWindow.dismiss();
                    });
        }
    }
    /**
     * Action
     */

}
