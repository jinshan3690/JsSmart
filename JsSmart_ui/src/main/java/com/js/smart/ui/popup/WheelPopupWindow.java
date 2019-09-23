package com.js.smart.ui.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.js.smart.common.ui.popup.BasePopupWindow;
import com.js.smart.ui.R;
import com.js.smart.ui.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/4/24 11:34.
 *
 * @author pan
 * @version 1.0
 */
public class WheelPopupWindow<T> extends BasePopupWindow<T> {

    protected TextView titleTv;
    protected WheelView wheelView1;

    protected TextView btn2;

    public WheelPopupWindow(Context context, View view) {
        super(context, view, R.layout.pop_wheel);

        titleTv = content.findViewById(R.id.textView1);
        content.findViewById(R.id.btn1).setOnClickListener(v -> windowUtil.dismiss());
        btn2 = content.findViewById(R.id.btn2);
        wheelView1 = content.findViewById(R.id.wheelView1);
        wheelView1.setOffset(1);
    }

    public T setTitle(String title) {
        titleTv.setText(title);
        return (T) this;
    }

    public WheelPopupWindow setItemsOne(List<String> items) {
        return setItemsOne(items, 0);
    }

    public WheelPopupWindow setItemsOne(List<String> items, int index) {
        wheelView1.setItems(new ArrayList<>(items));
        wheelView1.setSeletion(index);
        return this;
    }

    public WheelPopupWindow setItemsOne(List<String> items, String index) {
        return setItemsOne(items, items.indexOf(index));
    }

    public String getSelectedItemOne(){
        return wheelView1.getSeletedItem();
    }

    public PopupWindow show(ThreeWheelPopupListener threeWheelListener) {
        return show(Gravity.BOTTOM, 0, 0, threeWheelListener);
    }

    public PopupWindow show(int gravity, int x, int y, ThreeWheelPopupListener threeWheelListener) {
        btn2.setOnClickListener(v -> {
            threeWheelListener.item(getSelectedItemOne());
            windowUtil.dismiss();
        });
        return show(gravity, x, y);
    }

    public interface ThreeWheelPopupListener {
        void item(String one);
    }

}
