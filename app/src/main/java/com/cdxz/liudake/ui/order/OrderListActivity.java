package com.cdxz.liudake.ui.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.BasePagerAdapter;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.shop_mall.GoodsListFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderListActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager2 viewPager;

    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] tabTitle = {"全部订单", "待发货","待付款", "待收货",
//            "待评价",
            "已完成"};

    public static void startOrderListActivity(Context context, int position) {
        Intent intent = new Intent(context, OrderListActivity.class);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initViews() {
        setTitleText("订单列表");

        fragmentList.add(OrderListFragment.newInstance(0));
        fragmentList.add(OrderListFragment.newInstance(2));
        fragmentList.add(OrderListFragment.newInstance(1));
        fragmentList.add(OrderListFragment.newInstance(3));
//        fragmentList.add(OrderListFragment.newInstance(4));
        fragmentList.add(OrderListFragment.newInstance(5));
        viewPager.setAdapter(new BasePagerAdapter(this, fragmentList));

        TabLayoutMediator layoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(tabTitle[position])
        );
        layoutMediator.attach();
    }

    @Override
    protected void initDatas() {
        viewPager.setCurrentItem(getIntent().getIntExtra("position", 0), false);
    }

    @Override
    protected void initListener() {

    }
}