package com.cdxz.liudake.ui.my.service;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.my.service.RedmiBillAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.RedmiBillBean;
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

public class RedmiBillActivity extends BaseActivity {

    @BindView(R.id.tvToPromoteNum)
    TextView tvToPromoteNum;

    @BindView(R.id.tvTuiguang)
    TextView tvTuiguang;

    @BindView(R.id.tvJifen)
    TextView tvJifen;

    @BindView(R.id.tvTixian)
    TextView tvTixian;

    @BindView(R.id.tvXiaofei)
    TextView tvXiaofei;

    @BindView(R.id.tvAllBill)
    DrawableTextView tvAllBill;

    @BindView(R.id.tvDate)
    DrawableTextView tvDate;

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @BindView(R.id.recyclerRedmiBill)
    RecyclerView recyclerRedmiBill;

    private RedmiBillAdapter mAdapter;
    private int type = 0;
    private String time = null;
    private int page = 1;
    private String shopId = "";
    private List<RedmiBillBean.ListBean> listBeanList = new ArrayList<>();
    private String[] userTitle = new String[]{
            "全部账单",
            "直推用户收益",
            "直推商家收益",
            "运营商收益",
            "推荐收益",
            "积分兑换",
            "商城消费",
            "线下消费",
            "分享红米线下消费",
            "交易红米线下消费",
            "平台分红",
            "会员提现",
            "分享红米商城消费",
            "交易红米商城消费",
            "拒绝提现",
            "商城红米退款",
            "分润退回"
    };
    private int[] userType = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 18, 19};
    private String[] storeTitle = new String[]{
            "全部",
            "商家收款",
            "商家提现",
            "商家提现拒绝"
    };
    private int[] storeType = new int[]{0, 8, 16, 17};

    public static void startRedmiBillActivity(Context context) {
        Intent intent = new Intent(context, RedmiBillActivity.class);
        context.startActivity(intent);
    }

    public static void startRedmiBillActivity(Context context, String shopId) {
        Intent intent = new Intent(context, RedmiBillActivity.class);
        intent.putExtra("shopId", shopId);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_redmi_bill_new;
    }

    @Override
    protected void initViews() {
//        setTitleText("红米账单");
        recyclerRedmiBill.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initDatas() {
        if (!ObjectUtils.isEmpty(getIntent().getStringExtra("shopId"))) {
            shopId = getIntent().getStringExtra("shopId");
        }
        mAdapter = new RedmiBillAdapter(listBeanList);
        recyclerRedmiBill.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.public_no_data);
        if (!TextUtils.isEmpty(shopId)) {
            tvAllBill.setText("全部");
            type = 0;
        }
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
        tvAllBill.setOnClickListener(v -> {
            hideKeyboard();
            new XPopup.Builder(this)
                    .maxHeight((int) (ScreenUtils.getScreenHeight() * 0.6))
                    .asBottomList("", TextUtils.isEmpty(shopId) ? userTitle : storeTitle, new OnSelectListener() {
                        @Override
                        public void onSelect(int position, String text) {
                            type = TextUtils.isEmpty(shopId) ? userType[position] : storeType[position];
                            tvAllBill.setText(text);
                            getData();
                        }
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
    }

    private void getData() {
        HttpsUtil.getInstance(this).redmiBill(type, time, page, shopId, object -> {
            RedmiBillBean redmiBillBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), RedmiBillBean.class);
            tvToPromoteNum.setText(redmiBillBean.getUserInfo().getBalance());
            tvTuiguang.setText(redmiBillBean.getMy_balance().getTuiguang_balance());
            tvJifen.setText(redmiBillBean.getMy_balance().getIntegral_balance());
            tvTixian.setText(redmiBillBean.getMy_balance().getWithdraw_balance());
            tvXiaofei.setText(redmiBillBean.getMy_balance().getXiaofei_balance());
//            findViewById(R.id.tvGoTixian).setOnClickListener(v -> {
//                if (ActivityUtils.isActivityExistsInStack(WithdrawalActivity.class)) {
//                    ActivityUtils.finishActivity(WithdrawalActivity.class);
//                }
//                WithdrawalActivity.startWithdrawalActivity(this, redmiBillBean.getUserInfo().getBalance());
//            });
            if (CollectionUtils.isEmpty(redmiBillBean.getList())) {
                if (page == 1) {
                    refresh.finishRefreshWithNoMoreData();
                } else {
                    refresh.finishLoadMoreWithNoMoreData();
                }
//                listBeanList.clear();
            } else {
                if (page == 1) {
                    listBeanList.clear();
                    if (redmiBillBean.getList().size() < Constants.LIST_SIZE) {
                        refresh.finishLoadMoreWithNoMoreData();
                    } else {
                        refresh.finishRefresh();
                    }
                } else {
                    refresh.finishLoadMore();
                }
                listBeanList.addAll(redmiBillBean.getList());
            }
            mAdapter.notifyDataSetChanged();
        });
    }
}