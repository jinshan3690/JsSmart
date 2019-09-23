package com.js.smart.ui.popup;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.js.smart.ui.R;
import com.js.smart.ui.popup.adapter.MultipleAdapter;
import com.js.smart.common.ui.popup.BasePopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/4/24 11:34.
 *
 * @author pan
 * @version 1.0
 */
public class MultiplePopupWindow extends BasePopupWindow<MultiplePopupWindow> {

    protected TextView titleTv;
    protected RecyclerView recyclerView;
    protected MultipleAdapter adapter;

    protected Button btn2;

    public MultiplePopupWindow(Context context, View view, List<String> selected) {
        super(context, view, R.layout.pop_multiple);

        titleTv = content.findViewById(R.id.textView1);
        content.findViewById(R.id.btn1).setOnClickListener(v -> windowUtil.dismiss());
        btn2 = content.findViewById(R.id.btn2);

        recyclerView = content.findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MultipleAdapter(context, new ArrayList<>(), new ArrayList<>(selected));
        recyclerView.setAdapter(adapter);

    }

    public MultiplePopupWindow setTitle(String title) {
        titleTv.setText(title);
        return this;
    }

    public MultiplePopupWindow setItems(List<String> items) {
        adapter.setList(new ArrayList<>(items));
        return this;
    }

    public MultiplePopupWindow setMultiple(List<String> selected) {
        adapter.setMultiple(selected);
        return this;
    }

    public List<String> getMultiple() {
        return adapter.getMultiple();
    }

    public PopupWindow show(MultiplePopupListener multiplePopupListener) {
        return show(Gravity.BOTTOM, 0, 0, multiplePopupListener);
    }

    public PopupWindow show(int gravity, int x, int y, MultiplePopupListener multiplePopupListener) {
        btn2.setOnClickListener(v -> {
            multiplePopupListener.item(getMultiple());
            windowUtil.dismiss();
        });
        return show(gravity, x, y);
    }

    public interface MultiplePopupListener {
        void item(List<String> items);
    }

}
