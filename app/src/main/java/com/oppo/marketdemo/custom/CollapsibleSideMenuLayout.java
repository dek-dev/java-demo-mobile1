package com.oppo.marketdemo.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.oppo.marketdemo.R;


/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/4/17 10:53
 * Description: 可收起的侧边菜单
 */
public class CollapsibleSideMenuLayout extends FrameLayout {

    private ValueAnimator maxToMinAnimator, minToMaxAnimator;
    private FrameLayout.LayoutParams maxParams, minParams;

    private RadioGroup childMax;
    private RadioGroup childMin;

    private float curWidth;
    private float mWidthMax;
    private float mWidthMin;
    private boolean isMax;
    private boolean isChanging;

    public CollapsibleSideMenuLayout(Context context) {
        this(context, null);
    }

    public CollapsibleSideMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsibleSideMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CollapsibleSideMenuLayout);
        if (typedArray != null) {
            mWidthMax = typedArray.getDimension(R.styleable.CollapsibleSideMenuLayout_layout_width_max, 1);
            mWidthMin = typedArray.getDimension(R.styleable.CollapsibleSideMenuLayout_layout_width_min, 0);
            typedArray.recycle();
        }
        childMax = new RadioGroup(context);
        maxParams = new FrameLayout.LayoutParams((int)mWidthMin, ViewGroup.LayoutParams.MATCH_PARENT);
        childMax.setLayoutParams(maxParams);
        childMax.setGravity(Gravity.CENTER_HORIZONTAL);
        childMax.setOrientation(LinearLayout.VERTICAL);
        childMax.setAlpha(0f);
        childMax.setVisibility(GONE);

        childMin = new RadioGroup(context);
        minParams = new FrameLayout.LayoutParams((int)mWidthMin, ViewGroup.LayoutParams.MATCH_PARENT);
        childMin.setLayoutParams(minParams);
        childMin.setGravity(Gravity.CENTER_HORIZONTAL);
        childMin.setOrientation(LinearLayout.VERTICAL);
        addView(childMin);
        addView(childMax);
        maxToMinAnimator = ValueAnimator.ofFloat(0f, 1f);
        maxToMinAnimator.setDuration(300);
        maxToMinAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isChanging = true;
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                isChanging = false;
                if (childMax != null) {
                    maxParams.width = (int) mWidthMin;
                    childMax.setLayoutParams(maxParams);
                    childMax.setAlpha(0f);
                    childMax.setVisibility(GONE);
                }
                if (mOnChangeListener != null){
                    mOnChangeListener.end();
                }
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                isChanging = false;
                if (mOnChangeListener != null){
                    mOnChangeListener.cancel();
                }
            }
            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        maxToMinAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                if (childMax != null) {
                    maxParams.width = (int) (mWidthMin + (curWidth - mWidthMin) * (1 - value));
                    childMax.setLayoutParams(maxParams);
                    if (value <= 0.25f) {
                        childMax.setAlpha(1f - value * 4f);
                        childMax.setVisibility(VISIBLE);
                    } else {
                        childMax.setAlpha(0f);
                    }
                }
            }
        });
        minToMaxAnimator = ValueAnimator.ofFloat(0f, 1f);
        minToMaxAnimator.setDuration(500);
        minToMaxAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isChanging = true;
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                isChanging = false;
                if (childMax != null) {
                    maxParams.width = (int) mWidthMax;
                    childMax.setLayoutParams(maxParams);
                    childMax.setAlpha(1f);
                    childMax.setVisibility(VISIBLE);
                }
                if (mOnChangeListener != null){
                    mOnChangeListener.end();
                }
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                isChanging = false;
                if (mOnChangeListener != null){
                    mOnChangeListener.cancel();
                }
            }
            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        minToMaxAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                if (childMax != null) {
                    maxParams.width = (int) (curWidth + (mWidthMax - curWidth) * value);
                    childMax.setLayoutParams(maxParams);
                    if (value >= 0.75f) {
                        childMax.setAlpha((value - 0.75f) * 4f);
                        childMax.setVisibility(VISIBLE);
                    } else {
                        childMax.setAlpha(0f);
                        childMax.setVisibility(VISIBLE);
                    }
                }
            }
        });
    }

    public void setChildMaxChildren(View... views) {
        childMax.removeAllViews();
        for (View view : views){
            childMax.addView(view);
        }
    }

    public void setChildMinChildren(View... views) {
        childMin.removeAllViews();
        for (View view : views){
            childMin.addView(view);
        }
    }

    public void changeStatus(){
        curWidth = childMax.getMeasuredWidth();
        if (isMax){
            if (minToMaxAnimator != null) {
                minToMaxAnimator.cancel();
            }
            if (maxToMinAnimator != null) {
                maxToMinAnimator.cancel();
                maxToMinAnimator.start();
                if (mOnChangeListener != null){
                    mOnChangeListener.start();
                }
            }
        }else {
            if (isChanging){
                return;
            }
            if (minToMaxAnimator != null) {
                minToMaxAnimator.cancel();
                minToMaxAnimator.start();
                if (mOnChangeListener != null){
                    mOnChangeListener.start();
                }
            }
        }
        isMax = !isMax;
    }

    public boolean isMax() {
        return isMax;
    }

    public boolean isChanging() {
        return isChanging;
    }

    public interface OnChangeListener{
        void start();
        void end();
        void cancel();
    }

    private OnChangeListener mOnChangeListener;

    public void setOnChangeListener(OnChangeListener mOnChangeListener) {
        this.mOnChangeListener = mOnChangeListener;
    }
}
