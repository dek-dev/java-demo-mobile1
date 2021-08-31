package com.oppo.marketdemo.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.oppo.marketdemo.R;
import com.oppo.marketdemo.globle.VApplication;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/17 15:26
 * Description:
 */
public class ListImageAdapter extends BaseAdapter {

    private Context mContext;
    private SimpleDraweeView[] simpleDraweeViews;
    private int[] imagesId;
    public ListImageAdapter(Context context, int[] images){
        mContext = context;
        simpleDraweeViews = new SimpleDraweeView[images.length];
        imagesId = images;
    }

    @Override
    public int getCount() {
        return simpleDraweeViews.length;
    }

    @Override
    public Object getItem(int position) {
        if (simpleDraweeViews[position] == null){
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            simpleDraweeViews[position] = new SimpleDraweeView(mContext);
            simpleDraweeViews[position].setLayoutParams(params);
            simpleDraweeViews[position].setAspectRatio(0.9685f);
            simpleDraweeViews[position].setImageURI(Uri.parse("res://" + mContext.getPackageName() + "/" + imagesId[position]));
        }
        return simpleDraweeViews[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return (SimpleDraweeView)getItem(position);
    }
}
