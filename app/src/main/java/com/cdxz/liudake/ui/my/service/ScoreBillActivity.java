package com.cdxz.liudake.ui.my.service;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.my.service.RedmiBillAdapter;
import com.cdxz.liudake.adapter.my.service.ScoreBillAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.ScoreBillBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.DrawableTextView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ScoreBillActivity extends BaseActivity {
    @BindView(R.id.recyclerScoreBill)
    RecyclerView recyclerScoreBill;

    @BindView(R.id.tvDaiLing)
    TextView tvDaiLing;

    @BindView(R.id.tvKeYong)
    TextView tvKeYong;

//    @BindView(R.id.tvLingquScore)
//    TextView tvLingquScore;

    @BindView(R.id.tvRedmi)
    TextView tvRedmi;

    @BindView(R.id.tvGoods)
    TextView tvGoods;

    @BindView(R.id.tvType)
    DrawableTextView tvType;

    @BindView(R.id.tvDate)
    DrawableTextView tvDate;

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private ScoreBillAdapter mAdapter;
    private List<ScoreBillBean.ListBean> listBeanList = new ArrayList<>();
    private int type = 0;
    private String time = null;
    private int page = 1;

    public static void startScoreBillActivity(Context context) {
        Intent intent = new Intent(context, ScoreBillActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_score_bill_new;
    }

    @Override
    protected void initViews() {
//        setTitleText("积分账单");
        recyclerScoreBill.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initDatas() {
        mAdapter = new ScoreBillAdapter(listBeanList);
        recyclerScoreBill.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.public_no_data);
        getData();
    }

    @Override
    protected void initListener() {

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvType.setOnClickListener(v -> {
            new XPopup.Builder(this)
                    .asBottomList("", new String[]{
                            "全部账单",
                            "购物使用",
                            "订单完成赠送",
                            "线下消费赠送",
                            "兑换红米",
                            "退回积分",
                            "退款收回积分"
                    }, (position, text) -> {
                        type = position;
                        tvType.setText(text);
                        getData();
                    }).show();
        });
        tvDate.setOnClickListener(v -> {
            TimePickerView pvTime = new TimePickerBuilder(this, (date, v1) -> {
                time = TimeUtils.date2String(date, "yyyy-MM-dd");
                tvDate.setText(time);
                getData();
            }).setType(new boolean[]{true, true, true, false, false, false})
                    .setCancelColor(ContextCompat.getColor(this, R.color.appColor))
                    .setSubmitColor(ContextCompat.getColor(this, R.color.appColor))
                    .build();
            pvTime.show(true);
        });
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
        HttpsUtil.getInstance(this).integralBill(type, time, page, object -> {
            ScoreBillBean scoreBillBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), ScoreBillBean.class);
            tvDaiLing.setText(scoreBillBean.getMy_integral().getWait_integral());
            tvKeYong.setText(scoreBillBean.getIntegral());
//            tvLingquScore.setText(scoreBillBean.getMy_integral().getDetermine_integral());
            tvRedmi.setText(scoreBillBean.getMy_integral().getRedmi_integral());
            tvGoods.setText(scoreBillBean.getMy_integral().getGoods_integral());
            if (CollectionUtils.isEmpty(scoreBillBean.getList())) {
                if (page == 1) {
                    refresh.finishRefreshWithNoMoreData();
                } else {
                    refresh.finishLoadMoreWithNoMoreData();
                }
                listBeanList.clear();
            } else {
                if (page == 1) {
                    listBeanList.clear();
                    if (scoreBillBean.getList().size() < Constants.LIST_SIZE) {
                        refresh.finishLoadMoreWithNoMoreData();
                    } else {
                        refresh.finishRefresh();
                    }
                } else {
                    refresh.finishLoadMore();
                }
                listBeanList.addAll(scoreBillBean.getList());
            }
            mAdapter.notifyDataSetChanged();
        });
    }
}