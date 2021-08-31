package com.oppo.marketdemo.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/5/7 19:00
 * Description:
 */
public class FlingRecyclerView extends RecyclerView {

    public FlingRecyclerView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        return super.fling(velocityX * 2 / 3, velocityY * 2 / 3);
    }
}