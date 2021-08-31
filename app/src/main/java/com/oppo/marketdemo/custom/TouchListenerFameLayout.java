package com.oppo.marketdemo.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

/**
 * Created by szm on 2019/2/27.
 */

public class TouchListenerFameLayout extends FrameLayout {

    public TouchListenerFameLayout(Context context) {
        this(context, null);
    }

    public TouchListenerFameLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (mTouchListener != null){
                    mTouchListener.touchDown();
                }
                Log.e(getClass().getName(), "motionEvent.getRawX() = " + ev.getRawX() + "; motionEvent.getRawY() = " + ev.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mTouchListener != null){
                    mTouchListener.touchUp();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    public interface TouchListener{
        void touchDown();
        void touchUp();
    }

    private TouchListener mTouchListener;

    public void setTouchListener(TouchListener touchListener){
        mTouchListener = touchListener;
    }
}
