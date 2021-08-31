package com.oppo.marketdemo.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/17 16:33
 * Description:
 */
public class FitXYScrollView extends ScrollView {

    public FitXYScrollView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        scrollTo(0, (int)(getScrollY() * 1f * w / oldw));
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
    }

    private boolean isTouch;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_MOVE:
                isTouch = true;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean scrollSpeed;
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (Math.abs(t - oldt) < 40){
            if (scrollSpeed) {
                Log.e("FitXYScrollView", "t = " + t);
                Log.e("FitXYScrollView", "oldt = " + oldt);
                scrollSpeed = false;
                scrollToRecentChild();
            }
        }else {
            if (isTouch) {
                isTouch = false;
                scrollSpeed = true;
            }
        }
    }

    private void scrollToRecentChild(){
        Log.e("FitXYScrollView", "scrollToRecentChild");
        if (getScrollY() + getMeasuredHeight() >= getChildAt(0).getMeasuredHeight() - ((LinearLayout)getChildAt(0)).getChildAt(((LinearLayout)getChildAt(0)).getChildCount() - 1).getMeasuredHeight() / 3){
            smoothScrollTo(0, getChildAt(0).getMeasuredHeight() - getMeasuredHeight());
            Log.e("FitXYScrollView", "1");
        }else {
            int oldHeightCount = 0;
            int heightCount = 0;
            int i = 0;
            while (getScrollY() > heightCount && i < ((LinearLayout)getChildAt(0)).getChildCount() - 1){
                heightCount += ((LinearLayout)getChildAt(0)).getChildAt(i).getMeasuredHeight();
                if (heightCount >= getScrollY()){
                    if (heightCount - getScrollY() < ((LinearLayout)getChildAt(0)).getChildAt(i).getMeasuredHeight() / 2){
                        smoothScrollTo(0, heightCount);
                        Log.e("FitXYScrollView", "2 heightCount = " + heightCount);
                    }else {
                        smoothScrollTo(0, oldHeightCount);
                        Log.e("FitXYScrollView", "3 oldHeightCount = " + oldHeightCount);
                    }
                }
                oldHeightCount = heightCount;
                i++;
            }
        }
        Log.e("FitXYScrollView", "getScrollY() = " + getScrollY());
    }
}