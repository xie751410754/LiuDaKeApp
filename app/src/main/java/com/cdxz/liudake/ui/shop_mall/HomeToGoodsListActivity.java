package com.cdxz.liudake.ui.shop_mall;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.GoodsAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.GoodsBean;
import com.cdxz.liudake.pop.PopLifeCirclePrice;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.DrawableTextView;
import com.cdxz.liudake.view.GridSpacingItemDecoration;
import com.lxj.xpopup.XPopup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeToGoodsListActivity extends BaseActivity {
    public static final int HOME1 = 1;
    public static final int HOME2 = 2;

    @BindView(R.id.tvZongHe)
    DrawableTextView tvZongHe;

    @BindView(R.id.tvFuJin)
    DrawableTextView tvFuJin;

    @BindView(R.id.tvXiaoLiang)
    DrawableTextView tvXiaoLiang;

    @BindView(R.id.tvPrice)
    DrawableTextView tvPrice;

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @BindView(R.id.recyclerGoods)
    RecyclerView recyclerGoods;

    private GoodsAdapter mAdapter;
    private int page = 1;
    private int sort = 0;
    private List<GoodsBean> goodsBeanList = new ArrayList<>();
    private int homeType;

    public static void startHomeToGoodsListActivity(Context context, String id, String name, int homeType) {
        Intent intent = new Intent(context, HomeToGoodsListActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("homeType", homeType);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_goods_list;
    }

    @Override
    protected void initViews() {
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        fragmentList.add(new GoodsListFragment());
//        fragmentList.add(new GoodsListFragment());
//        fragmentList.add(new GoodsListFragment());
//        fragmentList.add(new GoodsListFragment());
//        fragmentList.add(new GoodsListFragment());
//        viewPager.setAdapter(new BasePagerAdapter(this, fragmentList));
//
//        TabLayoutMediator layoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
//                tab.setText(tabTitle[position])
//        );
//        layoutMediator.attach();
        GridLayoutManager goodsLayout = new GridLayoutManager(this, 2);
        recyclerGoods.setLayoutManager(goodsLayout);
        recyclerGoods.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(15), true));

        //
        tvZongHe.setTextColor(ContextCompat.getColor(this, R.color.appColor));
        tvFuJin.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
        tvXiaoLiang.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
        tvPrice.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
    }

    @Override
    protected void initDatas() {
        setTitleText(getIntent().getStringExtra("name"));
        homeType = getIntent().getIntExtra("homeType", 0);

        mAdapter = new GoodsAdapter(goodsBeanList);
        recyclerGoods.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.public_no_data);
        if (getIntent().getStringExtra("id") != null) {
            mAdapter.setActiveID(getIntent().getStringExtra("id"));
        }
        mAdapter.checkHomeType(homeType);
        goodsList();
    }

    @Override
    protected void initListener() {
//        findViewById(R.id.tvSearch).setOnClickListener(v -> {
//            SearchActivity.startSearchActivity(this, null);
//        });
        tvZongHe.setOnClickListener(v -> {
            sort = 0;
            tvZongHe.setTextColor(ContextCompat.getColor(this, R.color.appColor));
            tvFuJin.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            tvXiaoLiang.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            tvPrice.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            goodsList();
        });
        tvFuJin.setOnClickListener(v -> {
            sort = 5;
            tvZongHe.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            tvFuJin.setTextColor(ContextCompat.getColor(this, R.color.appColor));
            tvXiaoLiang.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            tvPrice.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            goodsList();
        });
        tvXiaoLiang.setOnClickListener(v -> {
            sort = 2;
            tvZongHe.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            tvFuJin.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            tvXiaoLiang.setTextColor(ContextCompat.getColor(this, R.color.appColor));
            tvPrice.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            goodsList();
        });
        tvPrice.setOnClickListener(v -> {
            new XPopup.Builder(this)
                    .asCustom(new PopLifeCirclePrice(this, (sort, text) -> {
                        tvZongHe.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
                        tvFuJin.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
                        tvXiaoLiang.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
                        tvPrice.setTextColor(ContextCompat.getColor(this, R.color.appColor));
                        this.sort = sort;
                        tvPrice.setText(text);
                        goodsList();
                    })).show();
        });
        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                goodsList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                goodsList();
            }
        });
    }

    private void goodsList() {
        LogUtils.e("homeType = " + homeType);
        if (homeType == HOME1) {
            HttpsUtil.getInstance(this).goodsList(page, getIntent().getStringExtra("id"), null, null,
                    null, null, null, null, Constants.LAT, Constants.LNG, sort, object -> {
                        List<GoodsBean> beanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), GoodsBean.class);
                        if (CollectionUtils.isEmpty(beanList)) {
                            if (page == 1) {
                                refresh.finishRefreshWithNoMoreData();
                            } else {
                                refresh.finishLoadMoreWithNoMoreData();
                            }
//                            goodsBeanList.clear();
                        } else {
                            if (page == 1) {
                                goodsBeanList.clear();
                                if (beanList.size() < Constants.LIST_SIZE) {
                                    refresh.finishLoadMoreWithNoMoreData();
                                } else {
                                    refresh.finishRefresh();
                                }
                            } else {
                                refresh.finishLoadMore();
                            }
                            goodsBeanList.addAll(beanList);
                        }
                        mAdapter.notifyDataSetChanged();
                    });
        } else if (homeType == HOME2) {
            HttpsUtil.getInstance(this).goodsList(page, null, getIntent().getStringExtra("id"), null,
                    null, null, null, null, Constants.LAT, Constants.LNG, sort, object -> {
                        List<GoodsBean> beanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), GoodsBean.class);
                        if (CollectionUtils.isEmpty(beanList)) {
                            if (page == 1) {
                                refresh.finishRefreshWithNoMoreData();
                            } else {
                                refresh.finishLoadMoreWithNoMoreData();
                            }
                            goodsBeanList.clear();
                        } else {
                            if (page == 1) {
                                goodsBeanList.clear();
                                if (beanList.size() < Constants.LIST_SIZE) {
                                    refresh.finishLoadMoreWithNoMoreData();
                                } else {
                                    refresh.finishRefresh();
                                }
                            } else {
                                refresh.finishLoadMore();
                            }
                            goodsBeanList.addAll(beanList);
                        }
                        mAdapter.notifyDataSetChanged();
                    });
        } else if (homeType == 5) {
            HttpsUtil.getInstance(this).activeGoodsList(page, null, getIntent().getStringExtra("id"), null,
                    null, null, null, null, Constants.LAT, Constants.LNG, sort, object -> {
                        List<GoodsBean> beanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), GoodsBean.class);
                        if (CollectionUtils.isEmpty(beanList)) {
                            if (page == 1) {
                                refresh.finishRefreshWithNoMoreData();
                            } else {
                                refresh.finishLoadMoreWithNoMoreData();
                            }
                            goodsBeanList.clear();
                        } else {
                            if (page == 1) {
                                goodsBeanList.clear();
                                if (beanList.size() < Constants.LIST_SIZE) {
                                    refresh.finishLoadMoreWithNoMoreData();
                                } else {
                                    refresh.finishRefresh();
                                }
                            } else {
                                refresh.finishLoadMore();
                            }
                            goodsBeanList.addAll(beanList);
                        }
                        mAdapter.notifyDataSetChanged();
                    });
        }else if (homeType ==100){
            HttpsUtil.getInstance(this).disscountGoodsList(page, null, getIntent().getStringExtra("id"), null,
                    null, null, null, null, Constants.LAT, Constants.LNG, sort, object -> {
                        List<GoodsBean> beanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), GoodsBean.class);
                        if (CollectionUtils.isEmpty(beanList)) {
                            if (page == 1) {
                                refresh.finishRefreshWithNoMoreData();
                            } else {
                                refresh.finishLoadMoreWithNoMoreData();
                            }
                            goodsBeanList.clear();
                        } else {
                            if (page == 1) {
                                goodsBeanList.clear();
                                if (beanList.size() < Constants.LIST_SIZE) {
                                    refresh.finishLoadMoreWithNoMoreData();
                                } else {
                                    refresh.finishRefresh();
                                }
                            } else {
                                refresh.finishLoadMore();
                            }
                            goodsBeanList.addAll(beanList);
                        }
                        mAdapter.notifyDataSetChanged();
                    });
        }
    }
}