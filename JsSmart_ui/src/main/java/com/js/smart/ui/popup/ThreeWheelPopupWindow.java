package com.js.smart.ui.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.js.smart.ui.R;
import com.js.smart.ui.widget.WheelView;
import com.js.smart.common.ui.popup.BasePopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/4/24 11:34.
 *
 * @author pan
 * @version 1.0
 */
public class ThreeWheelPopupWindow<T> extends BasePopupWindow<T> {

    protected TextView titleTv;
    protected WheelView wheelView1;
    protected WheelView wheelView2;
    protected WheelView wheelView3;

    protected Button btn2;

    public ThreeWheelPopupWindow(Context context, View view) {
        super(context, view, R.layout.pop_three_wheel);

        titleTv = content.findViewById(R.id.textView1);
        content.findViewById(R.id.btn1).setOnClickListener(v -> windowUtil.dismiss());
        btn2 = content.findViewById(R.id.btn2);
        wheelView1 = content.findViewById(R.id.wheelView1);
        wheelView2 = content.findViewById(R.id.wheelView2);
        wheelView3 = content.findViewById(R.id.wheelView3);
        wheelView1.setOffset(1);
        wheelView2.setOffset(1);
        wheelView3.setOffset(1);
    }

    public T setTitle(String title) {
        titleTv.setText(title);
        return (T) this;
    }

    public ThreeWheelPopupWindow setItemsOne(List<String> items) {
        return setItemsOne(items, 0);
    }

    public ThreeWheelPopupWindow setItemsOne(List<String> items, int index) {
        wheelView1.setItems(new ArrayList<>(items));
        wheelView1.setSeletion(index);
        return this;
    }

    public ThreeWheelPopupWindow setItemsOne(List<String> items, String index) {
        return setItemsOne(items, items.indexOf(index));
    }

    public ThreeWheelPopupWindow setItemsTwo(List<String> items) {
        return setItemsTwo(items, 0);
    }

    public ThreeWheelPopupWindow setItemsTwo(List<String> items, int index) {
        wheelView2.setItems(new ArrayList<>(items));
        wheelView2.setSeletion(index);
        return this;
    }

    public ThreeWheelPopupWindow setItemsTwo(List<String> items, String index) {
        return setItemsTwo(items, items.indexOf(index));
    }

    public ThreeWheelPopupWindow setItemsThree(List<String> items) {
        return setItemsThree(items, 0);
    }

    public ThreeWheelPopupWindow setItemsThree(List<String> items, int index) {
        wheelView3.setItems(new ArrayList<>(items));
        wheelView3.setSeletion(index);
        return this;
    }

    public ThreeWheelPopupWindow setItemsThree(List<String> items, String index) {
        return setItemsThree(items, items.indexOf(index));
    }

    public String getSelectedItemOne(){
        return wheelView1.getSeletedItem();
    }

    public String getSelectedItemTwo(){
        return wheelView2.getSeletedItem();
    }

    public String getSelectedItemThree(){
        return wheelView3.getSeletedItem();
    }

    public PopupWindow show(ThreeWheelPopupListener threeWheelListener) {
        return show(Gravity.BOTTOM, 0, 0, threeWheelListener);
    }

    public PopupWindow show(int gravity, int x, int y, ThreeWheelPopupListener threeWheelListener) {
        btn2.setOnClickListener(v -> {
            threeWheelListener.item(getSelectedItemOne(), getSelectedItemTwo(), getSelectedItemThree());
            windowUtil.dismiss();
        });
        return show(gravity, x, y);
    }

    public interface ThreeWheelPopupListener {
        void item(String one, String two, String three);
    }

}
