package com.js.smart.ui.adapter;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;



/**
 * Created by Js on 2016/5/13.
 */
public class ArraySearchAdapter extends SearchAdapter<String> {

    public ArraySearchAdapter(AppCompatActivity context, List<String> data) {
        super(context, data);
    }

    @Override
    protected void getView(TextView textView, String item, SearchType type) {
        textView.setText(item);
    }

    @Override
    protected String getItem(SearchType type, String item) {
        return item;
    }

}
