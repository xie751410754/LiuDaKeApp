package com.cdxz.liudake.ui.shop_mall;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.GoodsClassChildAdapter;
import com.cdxz.liudake.adapter.shop_mall.GoodsClassNameAdapter;
import com.cdxz.liudake.adapter.shop_mall.HomeBannerAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.BannerBean;
import com.cdxz.liudake.bean.CategoryListBean;
import com.cdxz.liudake.ui.SearchActivity;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.GridSpacingItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodsClassActivity extends BaseActivity {

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.recyclerClass)
    RecyclerView recyclerClass;

    @BindView(R.id.goodsView)
    LinearLayout goodsView;

    private String cateid;
    private GoodsClassNameAdapter classNameAdapter;

    public static void startGoodsClassActivity(Context context) {
        Intent intent = new Intent(context, GoodsClassActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_goods_class;
    }

    @Override
    protected void initViews() {
        recyclerClass.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initDatas() {
        getBannerList();
        categoryLists();
    }

    @Override
    protected void initListener() {
        findViewById(R.id.tvSearch).setOnClickListener(v -> {
            SearchActivity.startSearchActivity(this, null);
        });
    }

    private void categoryLists() {
        HttpsUtil.getInstance(this).categoryLists(object -> {
            List<CategoryListBean> categoryListBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), CategoryListBean.class);
            for (int i = 0; i < categoryListBeanList.size(); i++) {
                if (i == 0) {//默认第一个选中
                    categoryListBeanList.get(i).setSelect(true);
                    refresh(categoryListBeanList.get(0).getChild());
                } else {
                    categoryListBeanList.get(i).setSelect(false);
                }
            }
            classNameAdapter = new GoodsClassNameAdapter(categoryListBeanList);
            recyclerClass.setAdapter(classNameAdapter);
            classNameAdapter.setOnItemClickListener((adapter, view, position) -> {
                for (int i = 0; i < categoryListBeanList.size(); i++) {
                    if (i == position) {
                        categoryListBeanList.get(i).setSelect(true);
                        refresh(categoryListBeanList.get(i).getChild());
                    } else {
                        categoryListBeanList.get(i).setSelect(false);
                    }
                }
                classNameAdapter.notifyDataSetChanged();
            });
        });
    }

    private void refresh(List<CategoryListBean.ChildBean> childBeans) {
        goodsView.removeAllViews();
        for (int j = 0; j < childBeans.size(); j++) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_goods_class_child, null);

            TextView tvName = itemView.findViewById(R.id.tvName);
            RecyclerView recyclerGoods = itemView.findViewById(R.id.recyclerGoods);
            recyclerGoods.setLayoutManager(new GridLayoutManager(this, 3));
            recyclerGoods.addItemDecoration(new GridSpacingItemDecoration(3, SizeUtils.dp2px(10), false));

            tvName.setText(childBeans.get(j).getName());

            GoodsClassChildAdapter childAdapter = new GoodsClassChildAdapter(childBeans.get(j).getChild());
            recyclerGoods.setAdapter(childAdapter);

            goodsView.addView(itemView);
        }
    }

    /**
     * banner数据
     */
    private void getBannerList() {
        HttpsUtil.getInstance(this).positionList(1, Constants.LNG, Constants.LAT, object -> {
            List<BannerBean> bannerBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), BannerBean.class);
            banner.setAdapter(new HomeBannerAdapter(bannerBeanList), true)
                    .setIndicator(new CircleIndicator(this));
        });
    }
}