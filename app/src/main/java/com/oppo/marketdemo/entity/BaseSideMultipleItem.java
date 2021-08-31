package com.oppo.marketdemo.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author Neo
 * 侧边栏Multi类
 */
public class BaseSideMultipleItem implements MultiItemEntity {
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_CONTENT = 2;
    //类型
    private int itemType;
    //item内容
    private int mItemContent;
    //是否选中
    private boolean isDot;

    public BaseSideMultipleItem(int itemType, int mItemContent, boolean isDot) {
        this.itemType = itemType;
        this.mItemContent = mItemContent;
        this.isDot = isDot;
    }

    public int getmItemContent() {
        return mItemContent;
    }

    public void setmItemContent(int mItemContent) {
        this.mItemContent = mItemContent;
    }

    public boolean isDot() {
        return isDot;
    }

    public void setDot(boolean dot) {
        isDot = dot;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
