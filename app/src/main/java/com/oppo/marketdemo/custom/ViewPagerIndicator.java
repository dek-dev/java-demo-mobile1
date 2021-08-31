package com.oppo.marketdemo.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.oppo.marketdemo.R;

/**
 * Copyright (C), 2003-2019, 深圳市图派科技有限公司
 * Author: szm
 * Date: 2016/7/25
 * Description: ViewPager的指示器
 */

public class ViewPagerIndicator extends LinearLayout {

    private int interval = 35;
    private int sum=0;
    private int selected=0;
    private Context context;
//    private int selected_id= R.mipmap.point_selected, unselected_id=R.mipmap.point_normal;
    private int selected_id= R.drawable.drawable_viewpager_checked, unselected_id= R.drawable.drawable_viewpager_normal;

    public ViewPagerIndicator(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(){
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    public void setLength(int sum){
        this.sum=sum;
        this.selected = 0;
        draw();
    }

    public void setSelected(int selected){
        removeAllViews();
        this.selected=sum==0?0:selected%sum;
        draw();
    }

    public void setSelected(int selected, int selected_id, int unselected_id){
        removeAllViews();
        this.selected_id = selected_id;
        this.unselected_id = unselected_id;
        this.selected=sum==0?0:selected%sum;
        draw();
    }

    public void draw(){
        for(int i=0;i<sum;i++){
            ImageView imageview=new ImageView(context);
            imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i == 0){
                layoutParams.setMargins(0,0,0,0);//4个参数按顺序分别是左上右下
            }else{
                layoutParams.setMargins(interval,0,0,0);//4个参数按顺序分别是左上右下
            }
            imageview.setLayoutParams(layoutParams);
            if(i==selected){
                imageview.setImageResource(selected_id);
            }else{
                imageview.setImageResource(unselected_id);
            }
            addView(imageview);
        }
    }

    public float getDistance(){
        return getChildAt(1).getX()-getChildAt(0).getX();
    }

    public int getSelected(){
        return selected;
    }

    public int getSum(){
        return sum;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
