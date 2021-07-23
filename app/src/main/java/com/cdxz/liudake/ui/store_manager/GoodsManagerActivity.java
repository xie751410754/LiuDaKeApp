package com.cdxz.liudake.ui.store_manager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.BasePagerAdapter;
import com.cdxz.liudake.adapter.life_circle.LifeCircleBannerAdapter;
import com.cdxz.liudake.adapter.life_circle.StoreCommentAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.bean.LifeCircleDetailBean;
import com.cdxz.liudake.pop.PopMap;
import com.cdxz.liudake.pop.PopSelector;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.life_circle.CommentFragment;
import com.cdxz.liudake.ui.life_circle.GoodsManagerFragment;
import com.cdxz.liudake.ui.life_circle.TuiJianStoreFragment;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.ThirdPartyMapsGuide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lxj.xpopup.XPopup;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class GoodsManagerActivity extends BaseActivity {


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPagerList)
    ViewPager2 viewPagerList;

    private final List<Fragment> fragmentList = new ArrayList<>();
    private String mTitles[] = {
            "已审核",  "未审核"};

//    @BindView(R.id.recyclerStoreComment)
//    RecyclerView recyclerStoreComment;
//
//    private StoreCommentAdapter mAdapter;
    private String id;

    public static void startGoodsManagerActivity(Context context, String id) {
        Intent intent = new Intent(context, GoodsManagerActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_tuijian_goods_manager;
    }

    @Override
    protected void initViews() {
//        recyclerStoreComment.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initDatas() {

        fragmentList.add(GoodsManagerFragment.newInstance(getIntent().getStringExtra("id"), 1));
        fragmentList.add(GoodsManagerFragment.newInstance(getIntent().getStringExtra("id"),0));
        viewPagerList.setAdapter(new BasePagerAdapter(this, fragmentList));
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPagerList, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(mTitles[position]);
            }
        });
        mediator.attach();

    }



    @Override
    protected void initListener() {

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.ll_goods_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddGoodsActivity.startAddGoodsActivity(context,getIntent().getStringExtra("id"));
            }
        });


    }


    @Override
    public Resources getResources() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 750);
        } else {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 750);
        }
    }
}