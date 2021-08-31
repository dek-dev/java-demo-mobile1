package com.oppo.marketdemo.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.custom.FitXYImageView;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.globle.VApplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by szm on 2019/8/9.
 */

public class RecyclerMenuAdapter extends RecyclerView.Adapter<RecyclerMenuAdapter.RecyclerHolder> {

    private Context mContext;
    private ItemClickListener mClickListener;
    private int[] imageIds = new int[20];
    private int[] stringIds = new int[20];

    public RecyclerMenuAdapter(Context context){
        mContext = context;
        Resources res = mContext.getResources();
        for (int i=0;i<stringIds.length;i++){
            int imageId = res.getIdentifier("home_item" + (i + 1), "mipmap", VApplication.getInstance().getPackageName());
            int stringId = res.getIdentifier("secondary_selling_point" + (i + 1), "string", VApplication.getInstance().getPackageName());
            imageIds[i] = imageId;
            stringIds[i] = stringId;
        }
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_item_layout, viewGroup, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerHolder recyclerHolder, final int i) {
        if (imageIds != null && imageIds.length > i){
            if (i == 0){
                ((RecyclerView.LayoutParams)recyclerHolder.mView.getLayoutParams()).topMargin = 289;
            }else {
                ((RecyclerView.LayoutParams)recyclerHolder.mView.getLayoutParams()).topMargin = 0;
            }
            recyclerHolder.imageView.setFit(0.932f);
            recyclerHolder.imageView.setImageResource(imageIds[i]);
            recyclerHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null){
                        mClickListener.onItemClick(RecyclerMenuAdapter.this, recyclerHolder.imageView, i);
                    }
                }
            });
            recyclerHolder.textTitle.setText(stringIds[i]);
            if (i == 5 || i == 10 || i == 13 || i == 16){
                recyclerHolder.itemFlag.setVisibility(View.VISIBLE);
            }else {
                recyclerHolder.itemFlag.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return imageIds.length;
    }

    static class RecyclerHolder extends RecyclerView.ViewHolder{

        private View mView;
        private FitXYImageView imageView;
        private TypefaceTextView textTitle;
        private View itemFlag;

        public RecyclerHolder(View itemView){
            super(itemView);
            mView = itemView;
            imageView = mView.findViewById(R.id.fit_xy_image_view);
            textTitle = mView.findViewById(R.id.text_title);
            itemFlag = mView.findViewById(R.id.item_flag);
        }

    }

    public ItemClickListener getClickListener() {
        return mClickListener;
    }

    public void setClickListener(ItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    // 点击事件接口
    public interface ItemClickListener {
        void onItemClick(RecyclerView.Adapter adapter, View view, int position);
        void onItemLongClick(RecyclerView.Adapter adapter, View view, int position);
    }

}
