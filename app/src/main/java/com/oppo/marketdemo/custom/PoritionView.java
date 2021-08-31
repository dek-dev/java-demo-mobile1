package com.oppo.marketdemo.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/1/2 17:04
 * Description: 左右对比样张
 */
public class PoritionView extends View {
    private Bitmap showPic = null;
    private Bitmap mPic = null;
    private int startX = 0;
    private int startY = 0;

    public PoritionView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public PoritionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //canvas.drawBitmap(showPic, startX, startY, null);
        //图片裁剪的核心功能
        if (startX < 0) {
            return;
        }
        if (startX >= showPic.getWidth()){
            return;
        }
        mPic = Bitmap.createBitmap(showPic,//原图
                startX,//图片裁剪横坐标开始位置
                0,//图片裁剪纵坐标开始位置
                showPic.getWidth() - startX,//要裁剪的宽度
                showPic.getHeight());//要裁剪的高度

        canvas.drawBitmap(mPic, startX, 0, null);
    }

    public void setBitmapShow(Bitmap b, int x) {
        showPic = b;
        startX = x;
        invalidate();
    }

    public void setPic(Bitmap mPic) {
        showPic = mPic;
        invalidate();
    }

    public void setStartX(int x){
        startX = x;
        invalidate();
    }
}
