package com.oppo.marketdemo.adapter;


import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.oppo.marketdemo.R;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;

/**
 * @author Neo
 * 类名：TwoVideoAdapter
 * 第二级视频适配器
 */
public class TwoPerformanceAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    LinearLayout llItemBg;
    TypefaceTextView tvTitle;
    TypefaceTextView tvContent;
    private final int PHOTO_ONE = 0;
    private final int PHOTO_TWO = 1;
    private final int PHOTO_THREE = 2;
    private final int PHOTO_FOUR = 3;
    private final int PHOTO_FIVE = 4;



    public TwoPerformanceAdapter() {
        super(R.layout.two_photo_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer dataNum) {
        llItemBg = helper.itemView.findViewById(R.id.ll_item_bg);
        tvTitle = helper.itemView.findViewById(R.id.tv_title);
        tvContent = helper.itemView.findViewById(R.id.tv_content);

        if (null != dataNum) {
            switch (dataNum) {
                case PHOTO_ONE:
                    llItemBg.setBackgroundResource(R.mipmap.two_per_1);
                    tvTitle.setText(R.string.secondary_point1);
                    tvContent.setText(R.string.secondary_point1);
                    tvContent.setVisibility(View.GONE);
                    break;
                case PHOTO_TWO:
                    llItemBg.setBackgroundResource(R.mipmap.two_per_2);
                    tvTitle.setText(R.string.secondary_point2);
                    tvContent.setText(R.string.secondary_point2);
                    tvContent.setVisibility(View.GONE);
                    break;
                case PHOTO_THREE:
                    llItemBg.setBackgroundResource(R.mipmap.two_per_3);
                    tvTitle.setText(R.string.secondary_point3);
                    tvContent.setText(R.string.secondary_point3);
                    tvContent.setVisibility(View.GONE);
                    break;
                case PHOTO_FOUR:
                    llItemBg.setBackgroundResource(R.mipmap.two_per_4);
                    tvTitle.setText(R.string.secondary_point4);
                    tvContent.setText(R.string.secondary_point4);
                    tvContent.setVisibility(View.GONE);
                    break;
                case PHOTO_FIVE:
                    llItemBg.setBackgroundResource(R.mipmap.two_per_5);
                    tvTitle.setText(R.string.secondary_point4_1);
                    tvContent.setText(R.string.secondary_point4_1);
                    tvContent.setVisibility(View.GONE);
                    break;
                default:
            }
        }
    }
}
