package com.js.smart.ui.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
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
public abstract class SearchAdapter<T> extends BaseAdapterI<T> implements Filterable {

    private SearchType type = SearchType.A;
    public enum SearchType{
        A,B,C,D,E,F,G,H,I,J,K
    }
    private SearchFilter searchFilter;

    public SearchAdapter(AppCompatActivity context, List<T> data) {
        super(context, data, R.layout.item_text_search);
    }

    protected abstract void getView(TextView textView, T item, SearchType type);

   protected abstract T getItem(SearchType type, T item);

    @Override
    protected View getView(int position, View convertView, ViewGroup parent, T item) {
        TextView textView = ViewHolder.get(convertView,android.R.id.text1);

        getView(textView, item, type);
        return convertView;
    }

    @Override
    public T getItem(int position) {
        return getItem(type, super.getItem(position));
    }

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

}
