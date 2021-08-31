package com.oppo.marketdemo.utils;

import android.view.View;

import com.oppo.marketdemo.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by szm on 2019/7/4.
 */

public class MyPageTransformer implements ViewPager.PageTransformer{
    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageHeight = page.getHeight();
        View baseLayout = page.findViewById(R.id.base_layout);
        View viewText = page.findViewById(R.id.text_layout);
        View viewTitle = page.findViewById(R.id.text_title);
        View viewContent = page.findViewById(R.id.text_content);
        if (position <= -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
        }else if (position > -1 && position <= 0){
            page.setTranslationY(-pageHeight * position);
            page.setTranslationZ(position);
        }else if (position >= 0 && position < 1){
            page.setTranslationZ(position);
        }else{ // (1,+Infinity]
        }
    }
}