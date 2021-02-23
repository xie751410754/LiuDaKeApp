package com.cdxz.liudake.ui.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.order.OrderDetailGoodsAdapter;
import com.cdxz.liudake.api.ApiRetrofit;
import com.cdxz.liudake.api.ApiServer;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BaseObserver;
import com.cdxz.liudake.bean.OrderDetailBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.recyclerGoods)
    RecyclerView recyclerGoods;

    @BindView(R.id.tvOrderStatus)
    TextView tvOrderStatus;

    @BindView(R.id.tvOrderTips)
    TextView tvOrderTips;

    @BindView(R.id.tvOrderAddress)
    TextView tvOrderAddress;

    @BindView(R.id.tvOrderUserInfo)
    TextView tvOrderUserInfo;

    @BindView(R.id.tvOrderPeisongWay)
    TextView tvOrderPeisongWay;

    @BindView(R.id.tvOrderNo)
    TextView tvOrderNo;

    @BindView(R.id.tvOrderDate)
    TextView tvOrderDate;

    @BindView(R.id.tvOrderPayType)
    TextView tvOrderPayType;

    @BindView(R.id.tvPeisong)
    TextView tvPeisong;

    @BindView(R.id.tvTotal)
    TextView tvTotal;

    @BindView(R.id.tv_logistics)
    TextView tvLogistics;

    @BindView(R.id.tv_logistics_title)
    TextView tvLogisticsTitle;

    private OrderDetailGoodsAdapter mAdapter;
    private String id;

    public static void startOrderDetailActivity(Context context, String id) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initViews() {
        setTitleText("确认详情");
        recyclerGoods.setLayoutManager(new LinearLayoutManager(this));
        recyclerGoods.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(12), true));
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void initDatas() {
        HttpsUtil.getInstance(this).orderDetail(getIntent().getStringExtra("id"), object -> {
            OrderDetailBean detailBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), OrderDetailBean.class);
            if (ActivityUtils.isActivityAlive(this)) {
                //商品信息
                mAdapter = new OrderDetailGoodsAdapter(detailBean.getList(), detailBean.getId());
                recyclerGoods.setAdapter(mAdapter);
                tvPeisong.setText(String.format("￥%.2f", detailBean.getFareprice()));
                tvTotal.setText(String.format("总计 ¥%.2f", detailBean.getTotalprice()));
                //配送信息
                tvOrderAddress.setText(
                        detailBean.getAddress().getProvince() +
                                detailBean.getAddress().getCity() +
                                detailBean.getAddress().getDistrict() +
                                detailBean.getAddress().getDetail()
                );
                tvOrderUserInfo.setText(detailBean.getAddress().getName() + " " + detailBean.getAddress().getPhone());
                if (detailBean.getLogistics().getMessage().equals("ok")) {

                    tvLogisticsTitle.setText(detailBean.getFarename());
                    if(detailBean.getLogistics().getData()!=null&&detailBean.getLogistics().getData().size()>0){
                        String content = detailBean.getLogistics().getData().get(0).getContext();
                        if(TextUtils.isEmpty(content)||content.equals("查无结果")){
                            content = "暂无物流信息";
                        }
                        tvLogistics.setText(content);
                    }

                    tvOrderPeisongWay.setText(String.format("(运单号:%s)%s", detailBean.getLogistics().getNu(), detailBean.getFarename()));
                } else {
                    tvOrderPeisongWay.setText("暂无配送方式");
                }

                //订单信息
                tvOrderNo.setText(detailBean.getId());
                tvOrderDate.setText(TimeUtils.millis2String(Long.parseLong(detailBean.getCreatetime()) * 1000, "yyyy.MM.dd HH:mm:ss"));
                //
                switch (detailBean.getStatus()) {//0-提交成功 1-待发货 2-配送中 3-已收货 4-申请退货 5-商家确认收货（完成） 6-未生效的订单 7-完成 8-换货
                    case "0":
                        tvOrderStatus.setText("订单提交成功");
                        tvOrderTips.setVisibility(View.GONE);
                        break;
                    case "1":
                        tvOrderStatus.setText("待发货");
                        tvOrderTips.setVisibility(View.GONE);
                        break;
                    case "2":
                        tvOrderStatus.setText("配送中");
                        tvOrderTips.setVisibility(View.GONE);
                        break;
                    case "3":
                        tvOrderStatus.setText("已收货");
                        tvOrderTips.setVisibility(View.GONE);
                        break;
                    case "4":
                        tvOrderStatus.setText("申请退货");
                        tvOrderTips.setVisibility(View.GONE);
                        break;
                    case "5":
                        tvOrderStatus.setText("商家确认收货");
                        tvOrderTips.setVisibility(View.GONE);
                        break;
                    case "6":
                        tvOrderStatus.setText("未生效的订单");
                        tvOrderTips.setVisibility(View.GONE);
                        break;
                    case "7":
                        tvOrderStatus.setText("订单已完成");
                        tvOrderTips.setVisibility(View.VISIBLE);
                        break;
                    case "8":
                        tvOrderStatus.setText("换货");
                        tvOrderTips.setVisibility(View.GONE);
                        break;
                }
                switch (detailBean.getIspay()) {//0-未支付 1-已支付
                    case "0":
                        tvOrderPayType.setText("未支付");
                        break;
                    case "1":
                        switch (detailBean.getPayment()) {
                            //0-在线 1-到付 2-支付宝 3-微信 4-银联 5-余额支付
                            //1为商城红米，2为分享红米，3为交易红米 4支付宝 5微信
                            case "0":
                                tvOrderPayType.setText("在线支付");
                                break;
                            case "1":
                                tvOrderPayType.setText("商城红米");
                                break;
                            case "2":
                                tvOrderPayType.setText("分享红米");
                                break;
                            case "3":
                                tvOrderPayType.setText("交易红米");
                                break;
                            case "4":
                                tvOrderPayType.setText("支付宝");
                                break;
                            case "5":
                                tvOrderPayType.setText("微信");
                                break;
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}