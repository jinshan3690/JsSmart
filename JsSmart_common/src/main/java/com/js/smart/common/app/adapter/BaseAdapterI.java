package com.js.smart.common.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;


/**
 * Created by Js on 2016/5/13.
 */
public abstract class BaseAdapterI<T> extends android.widget.BaseAdapter {

    protected Context context;
    protected List<T> data;
    protected int layoutId;
    protected OnItemClickListener itemClickListener;

    public BaseAdapterI(Context context, List<T> data, int layoutId) {
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
    }

    public BaseAdapterI(Context context, List<T> data) {
        this.context = context;
        this.data = data;
    }

    private BaseAdapterI() {
    }

    @Override
    public int getCount() {
        if (data != null)
            return data.size();
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (data != null)
            return data.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null && getViewTypeCount() == 1) {
            convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        }
        return getView(position, convertView, parent, data.get(position));
    }

    protected abstract View getView(int position, View convertView, ViewGroup parent, T item);

    public List<T> getList() {
        return data;
    }

    public void setList(List<T> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void clearList() {
        this.data.clear();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(View v, int position);

        void OnItemLongClick(View v, int position);
    }

    protected void setOnClickListener(final View view, final int position) {
        view.setOnClickListener(v -> {
            if (itemClickListener != null)
                itemClickListener.OnItemClick(view, position);
        });
    }

    protected void setOnLongClickListener(final View view, final int position) {
        view.setOnLongClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.OnItemLongClick(view, position);
                return true;
            }
            return false;
        });
    }

}
