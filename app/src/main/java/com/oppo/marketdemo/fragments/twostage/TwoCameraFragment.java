package com.oppo.marketdemo.fragments.twostage;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.oppo.marketdemo.R;
import com.oppo.marketdemo.TwoMainStageActivity;
import com.oppo.marketdemo.adapter.TwoCameraAdapter;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.utils.AnimationOppoUtils;

import java.util.ArrayList;
import java.util.List;



/**
 * @author Neo
 */
public class TwoCameraFragment extends BaseFragment {
    ImageView ivIcon;
    TypefaceTextView tvTitle;
    TypefaceTextView tvContent;
    View viewLine;
    RecyclerView rlvExp;
    private TwoCameraAdapter expAdapter;
    List<Integer> integerList;

    @Override
    protected int getLayoutId() {
        return R.layout.two_exp_fragment;
    }

    @Override
    protected void initViews(View mRootView) {
        ivIcon = mRootView.findViewById(R.id.iv_icon);
        tvTitle = mRootView.findViewById(R.id.tv_title);
        tvContent = mRootView.findViewById(R.id.tv_content);
        viewLine = mRootView.findViewById(R.id.view);
        rlvExp = mRootView.findViewById(R.id.rlv_exp);
    }

    @Override
    protected void init() {
        integerList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            integerList.add(i);
        }
        expAdapter = new TwoCameraAdapter();
        rlvExp.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlvExp.setAdapter(expAdapter);
        expAdapter.addData(integerList);
        //点击事件
        expAdapter.addChildClickViewIds(R.id.ll_item_bg);
        expAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AnimationOppoUtils.setRlvAnimationMethods(getActivity(), view, position + 12);
            }
        });
        if (TwoMainStageActivity.isFirst) {
            TwoMainStageActivity.isFirst = false;
            rlvExp.setAlpha(0);
            tvTitle.setAlpha(0);
            tvContent.setAlpha(0);
            viewLine.setAlpha(0);
            tvTitle.setVisibility(View.VISIBLE);
            tvContent.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
            rlvExp.setVisibility(View.VISIBLE);
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
        if (null != rlvExp && null != expAdapter) {
            rlvExp.setAlpha(1);
            tvTitle.setAlpha(1);
            tvContent.setAlpha(1);
            viewLine.setAlpha(1);
            expAdapter.notifyDataSetChanged();
            AnimationOppoUtils.setAnimationMethods(rlvExp, getActivity());
            AnimationOppoUtils.setAnimationXMethods(tvTitle, tvContent, viewLine);
            AnimationOppoUtils.setAnimationPartIcon(ivIcon, AnimationOppoUtils.TYPE_EXP);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (null != rlvExp && !isVisibleToUser) {
            hideViews(tvTitle, tvContent, viewLine, rlvExp, ivIcon);
        }
    }

    @Override
    public void startAnimList(boolean isAnimList) {
        super.startAnimList(isAnimList);
        if (null != tvTitle) {
            showViews(tvTitle, tvContent, viewLine, rlvExp, ivIcon);
            notifyAnimationMetods();
        }
    }
}
