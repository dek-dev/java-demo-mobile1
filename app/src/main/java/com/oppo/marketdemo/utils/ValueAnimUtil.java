package com.oppo.marketdemo.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/1/6 15:28
 * Description: 属性动画工具类
 */
public class ValueAnimUtil {

    private static final int AnimatorDuration = 300;

    private static HashMap<String, ValueAnimator> mapShow = new HashMap<>();
    private static HashMap<String, ValueAnimator> mapHide = new HashMap<>();

    public static void showViews(String key, View... views){
        showViews(key, AnimatorDuration, null, null, views);
    }

    public static void showViews(String key, CallBack callBack, View... views){
        showViews(key, AnimatorDuration, null, callBack, views);
    }

    public static void showViews(String key, long duration, CallBack callBack, View... views){
        showViews(key, duration, null, callBack, views);
    }

    public static void showViews(String key, Interpolator interpolator, CallBack callBack, View... views){
        showViews(key, AnimatorDuration, interpolator, callBack, views);
    }

    public static void showViews(final String key, long duration, Interpolator interpolator, final CallBack callBack, final View... views){
        if (views != null && views.length > 0){
            final ValueAnimator mShowValueAnimator = ValueAnimator.ofFloat(0f, 1f);
            final ArrayList<Float> floats = new ArrayList<>();
            for (View view:views){
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                    floats.add(view.getAlpha());
                }
            }
            mShowValueAnimator.setDuration(duration);
            if (interpolator != null){
                mShowValueAnimator.setInterpolator(interpolator);
            }else {
                mShowValueAnimator.setInterpolator(new LinearInterpolator());
            }
            mShowValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float) valueAnimator.getAnimatedValue();
                    for (int i=0; i<views.length; i++){
                        View view = views[i];
                        if (view != null) {
                            view.setVisibility(View.VISIBLE);
                            view.setAlpha(floats.get(i) + value * (1 - floats.get(i)));
                        }
                    }
                }
            });
            mShowValueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }
                @Override
                public void onAnimationEnd(Animator animator) {
                    if (callBack != null){
                        callBack.showEnd(key);
                    }
                    mapShow.remove(key);
                }
                @Override
                public void onAnimationCancel(Animator animator) {
                    if (callBack != null){
                        callBack.showEnd(key);
                    }
                    mapShow.remove(key);
                }
                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            mShowValueAnimator.start();
            if (mapShow.containsKey(key)){
                cancelShow(key);
            }
            mapShow.put(key, mShowValueAnimator);
        }
    }

    public static void cancelShow(String key){
        if (mapShow.containsKey(key)) {
            ValueAnimator valueAnimator = mapShow.get(key);
            if (valueAnimator != null){
                valueAnimator.cancel();
            }
            mapShow.remove(key);
        }
    }

    public static void hideViews(String key, View... views){
        hideViews(key, AnimatorDuration, null, null, views);
    }

    public static void hideViews(String key, CallBack callBack, View... views){
        hideViews(key, AnimatorDuration, null, callBack, views);
    }

    public static void hideViews(String key, long duration, CallBack callBack, View... views){
        hideViews(key, duration, null, callBack, views);
    }

    public static void hideViews(String key, Interpolator interpolator, CallBack callBack, View... views){
        hideViews(key, AnimatorDuration, interpolator, callBack, views);
    }

    public static void hideViews(final String key, long duration, Interpolator interpolator, final CallBack callBack, final View... views){
        if (views != null && views.length > 0){
            final ValueAnimator mHideValueAnimator = ValueAnimator.ofFloat(1f, 0f);
            final ArrayList<Float> floats = new ArrayList<>();
            for (View view:views){
                if (view != null) {
                    floats.add(view.getAlpha());
                }
            }
            mHideValueAnimator.setDuration(duration);
            if (interpolator != null){
                mHideValueAnimator.setInterpolator(interpolator);
            }else {
                mHideValueAnimator.setInterpolator(new LinearInterpolator());
            }
            mHideValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float) valueAnimator.getAnimatedValue();
                    for (int i=0; i<views.length; i++){
                        View view = views[i];
                        if (view != null) {
                            view.setAlpha(value * floats.get(i));
                            if (value == 0f){
                                view.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });
            mHideValueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }
                @Override
                public void onAnimationEnd(Animator animator) {
                    if (callBack != null){
                        callBack.hideEnd(key);
                    }
                    mapHide.remove(key);
                }
                @Override
                public void onAnimationCancel(Animator animator) {
                    if (callBack != null){
                        callBack.hideEnd(key);
                    }
                    mapHide.remove(key);
                }
                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            mHideValueAnimator.start();
            if (mapHide.containsKey(key)){
                cancelHide(key);
            }
            mapHide.put(key, mHideValueAnimator);
        }
    }

    public static void cancelHide(String key){
        if (mapHide.containsKey(key)) {
            ValueAnimator valueAnimator = mapHide.get(key);
            if (valueAnimator != null){
                valueAnimator.cancel();
            }
            mapHide.remove(key);
        }
    }

    public interface CallBack{
        void showEnd(String key);
        void hideEnd(String key);
    }
}
