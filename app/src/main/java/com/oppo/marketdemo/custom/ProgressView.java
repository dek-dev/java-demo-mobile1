package com.oppo.marketdemo.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.oppo.marketdemo.R;


/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/1/11 17:56
 * Description: 卖点进度
 */
public class ProgressView extends View {

    private float lineChecked = 0f;

    private Paint mPaint;

    private int mSum = 2;
    private int curPosition = 0;
    private boolean isWhiteType;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 10;
        int height = getMeasuredHeight();
        setMeasuredDimension(width, height);
        lineChecked = getMeasuredHeight() * 1f / mSum;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isWhiteType){
            mPaint.setColor(getContext().getColor(R.color.colorWhite4CFFFFFF));
        }else {
            mPaint.setColor(getContext().getColor(R.color.colorBlack4C000000));
        }
        canvas.drawLine(getMeasuredWidth() / 2f, 0, getMeasuredWidth() / 2f, getMeasuredHeight(), mPaint);

        if (isWhiteType){
            mPaint.setColor(getContext().getColor(R.color.colorWhite));
        }else {
            mPaint.setColor(getContext().getColor(R.color.colorBlack3));
        }
        canvas.drawLine(getMeasuredWidth() / 2f, lineChecked * (curPosition - 1), getMeasuredWidth() / 2f, lineChecked * curPosition, mPaint);
    }

    public void setSum(int mSum) {
        this.mSum = mSum;
        invalidate();
    }

    public void setCurPosition(int curPosition) {
        this.curPosition = curPosition;
        invalidate();
    }

    public void setWhiteType(boolean whiteType) {
        isWhiteType = whiteType;
        invalidate();
    }

    public void refreshProgressView(int sum, int position, boolean isWhite){
        setSum(sum);
        setCurPosition(position);
        setWhiteType(isWhite);
    }
}
