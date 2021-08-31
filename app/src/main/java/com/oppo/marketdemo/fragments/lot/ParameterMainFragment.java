package com.oppo.marketdemo.fragments.lot;

import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.adapter.ParemeterAdapter;
import com.oppo.marketdemo.adapter.ParemeterCompareAdapter;
import com.oppo.marketdemo.adapter.ParemeterNewAdapter;
import com.oppo.marketdemo.base.BaseFragment;
import com.oppo.marketdemo.custom.textview.TypefaceTextView;
import com.oppo.marketdemo.entity.ParameterEntityItem;
import com.oppo.marketdemo.entity.ParametercCompareEntityItem;
import com.oppo.marketdemo.globle.VApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szm on 2019/11/4.
 */

public class ParameterMainFragment extends BaseFragment {
    private RecyclerView mRecycleView;
    private List<ParameterEntityItem> sideList;

    private ParemeterNewAdapter paremeterAdapter;

    private ArrayList<Integer> titles = new ArrayList<>();

    private ArrayList<Integer> contents = new ArrayList<>();

    @Override
    protected int getLayoutId() {//加载布局文件
        return R.layout.parameter_main_fragment;
    }

    @Override
    protected void initViews(View mRootView) {

        mRecycleView = mRootView.findViewById(R.id.ry_par);

    }

    @Override
    protected void init() {

        initData();
        paremeterAdapter = new ParemeterNewAdapter(sideList);
        paremeterAdapter.setHasStableIds(true);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_parameter_footer, null);

        paremeterAdapter.addFooterView(view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecycleView.setAdapter(paremeterAdapter);


        initFragmentViews();
    }

    private void initData() {
        Resources res = this.getResources();
        contents.clear();
        titles.clear();

        for (int i = 0; i < 8; i++) {
            int titleId = res.getIdentifier("parameter_title" + (i + 1), "string", VApplication.getInstance().getPackageName());

            int contentId = res.getIdentifier("parameter_content" + (i + 1), "string", VApplication.getInstance().getPackageName());

            titles.add(titleId);
            contents.add(contentId);

        }
        sideList = new ArrayList<>();
        sideList.add(new ParameterEntityItem(ParameterEntityItem.TYPE_Head, 0, 0));
        for (int i = 0; i < 8; i++) {

            sideList.add(new ParameterEntityItem(ParameterEntityItem.TYPE_CONTENT, titles.get(i), contents.get(i)));


        }


    }

    @Override
    protected void start() {
        if (mRecycleView != null) {
            mRecycleView.scrollToPosition(0);
        }
    }

    @Override
    protected void pause() {
        initFragmentViews();
    }

    private void initFragmentViews() {
        if (mRecycleView != null) {
            mRecycleView.smoothScrollToPosition(0);
        }
    }



    @Override
    public void destroyFragmentViews() {
    }
}
