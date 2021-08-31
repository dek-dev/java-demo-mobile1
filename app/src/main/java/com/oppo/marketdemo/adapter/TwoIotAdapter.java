package com.oppo.marketdemo.adapter;

import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.oppo.marketdemo.R;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;

/**
 * @author Neo
 * 类名：TwoAppearanceAdapter
 * 第二级外观适配器
 */
public class TwoIotAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    LinearLayout llItemBg;
    TypefaceTextView tvTitle;
    TypefaceTextView tvContent;
    private final int APPEARANCE_ONE = 0;
    private final int APPEARANCE_TWO = 1;

    public TwoIotAdapter() {
        super(R.layout.two_iot_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer dataNum) {
        llItemBg = helper.itemView.findViewById(R.id.ll_item_bg);
        tvTitle = helper.itemView.findViewById(R.id.tv_title);

        tvContent = helper.itemView.findViewById(R.id.tv_content);

        if (null != dataNum) {
            switch (dataNum) {
                case APPEARANCE_ONE:
                    llItemBg.setBackgroundResource(R.mipmap.iot_item_first);
                    tvTitle.setText(R.string.secondary_point18);
                    tvContent.setText(R.string.secondary_point18);
                    tvContent.setVisibility(View.GONE);
                    break;
                case APPEARANCE_TWO:
                    llItemBg.setBackgroundResource(R.mipmap.iot_item_second);
                    tvTitle.setText(R.string.secondary_point19);
                    tvContent.setText(R.string.secondary_point19);
                    tvContent.setVisibility(View.GONE);
                    break;
                default:
            }
        }
    }
}
