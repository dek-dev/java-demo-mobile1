package com.oppo.marketdemo.utils;

import android.content.Context;

import androidx.recyclerview.widget.LinearSmoothScroller;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/20 18:11
 * Description:
 */
public class TopSmoothScroller extends LinearSmoothScroller {

    public TopSmoothScroller(Context context) {
        super(context);
    }
    @Override
    protected int getHorizontalSnapPreference() {
        return SNAP_TO_START;
    }
    @Override
    protected int getVerticalSnapPreference() {
        return SNAP_TO_START;  // 将子view与父view顶部对齐
    }
}