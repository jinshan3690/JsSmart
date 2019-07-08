package com.js.smart.common.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<Y extends BaseViewHolder, T extends Object> extends RecyclerView.Adapter<Y> {

    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_HEADER = 1;
    protected static final int TYPE_FOOTER = 2;

    protected List<T> data = new ArrayList<>();
    protected Context context;
    protected int layout;
    protected OnItemClickListener itemClickListener;

    public BaseRecyclerAdapter(Context context, List<T> list, int layout) {
        if (list != null)
            this.data.addAll(list);
        this.context = context;
        this.layout = layout;
    }

    public BaseRecyclerAdapter(Context context, List<T> list) {
        this.data.addAll(list);
        this.context = context;
    }

    public BaseRecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Y onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return onCreateViewHolder(parent, view, viewType);
    }

    protected abstract Y onCreateViewHolder(ViewGroup parent, View view, int viewType);

    @Override
    public void onBindViewHolder(Y holder, int position) {
        onBindViewHolder(holder, position, data.get(position));
    }

    public abstract void onBindViewHolder(Y holder, int position, T payloads);


    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(View v, int position);

        void OnItemLongClick(View v, int position);
    }

    protected void setOnClickListener(final View view, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null)
                    itemClickListener.OnItemClick(view, position);
            }
        });
    }

    protected void setOnLongClickListener(final View view, final int position) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.OnItemLongClick(view, position);
                    return true;
                }
                return false;
            }
        });
    }

    public View inflate(int layout) {
        return LayoutInflater.from(context).inflate(layout, null);
    }

    public View inflate(int layout, ViewGroup view) {
        return LayoutInflater.from(context).inflate(layout, view, false);
    }

    public T getData(int position) {
        return data.get(position);
    }

    public List<T> getList() {
        return data;
    }

    public void setList(List<T> list) {
        this.data.addAll(list);

        notifyDataSetChanged();
    }

    public void remove(int position) {
        getList().remove(position);
        notifyDataSetChanged();
    }

    public void clearList() {
        this.data.clear();
    }

}
