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
import com.oppo.marketdemo.entity.ParameterEntityItem;
import com.oppo.marketdemo.utils.AnimationOppoUtils;

import java.util.List;

/**
 * @author Neo
 * 侧边栏适配器
 */
public class ParemeterAdapter extends BaseMultiItemQuickAdapter<ParameterEntityItem, BaseViewHolder> {
    private TypefaceTextView tvTitle, tvContent;
    private View line;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ParemeterAdapter(List<ParameterEntityItem> data) {
        super(data);
        addItemType(ParameterEntityItem.TYPE_Head, R.layout.par_phone_item);
        addItemType(ParameterEntityItem.TYPE_TITLE, R.layout.par_title_item);
        addItemType(ParameterEntityItem.TYPE_CONTENT, R.layout.par_content_item);
        addItemType(ParameterEntityItem.TYPE_OTHER, R.layout.par_other_item);
    }



    @Override
    protected void convert(final BaseViewHolder helper, ParameterEntityItem item) {
        switch (helper.getItemViewType()) {
            case ParameterEntityItem.TYPE_TITLE:
                tvTitle = helper.itemView.findViewById(R.id.tv_par_title);
                line = helper.itemView.findViewById(R.id.line);
                tvTitle.setText(item.getmItemTitle());
                if (helper.getLayoutPosition() == 1) {
                    line.setVisibility(View.GONE);
                } else {
                    line.setVisibility(View.VISIBLE);
                }
                break;

            case ParameterEntityItem.TYPE_CONTENT:
                tvTitle = helper.itemView.findViewById(R.id.tv_title);
                tvContent = helper.itemView.findViewById(R.id.tv_content);
                tvTitle.setText(item.getmItemTitle());
                tvContent.setText(item.getmItemContent());
                break;


            case ParameterEntityItem.TYPE_OTHER:
                tvTitle = helper.itemView.findViewById(R.id.tv_other_title);
                tvContent = helper.itemView.findViewById(R.id.tv_other_content);
                tvTitle.setText(item.getmItemTitle());
                tvContent.setText(item.getmItemContent());
                break;
            default:
        }

    }


}
