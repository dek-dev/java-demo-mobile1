package com.oppo.marketdemo.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.oppo.marketdemo.R;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.entity.ParameterEntityItem;
import com.oppo.marketdemo.entity.ParametercCompareEntityItem;

import java.util.List;

/**
 * @author Neo
 * 侧边栏适配器
 */
public class ParemeterCompareAdapter extends BaseMultiItemQuickAdapter<ParametercCompareEntityItem, BaseViewHolder> {
    private TypefaceTextView tvTitle, tvContent, tvContent1, tvDif;
    private View line;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ParemeterCompareAdapter(List<ParametercCompareEntityItem> data) {
        super(data);
        addItemType(ParametercCompareEntityItem.TYPE_Head, R.layout.par_phone_compare_item);
        addItemType(ParametercCompareEntityItem.TYPE_TITLE, R.layout.par_title_item);
        addItemType(ParametercCompareEntityItem.TYPE_CONTENT, R.layout.par_content_compare_item);
        addItemType(ParametercCompareEntityItem.TYPE_OTHER, R.layout.par_other_compare_item);
    }


    @Override
    protected void convert(final BaseViewHolder helper, ParametercCompareEntityItem item) {
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
                tvContent1 = helper.itemView.findViewById(R.id.tv_content1);
                tvDif = helper.itemView.findViewById(R.id.tv_dif);
                tvTitle.setText(item.getmItemTitle());
                tvContent.setText(item.getmItemContent());
                tvContent1.setText(item.getmItemContent1());

                switch (helper.getLayoutPosition()) {
                    case 10:
                    case 11:
                    case 12:
                    case 14:
                    case 15:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                    case 22:

                        tvDif.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 13:
                    case 16:
                    case 21:
                        tvDif.setVisibility(View.GONE);
                        break;

                }

                break;


            case ParameterEntityItem.TYPE_OTHER:
                tvTitle = helper.itemView.findViewById(R.id.tv_other_title);
                tvContent = helper.itemView.findViewById(R.id.tv_other_content);
                tvContent1 = helper.itemView.findViewById(R.id.tv_other_content1);
                tvDif = helper.itemView.findViewById(R.id.tv_dif);
                tvTitle.setText(item.getmItemTitle());
                tvContent.setText(item.getmItemContent());
                tvContent1.setText(item.getmItemContent1());


                switch (helper.getLayoutPosition()) {
                    case 23:
                    case 24:
                        tvDif.setVisibility(View.VISIBLE);
                        break;
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                        tvDif.setVisibility(View.GONE);
                        break;

                }
                break;
            default:
        }

    }


}
