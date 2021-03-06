package com.oppo.marketdemo.utils;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.RequiresApi;

/**
 * @author Neo
 * 揭露动画工具类
 */
public class ViewCenterUtils {
    private static int mX;
    private static int mY;

    /**
     * 得到视图中心
     */
    public static int[] getViewCenter(View view) {
        int x ,y;
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        view.getLocationInWindow(location);
        x = location[0];
        y = location[1];
        x = x+view.getWidth()/2;
        y = y+view.getHeight()/2;
        int[] center = {x, y};
        return center;
    }

    /**
     * 设置开始动画
     * @param activity
     * @param view
     * @param intent
     */
    public static void setActivityStartAnim(final Activity activity, final View view, final Intent intent) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mX = intent.getIntExtra("x", 0);
                    mY = intent.getIntExtra("y", 0);
                    if (view != null) {
                        //对控件View进行判空，防止后台时间过长activity被回收后启动
                        Animator animator = createRevealAnimator(activity, view, false, mX, mY);
                        animator.start();
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static Animator createRevealAnimator(final Activity activity, final View view, boolean reversed, int x, int y) {
        int a = x;
        int b = y;
        int measuredHeight = view.getMeasuredHeight();
        int screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        if (screenWidth - x > x) {
            a = screenWidth - x;
        }
        if (screenHeight - y > y) {
            b = screenHeight - y;
        }
//        float hypot = (float) Math.hypot(screenWidth, screenHeight-measuredHeight);
//        float hypot = (float) Math.hypot(x, y);
        float hypot = (float) Math.hypot(a, b);
        float startRadius = reversed ? hypot : 0;
        float endRadius = reversed ? 0 : hypot;
        Animator animator = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, endRadius);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        if (reversed) {
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.INVISIBLE);
                    activity.finish();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
        return animator;
    }
}
