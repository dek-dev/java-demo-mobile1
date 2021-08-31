package com.oppo.marketdemo.fragments.twostage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.oppo.marketdemo.R;
import com.oppo.marketdemo.TwoMainStageActivity;
import com.oppo.marketdemo.adapter.TwoPerformanceAdapter;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.utils.AnimationOppoUtils;

import java.util.ArrayList;
import java.util.List;



/**
 * @author Neo
 */
public class TwoPerformanceFragment extends BaseFragment {
    public static final String IS_ANIMATION = "is_Animation";
    /**
     * 第一个为false不播放动画
     * 第五个为true播放动画
     */
    private boolean isPlay;
    /**
     * 第一个加载是拍照页面时，要播放动画
     */
    private boolean isThisFirst;
    ImageView ivIcon;
    TypefaceTextView tvTitle;
    TypefaceTextView tvContent;
    View viewLine;
    RecyclerView rlvPhoto;
    private TwoPerformanceAdapter photoAdapter;
    List<Integer> integerList;

    public static TwoPerformanceFragment getInstance(boolean isAnimation) {
        TwoPerformanceFragment fragment = new TwoPerformanceFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_ANIMATION, isAnimation);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.two_photo_fragment;
    }

    @Override
    protected void initViews(View mRootView) {
        ivIcon = mRootView.findViewById(R.id.iv_icon);
        tvTitle = mRootView.findViewById(R.id.tv_title);
        tvContent = mRootView.findViewById(R.id.tv_content);
        viewLine = mRootView.findViewById(R.id.view);
        rlvPhoto = mRootView.findViewById(R.id.rlv_photo);
    }

    @Override
    protected void init() {
        isPlay = getArguments().getBoolean(IS_ANIMATION);
        integerList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            integerList.add(i);
        }
        photoAdapter = new TwoPerformanceAdapter();
        rlvPhoto.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlvPhoto.setAdapter(photoAdapter);
        photoAdapter.addData(integerList);
        //点击事件
        photoAdapter.addChildClickViewIds(R.id.ll_item_bg);
        photoAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AnimationOppoUtils.setRlvAnimationMethods(getActivity(), view, position + 2);
            }
        });
        //首页所跳转
        if (TwoMainStageActivity.isFirst) {
            isThisFirst = true;
            TwoMainStageActivity.isFirst = false;
            rlvPhoto.setAlpha(0);
            tvTitle.setAlpha(0);
            tvContent.setAlpha(0);
            viewLine.setAlpha(0);
            tvTitle.setVisibility(View.VISIBLE);
            tvContent.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
            rlvPhoto.setVisibility(View.VISIBLE);
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
        if (null != rlvPhoto) {
            if (isPlay || isThisFirst || isAnim) {
                isThisFirst = false;
                rlvPhoto.setAlpha(1);
                tvTitle.setAlpha(1);
                tvContent.setAlpha(1);
                viewLine.setAlpha(1);
                photoAdapter.notifyDataSetChanged();
                AnimationOppoUtils.setAnimationMethods(rlvPhoto, getActivity());
                AnimationOppoUtils.setAnimationXMethods(tvTitle, tvContent, viewLine);
                AnimationOppoUtils.setAnimationIcon(ivIcon, AnimationOppoUtils.TYPE_PHOTO);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (null != rlvPhoto && !isVisibleToUser && isPlay) {
            hideViews(tvTitle, tvContent, viewLine, rlvPhoto, ivIcon);
        }
    }

    private boolean isAnim;

    @Override
    public void startAnimList(boolean isAnimList) {
        super.startAnimList(isAnimList);
        isAnim = isAnimList;
        if (null != tvTitle) {
            showViews(tvTitle, tvContent, viewLine, rlvPhoto, ivIcon);
            if (isAnim) {
                notifyAnimationMetods();
            }
        }
    }
}
