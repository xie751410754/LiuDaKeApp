package com.cdxz.liudake.ui.life_circle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.life_circle.LifeCircleAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.Bus.GetStoreIdBean;
import com.cdxz.liudake.bean.LifeCircleBean;
import com.cdxz.liudake.pop.PopLifeCirclePrice;
import com.cdxz.liudake.ui.base.BaseFragment;
import com.cdxz.liudake.util.ParseUtils;
import com.lxj.xpopup.XPopup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LifeCircleChildFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LifeCircleChildFragment extends BaseFragment {


    @BindView(R.id.refreshStore)
    SmartRefreshLayout refreshStore;

    @BindView(R.id.recyclerStore)
    RecyclerView recyclerStore;


    @BindView(R.id.tvPaixu)
    TextView tvPaixu;

    @BindView(R.id.tvSale)
    TextView tvSale;

    @BindView(R.id.tvPrice)
    TextView tvPrice;

    private int page = 1;
    private int sort = 1;
    private String cat_id = "", fastcateid = "";
    private LifeCircleAdapter mAdapter;
    private List<LifeCircleBean> lifeCircleBeanList = new ArrayList<>();

    public LifeCircleChildFragment() {
        // Required empty public constructor
    }

    public static LifeCircleChildFragment newInstance(String cat_id) {
        LifeCircleChildFragment fragment = new LifeCircleChildFragment();
        Bundle args = new Bundle();
        args.putString("cat_id", cat_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected int getResource() {
        return R.layout.fragment_life_circle_child;
    }

    @Override
    protected void initView() {
        recyclerStore.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initData() {
        BusUtils.register(this);
        mAdapter = new LifeCircleAdapter(lifeCircleBeanList);
        recyclerStore.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.public_no_data);
        if (getArguments() != null) {
            cat_id = getArguments().getString("cat_id");
            LogUtils.e("cat_id = " + cat_id);
            getStoreList();
        }
    }

    @BusUtils.Bus(tag = BusTag.GET_STORE_ID)
    public void onGetStoreId(GetStoreIdBean bean) {
        LogUtils.e("bean = " + GsonUtils.toJson(bean));
        cat_id = bean.getFastcateid();
//        fastcateid = bean.getFastcateid();
        page = 1;
        sort = 1;
        tvPaixu.setTextColor(ContextCompat.getColor(getContext(), R.color.appColor));
        tvSale.setTextColor(ContextCompat.getColor(getContext(), R.color.color_999999));
        tvPrice.setTextColor(ContextCompat.getColor(getContext(), R.color.color_999999));
        getStoreList();
    }

    @Override
    protected void initListener() {
        tvPaixu.setOnClickListener(v -> {
            tvPaixu.setTextColor(ContextCompat.getColor(getContext(), R.color.appColor));
            tvSale.setTextColor(ContextCompat.getColor(getContext(), R.color.color_999999));
            tvPrice.setTextColor(ContextCompat.getColor(getContext(), R.color.color_999999));
            sort = 1;
            getStoreList();
        });
        tvSale.setOnClickListener(v -> {
            tvPaixu.setTextColor(ContextCompat.getColor(getContext(), R.color.color_999999));
            tvSale.setTextColor(ContextCompat.getColor(getContext(), R.color.appColor));
            tvPrice.setTextColor(ContextCompat.getColor(getContext(), R.color.color_999999));
            sort = 2;
            getStoreList();
        });
        tvPrice.setOnClickListener(v -> {
            new XPopup.Builder(getContext())
                    .asCustom(new PopLifeCirclePrice(getContext(), (sort, text) -> {
                        tvPaixu.setTextColor(ContextCompat.getColor(getContext(), R.color.color_999999));
                        tvSale.setTextColor(ContextCompat.getColor(getContext(), R.color.color_999999));
                        tvPrice.setTextColor(ContextCompat.getColor(getContext(), R.color.appColor));
                        this.sort = sort;
                        tvPrice.setText(text);
                        getStoreList();
                    })).show();
        });
        refreshStore.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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

    private void getStoreList() {
        HttpsUtil.getInstance(getContext()).nearShop(Constants.LNG, Constants.LAT, page, cat_id, fastcateid, sort, object -> {
            List<LifeCircleBean> circleBeans = ParseUtils.parseJsonArray(GsonUtils.toJson(object), LifeCircleBean.class);
            if (CollectionUtils.isEmpty(circleBeans)) {
                if (page == 1) {
                    refreshStore.finishRefreshWithNoMoreData();
                } else {
                    refreshStore.finishLoadMoreWithNoMoreData();
                }
//                lifeCircleBeanList.clear();
            } else {
                if (page == 1) {
                    lifeCircleBeanList.clear();
                    if (circleBeans.size() < Constants.LIST_SIZE) {
                        refreshStore.finishLoadMoreWithNoMoreData();
                    } else {
                        refreshStore.finishRefresh();
                    }
                } else {
                    refreshStore.finishLoadMore();
                }
                lifeCircleBeanList.addAll(circleBeans);
            }
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
    }
}