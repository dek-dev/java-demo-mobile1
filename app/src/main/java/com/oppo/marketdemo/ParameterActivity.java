package com.oppo.marketdemo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oppo.marketdemo.adapter.ParemeterAdapter;
import com.oppo.marketdemo.adapter.ParemeterCompareAdapter;
import com.oppo.marketdemo.adapter.ParemeterNewAdapter;
import com.oppo.marketdemo.base.BaseActivity;
import com.oppo.marketdemo.entity.ParameterEntityItem;
import com.oppo.marketdemo.entity.ParametercCompareEntityItem;
import com.oppo.marketdemo.globle.VApplication;

import java.util.ArrayList;
import java.util.List;

public class ParameterActivity extends BaseActivity {

    private RecyclerView mRecycleView;
    private List<ParameterEntityItem> sideList;

    private ParemeterNewAdapter paremeterAdapter;

    private ArrayList<Integer> titles = new ArrayList<>();

    private ArrayList<Integer> contents = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter);
        init();
    }

    private void init() {
        mRecycleView = findViewById(R.id.ry_par);
        initData();
        paremeterAdapter = new ParemeterNewAdapter(sideList);
        paremeterAdapter.setHasStableIds(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(paremeterAdapter);


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
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            //体验更多
            case R.id.bt_experience:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            default:
        }
    }



}