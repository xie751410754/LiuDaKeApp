package com.cdxz.liudake.ui.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.BasePagerAdapter;
import com.cdxz.liudake.adapter.order.OrderDetailGoodsAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.bean.OrderDetailBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PaySuccessActivity extends BaseActivity {



    @BindView(R.id.tv_orderId)
    TextView tv_orderId;

    @BindView(R.id.tv_totalPrice)
    TextView tv_totalPrice;
    @BindView(R.id.tv_payMent)
    TextView tv_payMent;
    @BindView(R.id.tv_orderInfo)
    TextView tv_orderInfo;
    @BindView(R.id.tv_orderAdress)
    TextView tv_orderAdress;
    private OrderDetailBean orderDetailBean;

    public static void startPaySuccessActivity(Context context, String id) {
        Intent intent = new Intent(context, PaySuccessActivity.class);
        intent.putExtra("id", id);

        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_success;
    }

    @Override
    protected void initViews() {

    }

    String payMent = "支付方式：";
    String orderId = "订单编号：";
    String orderInfo = "收货人：";
    String orderAdress = "收货地址：";
    @SuppressLint("SetTextI18n")
    @Override
    protected void initDatas() {

        HttpsUtil.getInstance(this).orderDetail(getIntent().getStringExtra("id"), object -> {
            OrderDetailBean detailBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), OrderDetailBean.class);
            orderDetailBean = detailBean;
            tv_orderId.setText(orderId+ detailBean.getId());

            tv_totalPrice.setText(String.format("¥%.2f", detailBean.getTotalprice()));
            switch (detailBean.getIspay()) {//0-未支付 1-已支付
                case "0":
                    tv_payMent.setText(payMent+"未支付");
                    break;
                case "1":
                    switch (detailBean.getPayment()) {
                        //0-在线 1-到付 2-支付宝 3-微信 4-银联 5-余额支付
                        //1为商城红米，2为分享红米，3为交易红米 4支付宝 5微信
                        case "0":
                            tv_payMent.setText(payMent+"在线支付");
                            break;
                        case "1":
                            tv_payMent.setText(payMent+"商城红米");
                            break;
                        case "2":
                            tv_payMent.setText(payMent+"分享红米");
                            break;
                        case "3":
                            tv_payMent.setText(payMent+"交易红米");
                            break;
                        case "4":
                            tv_payMent.setText(payMent+"支付宝");
                            break;
                        case "5":
                            tv_payMent.setText(payMent+"微信");
                            break;
                    }
                    break;
            }
            tv_orderInfo.setText(orderInfo+detailBean.getAddress().getName() + " " + detailBean.getAddress().getPhone());
            tv_orderAdress.setText(orderAdress+detailBean.getAddress().getProvince() + detailBean.getAddress().getCity() + detailBean.getAddress().getDistrict() + detailBean.getAddress().getDetail());

        });

    }

    @Override
    protected void initListener() {

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderListActivity.startOrderListActivity(context, 0);
                finish();
            }
        });

        findViewById(R.id.img_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderDetailBean.getList()==null)return;

                for (int i=0;i<orderDetailBean.getList().size();i++){
                    HttpsUtil.getInstance(context).orderAddCar(orderDetailBean.getList().get(i).getGoods_attr(), orderDetailBean.getList().get(i).getId(), Integer.parseInt(orderDetailBean.getList().get(i).getBuycount()), orderDetailBean.getList().get(i).getCx_id(), object -> {

                    });
                }
                BusUtils.post(BusTag.GOODS_DETAIL_TO_CAR);
                BusUtils.post(BusTag.UPDATE_CAR);
                finish();


            }
        });
        findViewById(R.id.img_watch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDetailActivity.startOrderDetailActivity(context,getIntent().getStringExtra("id"));
                finish();
            }
        });



    }

    @Override
    public Resources getResources() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 750);
        } else {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 750);
        }
    }

}