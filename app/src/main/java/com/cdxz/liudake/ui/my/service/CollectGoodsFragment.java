package com.cdxz.liudake.ui.my.service;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.my.service.CollectGoodsAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.CollectGoodsBean;
import com.cdxz.liudake.ui.base.BaseFragment;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.SpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectGoodsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectGoodsFragment extends BaseFragment {

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private int page = 1;
    private CollectGoodsAdapter mAdapter;
    private List<CollectGoodsBean> collectGoodsBeanList = new ArrayList<>();

    public CollectGoodsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CollectGoodsFragment newInstance() {
        CollectGoodsFragment fragment = new CollectGoodsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected int getResource() {
        return R.layout.fragment_collect_goods;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(12), true));
    }

    @Override
    protected void initData() {
        mAdapter = new CollectGoodsAdapter(collectGoodsBeanList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.public_no_data);
        getList();
    }

    @Override
    protected void initListener() {
        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getList();
            }
        });
    }

    private void getList() {
        HttpsUtil.getInstance(getContext()).collectList(1, object -> {
            List<CollectGoodsBean> beanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), CollectGoodsBean.class);
            if (CollectionUtils.isEmpty(beanList)) {
                if (page == 1) {
                    refresh.finishRefreshWithNoMoreData();
                } else {
                    refresh.finishLoadMoreWithNoMoreData();
                }
//                collectGoodsBeanList.clear();
            } else {
                if (page == 1) {
                    collectGoodsBeanList.clear();
                    if (beanList.size() < Constants.LIST_SIZE) {
                        refresh.finishLoadMoreWithNoMoreData();
                    } else {
                        refresh.finishRefresh();
                    }
                } else {
                    refresh.finishLoadMore();
                }
                collectGoodsBeanList.addAll(beanList);
            }
            mAdapter.notifyDataSetChanged();
        });
    }
}