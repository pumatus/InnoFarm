package com.innofarm.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.innofarm.external.ViewHolder;




/**
 * User: syc
 * Date: 2015/9/9
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc: （继承BaseAdapter）Adapter 基类
 */

public abstract class Adapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    public Adapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }



    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,  parent);

        //抽取
        convert(viewHolder, getItem(position));

        return viewHolder.getConvertView();

    }

    /**
    * 填充View
    */
    public abstract void convert(ViewHolder helper, T item);



    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }

}
