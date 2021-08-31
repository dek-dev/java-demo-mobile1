package com.oppo.marketdemo.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.oppo.marketdemo.R;


public class PointIndicatorView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);

    int size = 4;
    int pos = 0;
    int space = 55;
    int wide = 12;
    int leg = 40;

    public PointIndicatorView(Context context) {
        super(context);
    }

    public PointIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PointIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setColor(getResources().getColor(R.color.colorBlackCC111111, null));
        paint2.setColor(getResources().getColor(R.color.colorBlack4D111111, null));
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = 0;
        RectF tip;

        for (int i = 0; i < size; i++) {
            if (pos == i) {
                tip = new RectF(0, x, 12, x + leg);
                canvas.drawRoundRect(tip, 12, 12, paint);
                x = x + leg + space;
            } else {
                tip = new RectF(0, x, 12, x + wide);
                canvas.drawRoundRect(tip, 12, 12, paint2);
                x = x + wide + space;
            }

        }

    }

    public void setBlack() {
        paint.setColor(getResources().getColor(R.color.colorBlackCC111111, null));
        paint2.setColor(getResources().getColor(R.color.colorBlack4D111111, null));
        invalidate();
    }

    public void setWhite() {
        paint.setColor(getResources().getColor(R.color.colorWhiteCCFFFFFF, null));
        paint2.setColor(getResources().getColor(R.color.colorWhite4DFFFFFF, null));
        this.pos = pos;
        invalidate();
    }

    public void setpos(int pos) {
        this.pos = pos;
        invalidate();
    }

    public void setzise(int size) {
        this.size = size;
        invalidate();
    }

}
