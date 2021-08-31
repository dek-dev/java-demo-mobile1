package com.oppo.marketdemo.fragments.twostage;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.oppo.marketdemo.R;
import com.oppo.marketdemo.TwoMainStageActivity;
import com.oppo.marketdemo.adapter.TwoDesignAdapter;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.utils.AnimationOppoUtils;

import java.util.ArrayList;
import java.util.List;



/**
 * @author Neo
 */
public class TwoDesignFragment extends BaseFragment {
    ImageView ivIcon;
    private TypefaceTextView tvTitle;
    private TypefaceTextView tvContent;
    private View viewLine;
    private RecyclerView rlvVideo;
    private TwoDesignAdapter videoAdapter;
    List<Integer> integerList;

    @Override
    protected int getLayoutId() {
        return R.layout.two_video_fragment;
    }

    @Override
    protected void initViews(View mRootView) {
        ivIcon = mRootView.findViewById(R.id.iv_icon);
        tvTitle = mRootView.findViewById(R.id.tv_title);
        tvContent = mRootView.findViewById(R.id.tv_content);
        viewLine = mRootView.findViewById(R.id.view);
        rlvVideo = mRootView.findViewById(R.id.rlv_video);
    }

    @Override
    protected void init() {
        integerList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            integerList.add(i);
        }
        videoAdapter = new TwoDesignAdapter();
        rlvVideo.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlvVideo.setAdapter(videoAdapter);
        videoAdapter.addData(integerList);
        //点击事件
        videoAdapter.addChildClickViewIds(R.id.ll_item_bg);
        videoAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AnimationOppoUtils.setRlvAnimationMethods(getActivity(), view, position + 8);
            }
        });
        if (TwoMainStageActivity.isFirst) {
            TwoMainStageActivity.isFirst = false;
            rlvVideo.setAlpha(0);
            tvTitle.setAlpha(0);
            tvContent.setAlpha(0);
            viewLine.setAlpha(0);
            tvTitle.setVisibility(View.VISIBLE);
            tvContent.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
            rlvVideo.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyAnimationMetods();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void notifyAnimationMetods() {
        if (null != rlvVideo && null != videoAdapter) {
            rlvVideo.setAlpha(1);
            tvTitle.setAlpha(1);
            tvContent.setAlpha(1);
            viewLine.setAlpha(1);
            videoAdapter.notifyDataSetChanged();
            AnimationOppoUtils.setAnimationMethods(rlvVideo, getActivity());
            AnimationOppoUtils.setAnimationXMethods(tvTitle, tvContent, viewLine);
            AnimationOppoUtils.setAnimationIcon(ivIcon, AnimationOppoUtils.TYPE_VIDEO);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (null != rlvVideo && !isVisibleToUser) {
            hideViews(tvTitle, tvContent, viewLine, rlvVideo, ivIcon);
        }
    }

    @Override
    public void startAnimList(boolean isAnimList) {
        super.startAnimList(isAnimList);
        if (null != tvTitle) {
            showViews(tvTitle, tvContent, viewLine, rlvVideo, ivIcon);
            notifyAnimationMetods();
        }
    }
}
