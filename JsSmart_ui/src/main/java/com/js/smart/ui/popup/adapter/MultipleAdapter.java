package com.js.smart.ui.popup.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.js.smart.common.app.adapter.BaseRecyclerAdapter;
import com.js.smart.common.app.adapter.BaseViewHolder;
import com.js.smart.ui.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MultipleAdapter extends BaseRecyclerAdapter<MultipleAdapter.ItemViewHolder, String> {

    private List<String> selected;

    public MultipleAdapter(Context context, List<String> list, List<String> selected) {
        super(context, list, R.layout.item_popup_multiple);
        this.selected = selected;
    }

    @Override
    protected ItemViewHolder onCreateViewHolder(ViewGroup parent, View view, int viewType) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position, String title) {

        holder.titleTv.setText(title);
        holder.checkBox.setTag(title);
        if (selected != null && selected.contains(title))
            holder.checkBox.setChecked(true);
        else
            holder.checkBox.setChecked(false);

        holder.layout.setTag(holder.checkBox);
        holder.layout.setOnClickListener(v -> {
            CheckBox checkBox = (CheckBox) v.getTag();
            checkBox.setChecked(!checkBox.isChecked());
            if (checkBox.isChecked())
                selected.add((String) checkBox.getTag());
            else
                selected.remove((String) checkBox.getTag());
        });

    }

    public List<String> getMultiple() {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            for (int j = 0; j < selected.size(); j++) {
                if(getData(i).equals(selected.get(j))){
                    list.add(selected.get(j));
                    break;
                }
            }
        }

        return list;
    }

    @Override
    public void setList(List<String> list) {
        Collections.sort(list, new Comparator<String>() {
            String[] weeks = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

            @Override
            public int compare(String o1, String o2) {
                List<String> list = Arrays.asList(weeks);
                int index1 = list.indexOf(o1);
                int index2 = list.indexOf(o2);
                if (index1 < index2)
                    return -1;
                return 0;
            }
        });
        super.setList(list);
    }

    class ItemViewHolder extends BaseViewHolder {

        public View layout;
        public TextView titleTv;
        public CheckBox checkBox;

        public ItemViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout1);
            titleTv = itemView.findViewById(R.id.textView1);
            checkBox = itemView.findViewById(R.id.checkBox1);
        }
    }
}
