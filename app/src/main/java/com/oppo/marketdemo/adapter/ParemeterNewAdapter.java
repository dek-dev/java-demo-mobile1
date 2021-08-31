package com.oppo.marketdemo.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.oppo.marketdemo.R;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.entity.ParameterEntityItem;

import java.util.List;

/**
 * @author Neo
 * 侧边栏适配器
 */
public class ParemeterNewAdapter extends BaseMultiItemQuickAdapter<ParameterEntityItem, BaseViewHolder> {
    private TypefaceTextView tvTitle, tvContent;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ParemeterNewAdapter(List<ParameterEntityItem> data) {
        super(data);
        addItemType(ParameterEntityItem.TYPE_Head, R.layout.par_phone_item);
        addItemType(ParameterEntityItem.TYPE_CONTENT, R.layout.par_content_item_new);
    }



    @Override
    protected void convert(final BaseViewHolder helper, ParameterEntityItem item) {
        switch (helper.getItemViewType()) {
            case ParameterEntityItem.TYPE_CONTENT:
                tvTitle = helper.itemView.findViewById(R.id.tv_title);
                tvContent = helper.itemView.findViewById(R.id.tv_content);
                tvTitle.setText(item.getmItemTitle());
                tvContent.setText(item.getmItemContent());
                break;

            default:
                break;
        }

    }


}
