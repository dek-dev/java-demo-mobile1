package com.oppo.marketdemo.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author Neo
 * 参数实体类
 */
public class ParameterEntityItem implements MultiItemEntity {
    public static final int TYPE_Head = 0;
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_CONTENT = 2;
    public static final int TYPE_OTHER = 3;

    //类型
    private int itemType;
    private int mItemTitle;
    private int mItemContent;

    public int getmItemTitle() {
        return mItemTitle;
    }

    public void setmItemTitle(int mItemTitle) {
        this.mItemTitle = mItemTitle;
    }

    public int getmItemContent() {
        return mItemContent;
    }

    public void setmItemContent(int mItemContent) {
        this.mItemContent = mItemContent;
    }



    public ParameterEntityItem(int itemType, int mItemTitle, int mItemContent) {
        this.itemType = itemType;
        this.mItemTitle = mItemTitle;
        this.mItemContent = mItemContent;

    }



    @Override
    public int getItemType() {
        return itemType;
    }
}
