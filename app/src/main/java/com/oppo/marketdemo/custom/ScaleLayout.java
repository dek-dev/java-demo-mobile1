package com.oppo.marketdemo.custom;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.oppo.marketdemo.interpolator.MinSinInterpolator;

/**
 * Created by szm on 2018/7/13.
 */

public class ScaleLayout extends FrameLayout {

    private long touchTime;

    public ScaleLayout(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchTime = SystemClock.uptimeMillis();
                actionDownAnimator();
                break;
            case MotionEvent.ACTION_UP:
                actionUpAnimator();
                if (SystemClock.uptimeMillis() - touchTime > 1000){
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                actionCancelAnimator();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void actionDownAnimator() {
        View child = getChildAt(0);
        if (child != null){
            child.setPivotX(child.getLeft() + child.getMeasuredWidth() / 2f);
            child.setPivotY(child.getTop() + child.getMeasuredHeight() / 2f);
            child.animate().scaleX(0.85f).scaleY(0.85f).setInterpolator(new DecelerateInterpolator()).setDuration(200).start();
        }
    }

    private void actionUpAnimator() {
        View child = getChildAt(0);
        if (child != null) {
            child.setPivotX(child.getLeft() + child.getMeasuredWidth() / 2f);
            child.setPivotY(child.getTop() + child.getMeasuredHeight() / 2f);
            child.animate().scaleX(1.0f).scaleY(1.0f).setInterpolator(new MinSinInterpolator()).setDuration(200).start();
        }
    }

    private void actionCancelAnimator() {
        View child = getChildAt(0);
        if (child != null) {
            child.setPivotX(child.getLeft() + child.getMeasuredWidth() / 2f);
            child.setPivotY(child.getTop() + child.getMeasuredHeight() / 2f);
            child.animate().scaleX(1.0f).scaleY(1.0f).setInterpolator(new DecelerateInterpolator()).setDuration(500).start();
        }
    }
}
