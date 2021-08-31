package com.oppo.marketdemo.interpolator;

import android.os.Build;
import android.view.animation.BaseInterpolator;

import androidx.annotation.RequiresApi;

/**
 * Created by Bruce on 2016/12/28.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
public class MinSinInterpolator extends BaseInterpolator {
    @Override
    public float getInterpolation(float input) {
        return (float) (Math.sin(2 * Math.PI * input) / 3) + 1;
    }
}
