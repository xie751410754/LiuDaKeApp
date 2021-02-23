package com.cdxz.liudake.ui.my.service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.BasePagerAdapter;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.order.OrderListFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CollectActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager2 viewPager;

//    private String[] title = {"商品收藏", "店铺收藏"};
    private String[] title = {"商品收藏"};
    private List<Fragment> fragmentList = new ArrayList<>();

    public static void startCollectActivity(Context context) {
        Intent intent = new Intent(context, CollectActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initViews() {
        setTitleText("我的收藏");
        fragmentList.add(new CollectGoodsFragment());
//        fragmentList.add(new CollectStoreFragment());
        viewPager.setAdapter(new BasePagerAdapter(this, fragmentList));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(title[position]));
        tabLayoutMediator.attach();
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListener() {

    }
}