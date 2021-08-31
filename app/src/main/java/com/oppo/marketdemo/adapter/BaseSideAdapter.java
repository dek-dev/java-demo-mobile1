package com.oppo.marketdemo.adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.oppo.marketdemo.R;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.entity.BaseSideMultipleItem;
import com.oppo.marketdemo.utils.AnimationOppoUtils;

import java.util.List;

/**
 * @author Neo
 * 侧边栏适配器
 */
public class BaseSideAdapter extends BaseMultiItemQuickAdapter<BaseSideMultipleItem, BaseViewHolder> {
    /**
     * 是否播放动画
     */
    private boolean isPlayAnimation = false;
    /**
     * 是否为打开动画
     */
    private boolean isOpen;
    private TypefaceTextView tvTitle, tvMenu;
    private View viewLine;
    private FrameLayout flScroll;
    private ImageView ivIcon;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BaseSideAdapter(List<BaseSideMultipleItem> data) {
        super(data);
        addItemType(BaseSideMultipleItem.TYPE_TITLE, R.layout.base_title_item);
        addItemType(BaseSideMultipleItem.TYPE_CONTENT, R.layout.base_content_item);
    }


    /**
     * 设置屏幕显示最后一个浮现动画
     *
     * @param isPlay
     */
    public void setPlayAnimation(boolean isPlay, boolean isOpen) {
        this.isPlayAnimation = isPlay;
        this.isOpen = isOpen;
    }

    @Override
    protected void convert(final BaseViewHolder helper, BaseSideMultipleItem item) {
        switch (helper.getItemViewType()) {
            case BaseSideMultipleItem.TYPE_TITLE:
                ivIcon = helper.itemView.findViewById(R.id.iv_icon);
                tvTitle = helper.itemView.findViewById(R.id.base_menu_title);
                viewLine = helper.itemView.findViewById(R.id.view_line);
                tvTitle.setText(item.getmItemContent());
                if (helper.getLayoutPosition() == 0) {
                    viewLine.setVisibility(View.GONE);
                } else {
                    viewLine.setVisibility(View.VISIBLE);
                }
                int iconInt = 0;
                switch (item.getmItemContent()) {
                    case R.string.main_selling_point1:
                        iconInt = R.mipmap.side_performance;
                        break;
                    case R.string.main_selling_point2:
                        iconInt = R.mipmap.side_exterior;
                        break;
                    case R.string.main_selling_point3:
                        iconInt = R.mipmap.side_video;
                        break;
                    case R.string.main_selling_point4:
                        iconInt = R.mipmap.side_set;
                        break;
                    case R.string.main_selling_point5:
                        iconInt = R.mipmap.side_accessories;
                        break;
                    default:
                }
                if (iconInt != 0) {
                    ivIcon.setImageResource(iconInt);
                }
                break;
            case BaseSideMultipleItem.TYPE_CONTENT:
                tvMenu = helper.itemView.findViewById(R.id.base_menu_text_title);
                flScroll = helper.itemView.findViewById(R.id.fl_scroll);

                tvMenu.setText(item.getmItemContent());
                if (item.isDot()) {
                    tvMenu.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlackf75d40));
                    tvMenu.setTypefaceStyle(3);
                    flScroll.setVisibility(View.VISIBLE);
                } else {
                    tvMenu.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack6));
                    tvMenu.setTypefaceStyle(1);
                    flScroll.setVisibility(View.INVISIBLE);
                }
                break;
            default:
        }
        if (isPlayAnimation) {
            setAnimationMethods(tvTitle, helper.getBindingAdapterPosition());
            setAnimationMethods(ivIcon, helper.getBindingAdapterPosition());
            setAnimationMethods(tvMenu, helper.getBindingAdapterPosition());
            setAnimationMethods(flScroll, helper.getBindingAdapterPosition());
        } else {
            refAnimationMethods(tvTitle);
            refAnimationMethods(ivIcon);
            refAnimationMethods(tvMenu);
            refAnimationMethods(flScroll);
        }
    }

    /**
     * 设置浮现动画
     */
    private void setAnimationMethods(View view, int position) {
        if (null == view) {
            return;
        }
        AnimationOppoUtils.orderInAnimation(view, position, isOpen);
    }

    private void refAnimationMethods(View view) {
        if (null == view) {
            return;
        }
        AnimationOppoUtils.ref(view);
    }
}
