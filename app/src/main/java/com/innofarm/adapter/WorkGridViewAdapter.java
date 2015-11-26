package com.innofarm.adapter;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innofarm.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;


/**
 * /**
 * User: syc
 * Date: 2015/9/9
 * Time: 17:28
 * Email: ycshi@isoftstone.com
 * Desc:
 */

public class WorkGridViewAdapter extends BaseAdapter {

    private ArrayList<String> alist ;
    private Context context;

   // @ViewInject(R.id.adapter_workgrid_item)
    private TextView item;

    public WorkGridViewAdapter(Context context,ArrayList list){

        this.context = context;
        this.alist = list;


    }



    @Override
    public int getCount() {
        return alist.size();
    }

    @Override
    public Object getItem(int i) {
        return alist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = View.inflate(context, R.layout.adapter_workgrid,null);
       // ViewUtils.inject(context,view);

     item = (TextView) view.findViewById(R.id.adapter_workgrid_item);


        item.setText(alist.get(i));

        return view;
    }
}
