package com.js.smart.ui.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.js.smart.common.app.AntiShakeOnClickListener;
import com.js.smart.common.ui.dialog.DialogBuilder;
import com.js.smart.common.util.DensityUtil;
import com.js.smart.common.util.ImageUtil;
import com.js.smart.common.util.L;
import com.js.smart.common.util.PopupWindowUtil;
import com.js.smart.common.util.T;
import com.js.smart.ui.R;
import com.js.smart.ui.R2;
import com.js.smart.ui.UIRoute;
import com.js.smart.ui.adapter.ArraySearchAdapter;
import com.js.smart.ui.app.UILoadingActivity;
import com.js.smart.ui.dialog.ConfirmDialog;
import com.js.smart.ui.dialog.DatePickerDialog;
import com.js.smart.ui.dialog.DateTimePickerDialog;
import com.js.smart.ui.dialog.DefaultDialog;
import com.js.smart.ui.dialog.GenderDialog;
import com.js.smart.ui.dialog.MessageDialog;
import com.js.smart.ui.dialog.TimePickerDialog;
import com.js.smart.ui.popup.DatePopupWindow;
import com.js.smart.ui.popup.MultiplePopupWindow;
import com.js.smart.ui.popup.ThreeWheelPopupWindow;
import com.js.smart.ui.popup.TwoWheelPopupWindow;
import com.js.smart.ui.popup.WheelPopupWindow;
import com.js.smart.ui.widget.CancelEditText;

import java.io.IOException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
@Route(path = UIRoute.ui_index)
public class UIIndexActivity extends UILoadingActivity {

    private DialogBuilder dialog;
    private PopupWindow popupWindow;
    private PopupWindowUtil headWindow;

    @BindView(R2.id.layout1)
    ViewGroup group;
    @BindView(R2.id.editText1)
    CancelEditText editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);//恢复原有的样式
        super.onCreate(savedInstanceState);
    }

    @Override
    public int createView(Bundle savedInstanceState) {
        acManager.setStatusTrans(true);
        return R.layout.ac_ui_index;
    }

    @Override
    public void initView() {
        titleUtil
                .setTitle("JsSmartUI")
                .setRightText("设置")
                .setRightClick(new AntiShakeOnClickListener() {
                    @Override
                    protected void antiShakeOnClick(View v) {
                        hideLoading();
                        view.post(() -> {
                            for (int i = 0; i < group.getChildCount(); i++) {
                                group.getChildAt(i).setEnabled(true);
                                group.invalidate();
                            }
                        });
                    }
                });

        for (int i = 0; i < group.getChildCount(); i++) {
            group.getChildAt(i).setOnClickListener(new AntiShakeOnClickListener() {
                @Override
                protected void antiShakeOnClick(View v) {
                    v.setEnabled(false);
                }
            });
        }

        initPopupWindow();
        editText1.setAdapter(new ArraySearchAdapter(context, Arrays.asList("fasdf", "fsadf", "vasdvas", "adfa", "bagadsfsa", "casdfasdf")));
        editText1.setOnClickListener(v -> editText1.showDropDown());
    }

    private void initPopupWindow() {

        View menu = LayoutInflater.from(context).inflate(R.layout.pop_image, null);
        headWindow = new PopupWindowUtil(context).
                initPopupWindow(menu, view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.popupWindow_anim_style);
        AntiShakeOnClickListener imageClickListener = new AntiShakeOnClickListener() {
            @Override
            protected void antiShakeOnClick(View v) {
                int i = v.getId();
                if (i == R.id.textView1) {        //拍照
                    ImageUtil.img_PhotoFromCapture(context);
                    headWindow.dismiss();
                } else if (i == R.id.textView2) {// 从相册选择
                    ImageUtil.img_PhotoFromDCIM(context);
                    headWindow.dismiss();
                } else if (i == R.id.textView3) {// 取消
                    headWindow.dismiss();
                }
            }
        };

        menu.findViewById(R.id.textView1).setOnClickListener(imageClickListener);
        menu.findViewById(R.id.textView2).setOnClickListener(imageClickListener);
        menu.findViewById(R.id.textView3).setOnClickListener(imageClickListener);

    }

    /**
     * Bind
     */
    @OnClick({R2.id.btn1, R2.id.btn2, R2.id.btn3, R2.id.btn4, R2.id.btn5, R2.id.btn6, R2.id.btn7, R2.id.btn8, R2.id.btn9,
            R2.id.btn10, R2.id.btn11, R2.id.btn12, R2.id.btn13, R2.id.btn14, R2.id.btn15, R2.id.btn16, R2.id.btn17, R2.id.btn18})
    public void click(View v) {
        if (dialog != null)
            dialog.dismiss();
        if (popupWindow != null)
            popupWindow.dismiss();
        if (headWindow != null)
            headWindow.dismiss();

        if (R.id.btn18 == v.getId()) {
            View view = LayoutInflater.from(context).inflate(R.layout.pop_image, null , false);
            view.setOnClickListener(v1 -> T.showSuccess("ok"));
            T.showCustom(view, Gravity.TOP);
        }else if (R.id.btn1 == v.getId()) {
            toActivityForData(v, UIRoute.ui_start_page, postcard -> postcard.withString("nextRoute", UIRoute.ui_index).navigation(context));
        } else if (R.id.btn2 == v.getId()) {
            toActivityForData(v, UIRoute.ui_guide_page, postcard -> postcard.withString("nextRoute", UIRoute.ui_index).navigation(context));
        } else if (R.id.btn3 == v.getId()) {
            toActivityForData(v, UIRoute.ui_web, postcard -> postcard.withString("title", "百度").withString("url", "https://github.com/").navigation(context));
        } else if (R.id.btn4 == v.getId()) {
            showLoading();
//            toActivityForData(v, UIRoute.ui_web, postcard -> postcard.withString("title", "百度").withString("url", "www.baidu.com").navigation(context));
        } else if (R.id.btn5 == v.getId()) {
            dialog = new ConfirmDialog(context).showConfirmDialog("标题", "内容", new AntiShakeOnClickListener() {
                @Override
                protected void antiShakeOnClick(View v) {
                    if (v.getId() == R.id.btn2) {
                        T.showSuccess("ok");
                    }
                    dialog.dismiss();
                }
            });
        } else if (R.id.btn6 == v.getId()) {
            dialog = new DatePickerDialog(context).setTitle("标题").showDatePickerDialog("2018-11-20", new AntiShakeOnClickListener() {
                @Override
                protected void antiShakeOnClick(View v) {
                    if (v.getId() == R.id.btn2) {
                        T.showSuccess("ok:" + dialog.getResult());
                    }
                    dialog.dismiss();
                }
            });
        } else if (R.id.btn7 == v.getId()) {
            dialog = new DateTimePickerDialog(context).setTitle("标题").showDateTimePickerDialog("2018-11-20 13:15:20", new AntiShakeOnClickListener() {
                @Override
                protected void antiShakeOnClick(View v) {
                    if (v.getId() == R.id.btn2) {
                        T.showSuccess("ok:" + dialog.getResult());
                    }
                    dialog.dismiss();
                }
            });
        } else if (R.id.btn8 == v.getId()) {
            dialog = new DefaultDialog(context).showDefaultDialog("内容", 1000, new AntiShakeOnClickListener() {
                @Override
                protected void antiShakeOnClick(View v) {
                    T.showSuccess("ok:");
                }
            });
        } else if (R.id.btn9 == v.getId()) {
            dialog = new GenderDialog(context).showGenderDialog(getResources().getString(R.string.male), text -> {
                T.showSuccess(text);
                dialog.dismiss();
            });
        } else if (R.id.btn10 == v.getId()) {
            dialog = new MessageDialog(context).showMessageDialog("内容", new AntiShakeOnClickListener() {
                @Override
                protected void antiShakeOnClick(View v) {
                    if (v.getId() == R.id.btn1) {
                        T.showSuccess("ok:");
                    }
                    dialog.dismiss();
                }
            });
        } else if (R.id.btn11 == v.getId()) {
            dialog = new TimePickerDialog(context).setTitle("标题").showTimePickerDialog("13:15:20", new AntiShakeOnClickListener() {
                @Override
                protected void antiShakeOnClick(View v) {
                    if (v.getId() == R.id.btn2) {
                        T.showSuccess("ok:" + dialog.getResult());
                    }
                    dialog.dismiss();
                }
            });
        } else if (R.id.btn12 == v.getId()) {
//            popupWindow = new DatePopupWindow(context, view, "2020-09-08 18:25:53",false).show((year, month, day, hour, minute, second) -> {
            DatePopupWindow popupWindow = new DatePopupWindow(context, view, "2019-07-08 18:25:53", true, "2019-09-08 18:25:53");
            popupWindow.getHourWl().setMaxWidth(DensityUtil.dp2px(context, 100));
            popupWindow.getMinuteWl().setMaxWidth(DensityUtil.dp2px(context, 100));
            popupWindow.getHourWl().setMinDivider(DensityUtil.dp2px(context, 70));
            popupWindow.getMinuteWl().setMinDivider(DensityUtil.dp2px(context, 70));
            popupWindow
//                    .showYear(false)
//                    .showMonth(false)
//                    .showDay(false)
//                    .showSecond(false)
                    .show((year, month, day, hour, minute, second) -> {
                        T.showSuccess("ok:" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
//                        popupWindow.dismiss();
                    });
        } else if (R.id.btn13 == v.getId()) {
            popupWindow = new MultiplePopupWindow(context, view, Arrays.asList("周一", "周三", "周六", "周日"))
                    .setItems(Arrays.asList("周一", "周二", "周三", "周四", "周五", "周六", "周日"))
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
        } else if (R.id.btn16 == v.getId()) {
            UIIndexActivityPermissionsDispatcher.showCameraWithPermissionCheck(UIIndexActivity.this);
        } else if (R.id.btn17 == v.getId()) {
            popupWindow = new WheelPopupWindow(context, view)
                    .setOffset(2)
                    .setItemsOne(Arrays.asList("111", "222", "333", "444"), "333")
                    .show(one -> {
                        T.showSuccess("ok:" + one);
                        popupWindow.dismiss();
                    });
        }
    }

    @Override
    @SuppressLint("NeedOnRequestPermissionsResult")
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        UIIndexActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void showCamera() {
        headWindow
                .setObscure()
                .showAtLocation(Gravity.BOTTOM, 0, 0);
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void onCameraDenied() {
        T.showWarning(getResources().getString(R.string.permissions));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;

        Bitmap bitmap = null;
        try {
            bitmap = ImageUtil.onActivityResult(context, requestCode, resultCode, data, true, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (requestCode == ImageUtil.REQUEST_CODE_PHOTO_CAPTURE_WITH_CROP || requestCode == ImageUtil.REQUEST_CODE_PHOTO_PICK_WITH_CROP) {
            L.e(bitmap.toString());
            titleUtil.setTitleSrc(new BitmapDrawable(getResources(), bitmap));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageUtil.cleanImageCache(ImageUtil.imageCache);
    }

    /**
     * Action
     */

}
