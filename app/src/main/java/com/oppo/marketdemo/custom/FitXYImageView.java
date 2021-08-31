package com.oppo.marketdemo.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/17 16:20
 * Description:
 */
public class FitXYImageView extends AppCompatImageView {

    private float fit = 1f;

    public FitXYImageView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = (int)(width / fit);
        setMeasuredDimension(width, height);
    }

    public void setFit(float fit) {
        this.fit = fit;
    }
}
