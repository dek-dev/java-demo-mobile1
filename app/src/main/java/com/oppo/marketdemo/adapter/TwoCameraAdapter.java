package com.oppo.marketdemo.adapter;


import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.oppo.marketdemo.R;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;

/**
 * @author Neo
 * 类名：TwoExpAdapter
 * 第二级体验适配器
 */
public class TwoCameraAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    LinearLayout llItemBg;
    TypefaceTextView tvTitle;
    TypefaceTextView tvContent;
    private final int EXP_ONE = 0;
    private final int EXP_TWO = 1;
    private final int EXP_THREE = 2;
    private final int EXP_FOUR= 3;
    private final int EXP_FIVE = 4;

    public TwoCameraAdapter() {
        super(R.layout.two_exp_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer dataNum) {
        llItemBg = helper.itemView.findViewById(R.id.ll_item_bg);
        tvTitle = helper.itemView.findViewById(R.id.tv_title);;
        tvContent = helper.itemView.findViewById(R.id.tv_content);;

        if (null != dataNum) {
            switch (dataNum) {
                case EXP_ONE:
                    llItemBg.setBackgroundResource(R.mipmap.two_camera_1);
                    tvTitle.setText(R.string.secondary_point8);
                    tvContent.setText(R.string.secondary_point8);
                    tvContent.setVisibility(View.GONE);
                    break;
                case EXP_TWO:
                    llItemBg.setBackgroundResource(R.mipmap.two_camera_2);
                    tvTitle.setText(R.string.secondary_point9);
                    tvContent.setText(R.string.secondary_point9);
                    tvContent.setVisibility(View.GONE);
                    break;
                case EXP_THREE:
                    llItemBg.setBackgroundResource(R.mipmap.two_camera_3);
                    tvTitle.setText(R.string.secondary_point10);
                    tvContent.setText(R.string.secondary_point10);
                    tvContent.setVisibility(View.GONE);
                    break;
                case EXP_FOUR:
                    llItemBg.setBackgroundResource(R.mipmap.two_camera_4);
                    tvTitle.setText(R.string.secondary_point11);
                    tvContent.setText(R.string.secondary_point11);
                    tvContent.setVisibility(View.GONE);
                    break;
                case EXP_FIVE:
                    llItemBg.setBackgroundResource(R.mipmap.two_camera_5);
                    tvTitle.setText(R.string.secondary_point12);
                    tvContent.setText(R.string.secondary_point12);
                    tvContent.setVisibility(View.GONE);
                    break;
                default:
            }
        }
    }
}
