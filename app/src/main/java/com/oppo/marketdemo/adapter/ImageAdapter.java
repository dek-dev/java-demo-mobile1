package com.oppo.marketdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2019/7/3
 * Description: ViewPager中展示图片的适配器
 */

public class ImageAdapter extends PagerAdapter {

    private List<View> views = new ArrayList<>();

    public ImageAdapter(Context context,@NonNull int[] images){
        if (images.length > 0) {
            for (int image : images) {
                ImageView imageView = new ImageView(context);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(image);
                views.add(imageView);
            }
        }
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup view, int position, @NonNull Object object) {
        view.removeView(views.get(position % views.size()));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        if (views.get(position % views.size()).getParent() == null) {
            view.addView(views.get(position % views.size()));
        }else {
            ((ViewGroup)views.get(position % views.size()).getParent()).removeView(views.get(position % views.size()));
            view.addView(views.get(position % views.size()));
        }
        return views.get(position % views.size());
    }
}
