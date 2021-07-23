package com.cdxz.liudake.ui.life_circle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.life_circle.LifeCircleAdapter;
import com.cdxz.liudake.adapter.life_circle.StoreTuiJianAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.LifeCircleBean;
import com.cdxz.liudake.bean.TuiJianStoreDto;
import com.cdxz.liudake.ui.base.BaseFragment;
import com.cdxz.liudake.util.ParseUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TuiJianStoreFragment extends BaseFragment {

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @BindView(R.id.rv_store)
    RecyclerView rv_store;

    private int page = 1;

    StoreTuiJianAdapter storeTuiJianAdapter;
    private List<TuiJianStoreDto> lifeCircleBeanList = new ArrayList<>();

    public static TuiJianStoreFragment newInstance(String id){
        TuiJianStoreFragment tuiJianStoreFragment = new TuiJianStoreFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        tuiJianStoreFragment.setArguments(args);
        return tuiJianStoreFragment;
    }
    @Override
    protected void lazyLoadData() {

    }

    @BusUtils.Bus(tag = "UpdateGoodsList")
    public void onUpdateList() {
        getStoreList();
    }

    @Override
    protected int getResource() {
        return R.layout.fragment_store_tuijian;
    }

    @Override
    protected void initView() {
        rv_store.setLayoutManager(new LinearLayoutManager(getContext()));
             BusUtils.register(getActivity());
    }

    @Override
    protected void initData() {

        storeTuiJianAdapter = new StoreTuiJianAdapter(lifeCircleBeanList);
        rv_store.setAdapter(storeTuiJianAdapter);
        storeTuiJianAdapter.setEmptyView(R.layout.public_no_data);
        getStoreList();

    }


    private void getStoreList() {
        HttpsUtil.getInstance(getContext()).nearShopGoodsList( page, getArguments().getString("id"),1, object -> {
            List<TuiJianStoreDto> circleBeans = ParseUtils.parseJsonArray(GsonUtils.toJson(object), TuiJianStoreDto.class);
            if (CollectionUtils.isEmpty(circleBeans)) {
                if (page == 1) {
                    refresh.finishRefreshWithNoMoreData();
                } else {
                    refresh.finishLoadMoreWithNoMoreData();
                }
//                lifeCircleBeanList.clear();
            } else {
                if (page == 1) {
                    lifeCircleBeanList.clear();
                    if (circleBeans.size() < Constants.LIST_SIZE) {
                        refresh.finishLoadMoreWithNoMoreData();
                    } else {
                        if (refresh!=null){
                            refresh.finishRefresh();
                        }
                    }
                } else {
                    refresh.finishLoadMore();
                }
                lifeCircleBeanList.addAll(circleBeans);
            }
            storeTuiJianAdapter.notifyDataSetChanged();
        });
    }


    @Override
    protected void initListener() {
        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getStoreList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getStoreList();
            }
        });
    }
}
