package com.cdxz.liudake.ui.my.service;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.my.service.ToPromoteAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.ToPromoteBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ToPromoteActivity extends BaseActivity {

    @BindView(R.id.tvToPromoteNum)
    TextView tvToPromoteNum;

    @BindView(R.id.tvZhitui)
    TextView tvZhitui;

    @BindView(R.id.tvShangjia)
    TextView tvShangjia;

    @BindView(R.id.tvFenhong)
    TextView tvFenhong;

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @BindView(R.id.recyclerToPromote)
    RecyclerView recyclerToPromote;

    private ToPromoteAdapter mAdapter;
    private List<ToPromoteBean.ListBean> listBeanList = new ArrayList<>();
    private int page = 1;

    public static void startToPromoteActivity(Context context) {
        Intent intent = new Intent(context, ToPromoteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_to_promote;
    }

    @Override
    protected void initViews() {
        setTitleText("我的推广");
        recyclerToPromote.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initDatas() {
        mAdapter = new ToPromoteAdapter(listBeanList);
        recyclerToPromote.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.public_no_data);
        getData();
    }

    @Override
    protected void initListener() {
        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
    }

    private void getData() {
        HttpsUtil.getInstance(this).userTuiguang(page, object -> {
            ToPromoteBean promoteBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), ToPromoteBean.class);
            if (promoteBean == null)return;
            tvToPromoteNum.setText(String.valueOf(promoteBean.getCount()));
            tvZhitui.setText(String.valueOf(promoteBean.getZhi()));
            tvShangjia.setText(String.valueOf(promoteBean.getShop()));
            tvFenhong.setText(String.valueOf(promoteBean.getHong()));
            if (CollectionUtils.isEmpty(promoteBean.getList())) {
                if (page == 1) {
                    refresh.finishRefreshWithNoMoreData();
                } else {
                    refresh.finishLoadMoreWithNoMoreData();
                }
                listBeanList.clear();
            } else {
                if (page == 1) {
                    listBeanList.clear();
                    if (promoteBean.getList().size() < Constants.LIST_SIZE) {
                        refresh.finishLoadMoreWithNoMoreData();
                    } else {
                        refresh.finishRefresh();
                    }
                } else {
                    refresh.finishLoadMore();
                }
                listBeanList.addAll(promoteBean.getList());
            }
            mAdapter.notifyDataSetChanged();
        });
    }
}