package com.js.smart.common.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private int type;

    public BaseViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public BaseViewHolder(View itemView, int type){
        super(itemView);
        this.type = type;
        ButterKnife.bind(this, itemView);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
