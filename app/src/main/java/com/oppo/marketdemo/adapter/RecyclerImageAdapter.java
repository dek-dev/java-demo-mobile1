package com.oppo.marketdemo.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.custom.FitXYImageView;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.globle.VApplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by szm on 2019/8/9.
 */

public class RecyclerImageAdapter extends RecyclerView.Adapter<RecyclerImageAdapter.RecyclerHolder> {

    private Context mContext;
    private ItemClickListener mClickListener;
    private int[] imageIds;

    public RecyclerImageAdapter(Context context, int[] images){
        mContext = context;
        imageIds = images;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_layout, viewGroup, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerHolder recyclerHolder, final int i) {
        if (imageIds != null && imageIds.length > i){
            recyclerHolder.imageView.setImageResource(imageIds[i]);
            recyclerHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null){
                        mClickListener.onItemClick(RecyclerImageAdapter.this, recyclerHolder.imageView, i);
                    }
                }
            });
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
        private ImageView imageView;

        public RecyclerHolder(View itemView){
            super(itemView);
            mView = itemView;
            imageView = mView.findViewById(R.id.image_view);
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
