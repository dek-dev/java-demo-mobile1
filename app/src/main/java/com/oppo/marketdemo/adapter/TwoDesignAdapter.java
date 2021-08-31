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
public class TwoDesignAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    LinearLayout llItemBg;
    TypefaceTextView tvTitle;
    TypefaceTextView tvContent;
    /**
     * 0防抖;1虚化;2即录;
     */
    private final int VIDEO_SHAKE = 0;
    private final int VIDEO_VIRTUAL = 1;
    private final int VIDEO_TAPE = 2;

    public TwoDesignAdapter() {
        super(R.layout.two_video_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer dataNum) {

        llItemBg = helper.itemView.findViewById(R.id.ll_item_bg);
         tvTitle = helper.itemView.findViewById(R.id.tv_title);;
         tvContent = helper.itemView.findViewById(R.id.tv_content);;

        if (null != dataNum) {
            switch (dataNum) {

                case VIDEO_SHAKE:
                    llItemBg.setBackgroundResource(R.mipmap.two_design_1);
                    tvTitle.setText(R.string.secondary_point5);
                    tvContent.setText(R.string.secondary_point5);
                    tvContent.setVisibility(View.GONE);
                    break;

                case VIDEO_VIRTUAL:
                    llItemBg.setBackgroundResource(R.mipmap.two_design_2);
                    tvTitle.setText(R.string.secondary_point6);
                    tvContent.setText(R.string.secondary_point6);
                    tvContent.setVisibility(View.GONE);
                    break;

                case VIDEO_TAPE:
                    llItemBg.setBackgroundResource(R.mipmap.two_design_3);
                    tvTitle.setText(R.string.secondary_point7);
                    tvContent.setText(R.string.secondary_point7);
                    tvContent.setVisibility(View.GONE);
                    break;
                default:
            }
        }
    }
}
