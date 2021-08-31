package com.oppo.marketdemo.interpolator;

import android.util.Log;
import android.view.animation.Interpolator;

/**
 * Copyright (C), 2003-2020, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2020/6/19 11:20
 * Description:
 */
public class MyInterpolator implements Interpolator {

    private float mCenterT = 0.4f;
    private float mCenterF = 0.9f;

    public MyInterpolator(){}

    public MyInterpolator(float centerT, float centerF){
        mCenterT = centerT;
        mCenterF = centerF;
    }

    private static float bounce(float t) {
        return t * t;
    }

    private static float bounce2(float t) {
        return (float)Math.sin(2 * Math.PI * t);
    }

    @Override
    public float getInterpolation(float input) {
        if (input < mCenterT){
            return bounce(mCenterF / mCenterT * input) / bounce(mCenterF);
        }else{
            return (bounce2((input - mCenterT) / (1f - mCenterT)) / 2f + 1f) * (1f - bounce(mCenterF)) + bounce(mCenterF);
        }
    }
}
