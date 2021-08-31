
package com.oppo.marketdemo;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.core.view.GravityCompat;

import com.oppo.marketdemo.base.BaseMenuActivity;
import com.oppo.marketdemo.custom.MenuView;
import com.oppo.marketdemo.utils.AnimationOppoUtils;

public class MainActivity extends BaseMenuActivity {


    private FrameLayout fl, fl1, fl2, fl3, fl4;
    private FrameLayout flMenuView;
    private ImageView ivTrans, ivMenuBg;
    private MenuView menuView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {


        fl = findViewById(R.id.flayout);
        fl1 = findViewById(R.id.flayout1);
        fl2 = findViewById(R.id.flayout2);
        fl3 = findViewById(R.id.flayout3);
        fl4= findViewById(R.id.flayout4);

        flMenuView = findViewById(R.id.fl_menuView);
        ivTrans = findViewById(R.id.iv_transparent);
        menuView = findViewById(R.id.menuView);
        ivMenuBg = findViewById(R.id.iv_bg);

        setMenuWrite(true);
        menuView.setColorState(true);
        menuView.invalidate();
        flMenuView.setVisibility(View.GONE);
        menuView.setVisibility(View.GONE);
        ivMenuBg.setVisibility(View.GONE);
        fl.setOnClickListener(this);
        fl1.setOnClickListener(this);
        fl2.setOnClickListener(this);
        fl3.setOnClickListener(this);
        fl4.setOnClickListener(this);
        flMenuView.setOnClickListener(this);
        ivTrans.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 跳转对应的页码
     */
    public static final String PAGER_NUM = "pager_num";

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.flayout:
                AnimationOppoUtils.setScaleMethods(this, fl, 1);
                break;
            case R.id.flayout1:
                AnimationOppoUtils.setScaleMethods(this, fl1, 2);
                break;
            case R.id.flayout2:
                AnimationOppoUtils.setScaleMethods(this, fl2, 3);
                break;
            case R.id.flayout3:
                AnimationOppoUtils.setScaleMethods(this, fl3, 5);
                break;
            case R.id.flayout4:
                AnimationOppoUtils.setScaleMethods(this, fl3, 4);
                break;

            case R.id.fl_menuView:
            case R.id.iv_transparent:
                if (null == drawerLayout) {
                    return;
                }
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    setMenuClick(false);
                } else {
                    clickOpenMenu(-1);
                }
                break;
        }
    }


    /**
     * 侧边栏跳转
     *
     * @param position
     */
    @Override
    protected void onClickMenu(final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (position != -1) {
                                Intent intent = new Intent(MainActivity.this, SubPageActivity.class);
                                intent.putExtra(SubPageActivity.CUR_POSITION, position);
                                startActivity(intent);
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}