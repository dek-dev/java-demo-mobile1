package com.oppo.marketdemo.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.oppo.marketdemo.R;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2019/12/31 18:03
 * Description: 自定义菜单按钮
 */
public class MenuView extends View {

    private Paint mPaint;
    private ValueAnimator mValueAnimator;

    private Line startLine1, startLine2, startLine3, curLine1, curLine2, curLine3, endLine1, endLine2, endLine3;

    private boolean isStartLine;
    private boolean animIsRunning;
    /**
     * 线条宽度
     */
    private float strokeWidth = 4.5f;
    private boolean isWriteColor;


    public MenuView(Context context) {
        this(context, null);
    }

    public MenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setColorState(boolean isWrite) {
        isWriteColor = isWrite;
    }

    private void init() {
        isStartLine = true;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(isWriteColor ? getContext().getColor(R.color.colorWhite) : getContext().getColor(R.color.colorBlack));
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.FILL);

        startLine1 = new Line();
        startLine2 = new Line();
        startLine3 = new Line();
        curLine1 = new Line();
        curLine2 = new Line();
        curLine3 = new Line();
        endLine1 = new Line();
        endLine2 = new Line();
        endLine3 = new Line();
        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.setDuration(300);

        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animIsRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animIsRunning = false;
                if (isStartLine) {
                    curLine1.a.x = endLine1.a.x;
                    curLine1.a.y = endLine1.a.y;
                    curLine1.b.x = endLine1.b.x;
                    curLine1.b.y = endLine1.b.y;
                    curLine2.a.x = endLine2.a.x;
                    curLine2.a.y = endLine2.a.y;
                    curLine2.b.x = endLine2.b.x;
                    curLine2.b.y = endLine2.b.y;
                    curLine3.a.x = endLine3.a.x;
                    curLine3.a.y = endLine3.a.y;
                    curLine3.b.x = endLine3.b.x;
                    curLine3.b.y = endLine3.b.y;
                } else {
                    curLine1.a.x = startLine1.a.x;
                    curLine1.a.y = startLine1.a.y;
                    curLine1.b.x = startLine1.b.x;
                    curLine1.b.y = startLine1.b.y;
                    curLine2.a.x = startLine2.a.x;
                    curLine2.a.y = startLine2.a.y;
                    curLine2.b.x = startLine2.b.x;
                    curLine2.b.y = startLine2.b.y;
                    curLine3.a.x = startLine3.a.x;
                    curLine3.a.y = startLine3.a.y;
                    curLine3.b.x = startLine3.b.x;
                    curLine3.b.y = startLine3.b.y;
                }
                isStartLine = !isStartLine;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                animIsRunning = false;
                if (isStartLine) {
                    curLine1.a.x = endLine1.a.x;
                    curLine1.a.y = endLine1.a.y;
                    curLine1.b.x = endLine1.b.x;
                    curLine1.b.y = endLine1.b.y;
                    curLine2.a.x = endLine2.a.x;
                    curLine2.a.y = endLine2.a.y;
                    curLine2.b.x = endLine2.b.x;
                    curLine2.b.y = endLine2.b.y;
                    curLine3.a.x = endLine3.a.x;
                    curLine3.a.y = endLine3.a.y;
                    curLine3.b.x = endLine3.b.x;
                    curLine3.b.y = endLine3.b.y;
                } else {
                    curLine1.a.x = startLine1.a.x;
                    curLine1.a.y = startLine1.a.y;
                    curLine1.b.x = startLine1.b.x;
                    curLine1.b.y = startLine1.b.y;
                    curLine2.a.x = startLine2.a.x;
                    curLine2.a.y = startLine2.a.y;
                    curLine2.b.x = startLine2.b.x;
                    curLine2.b.y = startLine2.b.y;
                    curLine3.a.x = startLine3.a.x;
                    curLine3.a.y = startLine3.a.y;
                    curLine3.b.x = startLine3.b.x;
                    curLine3.b.y = startLine3.b.y;
                }
                isStartLine = !isStartLine;
                invalidate();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (isStartLine) {
                    curLine1.a.x = (int) ((endLine1.a.x - startLine1.a.x) * value + startLine1.a.x);
                    curLine1.a.y = (int) ((endLine1.a.y - startLine1.a.y) * value + startLine1.a.y);
                    curLine1.b.x = (int) ((endLine1.b.x - startLine1.b.x) * value + startLine1.b.x);
                    curLine1.b.y = (int) ((endLine1.b.y - startLine1.b.y) * value + startLine1.b.y);
                    curLine2.a.x = (int) ((endLine2.a.x - startLine2.a.x) * value + startLine2.a.x);
                    curLine2.a.y = (int) ((endLine2.a.y - startLine2.a.y) * value + startLine2.a.y);
                    curLine2.b.x = (int) ((endLine2.b.x - startLine2.b.x) * value + startLine2.b.x);
                    curLine2.b.y = (int) ((endLine2.b.y - startLine2.b.y) * value + startLine2.b.y);
                    curLine3.a.x = (int) ((endLine3.a.x - startLine3.a.x) * value + startLine3.a.x);
                    curLine3.a.y = (int) ((endLine3.a.y - startLine3.a.y) * value + startLine3.a.y);
                    curLine3.b.x = (int) ((endLine3.b.x - startLine3.b.x) * value + startLine3.b.x);
                    curLine3.b.y = (int) ((endLine3.b.y - startLine3.b.y) * value + startLine3.b.y);
                } else {
                    curLine1.a.x = (int) ((endLine1.a.x - startLine1.a.x) * (1 - value) + startLine1.a.x);
                    curLine1.a.y = (int) ((endLine1.a.y - startLine1.a.y) * (1 - value) + startLine1.a.y);
                    curLine1.b.x = (int) ((endLine1.b.x - startLine1.b.x) * (1 - value) + startLine1.b.x);
                    curLine1.b.y = (int) ((endLine1.b.y - startLine1.b.y) * (1 - value) + startLine1.b.y);
                    curLine2.a.x = (int) ((endLine2.a.x - startLine2.a.x) * (1 - value) + startLine2.a.x);
                    curLine2.a.y = (int) ((endLine2.a.y - startLine2.a.y) * (1 - value) + startLine2.a.y);
                    curLine2.b.x = (int) ((endLine2.b.x - startLine2.b.x) * (1 - value) + startLine2.b.x);
                    curLine2.b.y = (int) ((endLine2.b.y - startLine2.b.y) * (1 - value) + startLine2.b.y);
                    curLine3.a.x = (int) ((endLine3.a.x - startLine3.a.x) * (1 - value) + startLine3.a.x);
                    curLine3.a.y = (int) ((endLine3.a.y - startLine3.a.y) * (1 - value) + startLine3.a.y);
                    curLine3.b.x = (int) ((endLine3.b.x - startLine3.b.x) * (1 - value) + startLine3.b.x);
                    curLine3.b.y = (int) ((endLine3.b.y - startLine3.b.y) * (1 - value) + startLine3.b.y);
                }
                isFirst = true;
                invalidate();
            }
        });
    }

    private boolean isFirst;
    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isFirst){
            setState();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(getContext().getColor(R.color.colorTransparent));
        if (null != mPaint) {
            mPaint.setColor(isWriteColor ? getContext().getColor(R.color.colorWhite) : getContext().getColor(R.color.colorBlack));
        }
        canvas.drawLine(curLine1.a.x, curLine1.a.y, curLine1.b.x, curLine1.b.y, mPaint);
        canvas.drawLine(curLine2.a.x, curLine2.a.y, curLine2.b.x, curLine2.b.y, mPaint);
        canvas.drawLine(curLine3.a.x, curLine3.a.y, curLine3.b.x, curLine3.b.y, mPaint);
    }

    public void startAnimatorMethods(){
        if (!animIsRunning && mValueAnimator != null) {
            mValueAnimator.cancel();
            mValueAnimator.start();
        }
    }

    /**
     * 设置状态
     */
    public void setState() {
        setCoordinateOpen(startLine1, startLine2, startLine3);
        setCoordinateOpen(curLine1, curLine2, curLine3);
        setCoordinateClose(endLine1, endLine2, endLine3);
    }

    /**
     * 开始为关闭状态
     *
     * @param Line1
     * @param Line2
     * @param Line3
     */
    private void setCoordinateClose(Line Line1, Line Line2, Line Line3) {
        Line1.a = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        Line1.b = new Point((int)(getMeasuredWidth() - strokeWidth), (int)strokeWidth);

        Line2.a = new Point((int)strokeWidth, (int)strokeWidth);
        Line2.b = new Point((int)(getMeasuredWidth() - strokeWidth), (int)(getMeasuredHeight() - strokeWidth));

        Line3.a = new Point((int)strokeWidth, (int)(getMeasuredHeight() - strokeWidth));
        Line3.b = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
    }

    /**
     * 开始为打开状态
     *
     * @param Line1
     * @param Line2
     * @param Line3
     */
    private void setCoordinateOpen(Line Line1, Line Line2, Line Line3) {
        Line1.a = new Point(getMeasuredWidth() / 3, (int)strokeWidth);
        Line1.b = new Point((int)(getMeasuredWidth() - strokeWidth), (int)strokeWidth);

        Line2.a = new Point((int)strokeWidth, getMeasuredHeight() / 2);
        Line2.b = new Point((int)(getMeasuredWidth() - strokeWidth), getMeasuredHeight() / 2);

        Line3.a = new Point((int)strokeWidth, (int)(getMeasuredHeight() - strokeWidth));
        Line3.b = new Point(getMeasuredWidth() * 2 / 3, (int)(getMeasuredHeight() - strokeWidth));
    }

    class Line {
        Point a;
        Point b;
    }
}
