package com.oppo.marketdemo.custom.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.globle.VApplication;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2018/6/26
 * Description: 自定义字体TextView
 */

public class TypefaceTextView extends AppCompatTextView {

    /**
     * 通用字体
     */
    private static String[] commonStyle = {"fonts/OPPOSans-S-R.ttf", "fonts/OPPOSans-S-R.ttf", "fonts/OPPOSans-S-M.ttf", "fonts/OPPOSans-S-B.ttf","fonts/OPPOSans-S-B.ttf"};
    private static Typeface[] fromAssets = new Typeface[5];

    static {
        fromAssets[0] = Typeface.createFromAsset(VApplication.getInstance().getAssets(), commonStyle[0]);
        fromAssets[1] = Typeface.createFromAsset(VApplication.getInstance().getAssets(), commonStyle[1]);
        fromAssets[2] = Typeface.createFromAsset(VApplication.getInstance().getAssets(), commonStyle[2]);
        fromAssets[3] = Typeface.createFromAsset(VApplication.getInstance().getAssets(), commonStyle[3]);
        fromAssets[4] = Typeface.createFromAsset(VApplication.getInstance().getAssets(), commonStyle[4]);

    }

    private int typefaceStyle;

    public TypefaceTextView(Context context) {
        this(context, null);
    }

    public TypefaceTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypefaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TypefaceTextView);
        if (typedArray != null) {
            typefaceStyle = typedArray.getInt(R.styleable.TypefaceTextView_typeface_style, 1);
            if (typefaceStyle > 4){
                typefaceStyle = 3;
            }
            if (typefaceStyle < 0){
                typefaceStyle = 0;
            }
            typedArray.recycle();
        }

        getPaint().setAntiAlias(true);
        setTypeface(fromAssets[typefaceStyle]);
    }

    public void setTypefaceStyle(int typefaceStyle) {
        this.typefaceStyle = typefaceStyle;
        getPaint().setAntiAlias(true);
        setTypeface(fromAssets[typefaceStyle]);
    }
}