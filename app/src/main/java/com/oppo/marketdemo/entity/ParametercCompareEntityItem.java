package com.oppo.marketdemo.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author Neo
 * 参数实体类
 */
public class ParametercCompareEntityItem implements MultiItemEntity {
    public static final int TYPE_Head = 0;
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_CONTENT = 2;
    public static final int TYPE_OTHER = 3;

    //类型
    private int itemType;
    private int mItemTitle;
    private int mItemContent;
    private int mItemContent1;

    public int getmItemContent1() {
        return mItemContent1;
    }

    public void setmItemContent1(int mItemContent1) {
        this.mItemContent1 = mItemContent1;
    }

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


    public ParametercCompareEntityItem(int itemType, int mItemTitle, int mItemContent, int mItemContent1) {
        this.itemType = itemType;
        this.mItemTitle = mItemTitle;
        this.mItemContent = mItemContent;
        this.mItemContent1 = mItemContent1;
    }


    @Override
    public int getItemType() {
        return itemType;
    }
}
