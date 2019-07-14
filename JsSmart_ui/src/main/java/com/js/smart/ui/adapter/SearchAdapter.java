package com.js.smart.ui.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.js.smart.common.app.adapter.BaseAdapterI;
import com.js.smart.common.util.ViewHolder;
import com.js.smart.ui.R;

import java.util.List;


/**
 * Created by Js on 2016/5/13.
 */
public abstract class SearchAdapter<T> extends BaseAdapter implements Filterable {

    private SearchType type = SearchType.A;
    public enum SearchType{
        A,B,C,D,E,F,G,H,I,J,K
    }
    private SearchFilter searchFilter;

    public SearchAdapter(AppCompatActivity context, List<T> data) {
        this(context, data, R.layout.item_text_search);
    }

    protected abstract void getView(TextView textView, T item, SearchType type);

   protected abstract Object getItem(SearchType type, T item);

    public void setType(SearchType type) {
        this.type = type;
    }

    @Override
    public Filter getFilter() {
        if (searchFilter == null) {
            searchFilter = new SearchFilter();
        }
        return searchFilter;
    }

    public boolean isType(SearchType type){
        if (type.name().equals(type.name()))
            return true;
        return false;
    }

    class SearchFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            // 持有过滤操作完成之后的数据。该数据包括过滤操作之后的数据的值以及数量。 count:数量 values包含过滤操作之后的数据的值
            FilterResults results = new FilterResults();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
            // 重新将与适配器相关联的List重赋值一下
        }

    }

    /**
     * BaseAdapterI
     */
    protected Context context;
    protected List<T> data;
    protected int layoutId;
    protected BaseAdapterI.OnItemClickListener itemClickListener;

    public SearchAdapter(Context context, List<T> data, int layoutId) {
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
    }

    public SearchAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
    }

    private SearchAdapter() {
    }

    @Override
    public int getCount() {
        if (data != null)
            return data.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (data != null) {
            return getItem(type, data.get(position));
        }
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

        TextView textView = ViewHolder.get(convertView,android.R.id.text1);

        getView(textView, data.get(position), type);
        return convertView;
    }

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

    public void setItemClickListener(BaseAdapterI.OnItemClickListener itemClickListener) {
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
