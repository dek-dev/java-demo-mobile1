package com.oppo.marketdemo.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class CustomDrawerLayout extends DrawerLayout {

    public CustomDrawerLayout(Context context) {
        this(context, null);
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch(ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                final float x = ev.getX();
                final float y = ev.getY();
                final View touchedView = findTopChildUnder((int) x, (int) y);
                if (touchedView != null && isContentView(touchedView)
                        && this.isDrawerOpen(GravityCompat.START)) {
                    return false;
                }
                break;

            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 判断点击位置是否位于相应的View内
     *
     * @param x x坐标
     * @param y y坐标
     * @return 返回当前view
     */
    public View findTopChildUnder(int x, int y) {
        final int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--){
            final View child = getChildAt(i);
            if (x >= child.getLeft() && x < child.getRight() && y >= child.getTop() && y < child.getBottom()) {
                return child;
            }
        }
        return null;
    }

    /**
     * 判断点击触摸点的View是否是ContentView(即是主页面的View)
     *
     * @param child 子view
     * @return boolean
     */
    boolean isContentView(View child) {
        return ((LayoutParams) child.getLayoutParams()).gravity == Gravity.NO_GRAVITY;
    }

    /**
     * 判断点击触摸点的View是否是DrawerView(即是侧边栏的View)
     *
     * @param child 子view
     * @return boolean
     */
    boolean isDrawerView(View child) {
        final int gravity = ((LayoutParams) child.getLayoutParams()).gravity;
        final int absGravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(child));
        if ((absGravity & Gravity.LEFT) != 0) {
// This child is a left-edge drawer
            return true;
        }
        // This child is a right-edge drawer
        return (absGravity & Gravity.RIGHT) != 0;
    }
}
