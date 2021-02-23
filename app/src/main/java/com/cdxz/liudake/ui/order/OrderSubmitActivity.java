package com.cdxz.liudake.ui.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.order.OrderSubmitAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.AddressListBean;
import com.cdxz.liudake.bean.ShopCarSettlementBean;
import com.cdxz.liudake.bean.SubmitOrderBean;
import com.cdxz.liudake.pop.PopPayPwd;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.my.service.AddressListActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.PayResult;
import com.cdxz.liudake.view.DrawableTextView;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.InputConfirmPopupView;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;

public class OrderSubmitActivity extends BaseActivity {

    @BindView(R.id.addressRootLayout)
    ConstraintLayout addressRootLayout;

    @BindView(R.id.tvAddAddress)
    TextView tvAddAddress;

    @BindView(R.id.tvAddress)
    DrawableTextView tvAddress;

    @BindView(R.id.tvPerson)
    DrawableTextView tvPerson;

    @BindView(R.id.goodsLayout)
    LinearLayout goodsLayout;

    @BindView(R.id.tvGoodsPrice)
    TextView tvGoodsPrice;

    @BindView(R.id.tvExpressFee)
    TextView tvExpressFee;

    @BindView(R.id.tvRedmi)
    TextView tvRedmi;

    @BindView(R.id.tvJifen1)
    TextView tvJifen1;

    @BindView(R.id.tvPayPrice)
    TextView tvPayPrice;

    @BindView(R.id.tvJifen)
    TextView tvJifen;

    @BindView(R.id.tvWXPay)
    DrawableTextView tvWXPay;

    @BindView(R.id.tvZfbPay)
    DrawableTextView tvZfbPay;

    @BindView(R.id.tvMallRedmiPay)
    DrawableTextView tvMallRedmiPay;

    @BindView(R.id.tvShareRedmiPay)
    DrawableTextView tvShareRedmiPay;

    @BindView(R.id.tvJiaoyiRedmiPay)
    DrawableTextView tvJiaoyiRedmiPay;

    @BindView(R.id.tvRedmiDeduction)
    DrawableTextView tvRedmiDeduction;

    @BindView(R.id.tvIntegralDeduction)
    DrawableTextView tvIntegralDeduction;

    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;

    private ShopCarSettlementBean bean;
    private int pay_type = 0;
    private String addressid;
    //微信支付
    private IWXAPI api;

    public static void startOrderSubmitActivity(Context context, ShopCarSettlementBean bean) {
        Intent intent = new Intent(context, OrderSubmitActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    final Handler mHandler = new Handler(msg -> {
        int what = msg.what;
        if (what == 4) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (StringUtils.equals(resultStatus, "9000")) {
                ToastUtils.showShort("支付成功");
                BusUtils.post(BusTag.UPDATE_CAR);
                OrderListActivity.startOrderListActivity(OrderSubmitActivity.this, 0);
                finish();
            } else {
                ToastUtils.showShort("支付失败");
            }
        } else if (what == 5) {
            try {
                String data = (String) msg.obj;
                JSONObject jsonObject = new JSONObject(data);
                String appid = jsonObject.getString("appid");
                String noncestr = jsonObject.getString("noncestr");
                String packageX = jsonObject.getString("package");
                String partnerid = jsonObject.getString("partnerid");
                String prepayid = jsonObject.getString("prepayid");
                String timestamp = jsonObject.getString("timestamp");
                String sign = jsonObject.getString("sign");
                //
                PayReq req = new PayReq();
                req.appId = appid;
                req.partnerId = partnerid;
                req.prepayId = prepayid;
                req.nonceStr = noncestr;
                req.timeStamp = timestamp;
                req.packageValue = packageX;
                req.sign = sign;
                api.sendReq(req);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    });

    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_submit;
    }

    @Override
    protected void initViews() {
        setTitleText("确认订单");
        addressRootLayout.setVisibility(View.GONE);
        tvAddAddress.setVisibility(View.VISIBLE);

        selectPay(4);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void initDatas() {
        BusUtils.register(this);
        api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);
        bean = (ShopCarSettlementBean) getIntent().getSerializableExtra("bean");
        assert bean != null;
        if (ObjectUtils.isNotEmpty(bean.getAddress()) && !StringUtils.isEmpty(bean.getAddress().getPhone())) {
            addressRootLayout.setVisibility(View.VISIBLE);
            tvAddAddress.setVisibility(View.GONE);
            addressid = bean.getAddress().getId();
            tvAddress.setText(bean.getAddress().getProvince() + bean.getAddress().getCity() + bean.getAddress().getDistrict() + bean.getAddress().getDetail());
            tvPerson.setText(bean.getAddress().getName() + "  " + bean.getAddress().getPhone());
        } else {
            addressRootLayout.setVisibility(View.GONE);
            tvAddAddress.setVisibility(View.VISIBLE);
        }
        goodsLayout.removeAllViews();
        float jifen = 0;
        for (int i = 0; i < bean.getList().size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_order_submit, null);
            if (bean.getList().get(i).getShop_logo().startsWith("http://") || bean.getList().get(i).getShop_logo().startsWith("https://")) {
                Glide.with(this)
                        .load(bean.getList().get(i).getShop_logo())
                        .placeholder(R.mipmap.img_default)
                        .into((RoundedImageView) view.findViewById(R.id.ivStorePic));
            } else {
                Glide.with(this)
                        .load(Constants.PICTURE_HTTPS_URL + bean.getList().get(i).getShop_logo())
                        .placeholder(R.mipmap.img_default)
                        .into((RoundedImageView) view.findViewById(R.id.ivStorePic));
            }
            ((TextView) view.findViewById(R.id.tvStoreName)).setText(bean.getList().get(i).getName());
            RecyclerView recyclerGoods = view.findViewById(R.id.recyclerGoods);
            recyclerGoods.setLayoutManager(new LinearLayoutManager(this));
            recyclerGoods.setAdapter(new OrderSubmitAdapter(bean.getList().get(i).getList()));
            goodsLayout.addView(view);
            for (int j = 0; j < bean.getList().get(i).getList().size(); j++) {
                float jf = Float.parseFloat(bean.getList().get(i).getList().get(j).getGold())
                        * Integer.parseInt(bean.getList().get(i).getList().get(j).getBuycount());
                jifen += jf;
            }
        }
        refreshUI(bean);
        if (jifen > 0) {
            tvJifen.setText(String.format("+积分%.2f", jifen));
            tvJifen.setVisibility(View.INVISIBLE);
        } else {
            tvJifen.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void initListener() {
        tvAddAddress.setOnClickListener(v -> {
            AddressListActivity.startAddressListActivity(this);
        });
        addressRootLayout.setOnClickListener(v -> {
            AddressListActivity.startAddressListActivity(this);
        });
        tvRedmiDeduction.setOnClickListener(v -> {
            int is_balance = 0;
            int is_gold = 0;

            Object tag = tvRedmiDeduction.getTag();
            if ("NO".equals(tag)) {
                tvRedmiDeduction.setTag("YES");
                tvRedmiDeduction.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_open_y));
                is_balance = 1;
            } else if ("YES".equals(tag)) {
                tvRedmiDeduction.setTag("NO");
                tvRedmiDeduction.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_open_n));
                is_balance = 0;
            }
            if ("NO".equals(tvIntegralDeduction.getTag())) {
                is_gold = 0;
            } else if ("YES".equals(tvIntegralDeduction.getTag())) {
                is_gold = 1;
            }
            settlement(is_balance, is_gold);
        });
        tvIntegralDeduction.setOnClickListener(v -> {
            int is_balance = 0;
            int is_gold = 0;
            if ("NO".equals(tvRedmiDeduction.getTag())) {
                is_balance = 0;
            } else if ("YES".equals(tvRedmiDeduction.getTag())) {
                is_balance = 1;
            }

            Object tag = tvIntegralDeduction.getTag();
            if ("NO".equals(tag)) {
                tvIntegralDeduction.setTag("YES");
                tvIntegralDeduction.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_open_y));
                is_gold = 1;
            } else if ("YES".equals(tag)) {
                tvIntegralDeduction.setTag("NO");
                tvIntegralDeduction.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_open_n));
                is_gold = 0;
            }
            settlement(is_balance, is_gold);
        });
        tvWXPay.setOnClickListener(v -> {
            selectPay(5);
        });
        tvZfbPay.setOnClickListener(v -> {
            selectPay(4);
        });
        tvMallRedmiPay.setOnClickListener(v -> {
            selectPay(1);
        });
        tvShareRedmiPay.setOnClickListener(v -> {
            selectPay(2);
        });
        tvJiaoyiRedmiPay.setOnClickListener(v -> {
            selectPay(3);
        });

        tvSubmit.setOnClickListener(v -> {
            if (StringUtils.isEmpty(addressid)) {
                ToastUtils.showShort("请添加收货地址");
                return;
            }
            new XPopup.Builder(this).asCustom(new PopPayPwd(this, text -> {
                int is_balance = 0;
                int is_gold = 0;
                if ("NO".equals(tvRedmiDeduction.getTag())) {
                    is_balance = 0;
                } else if ("YES".equals(tvRedmiDeduction.getTag())) {
                    is_balance = 1;
                }
                if ("NO".equals(tvIntegralDeduction.getTag())) {
                    is_gold = 0;
                } else if ("YES".equals(tvIntegralDeduction.getTag())) {
                    is_gold = 1;
                }
                HttpsUtil.getInstance(OrderSubmitActivity.this).submitOrder(
                        is_balance, is_gold, addressid, pay_type, text, object -> {
                            SubmitOrderBean orderBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), SubmitOrderBean.class);
                            if (orderBean.getPayprice() > 0) {
                                //调支付宝或微信
                                pay(orderBean.getOrder().getId());
                            } else {
                                BusUtils.post(BusTag.UPDATE_CAR);
                                OrderListActivity.startOrderListActivity(OrderSubmitActivity.this, 0);
                                finish();
                            }
                        });
            })).show();
        });
    }

    @SuppressLint("SetTextI18n")
    @BusUtils.Bus(tag = BusTag.SELECT_ADDRESS)
    public void onSelectAddress(AddressListBean bean) {
        addressRootLayout.setVisibility(View.VISIBLE);
        tvAddAddress.setVisibility(View.GONE);
        addressid = bean.getId();
        tvAddress.setText(bean.getProvince() + bean.getCity() + bean.getDistrict() + bean.getDetail());
        tvPerson.setText(bean.getName() + "  " + bean.getPhone());
    }

    @BusUtils.Bus(tag = BusTag.WX_PAY_SUCCESS)
    public void onPaySuccess() {
        BusUtils.post(BusTag.UPDATE_CAR);
        OrderListActivity.startOrderListActivity(OrderSubmitActivity.this, 0);
        finish();
    }

    private void selectPay(int type) {
        pay_type = type;
        switch (type) {
            case 1:
                tvWXPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvZfbPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvMallRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_y));
                tvShareRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvJiaoyiRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                //
                tvRedmiDeduction.setClickable(true);
                tvIntegralDeduction.setClickable(true);
//                tvRedmiDeduction.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_open_n));
//                tvRedmiDeduction.setClickable(false);
//                tvRedmiDeduction.setTag("NO");
//
//                tvIntegralDeduction.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_open_n));
//                tvIntegralDeduction.setClickable(false);
//                tvIntegralDeduction.setTag("NO");
                break;
            case 2:
                tvWXPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvZfbPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvMallRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvShareRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_y));
                tvJiaoyiRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                //
                tvRedmiDeduction.setClickable(true);
                tvIntegralDeduction.setClickable(true);
//                tvRedmiDeduction.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_open_n));
//                tvRedmiDeduction.setClickable(false);
//                tvRedmiDeduction.setTag("NO");
//
//                tvIntegralDeduction.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_open_n));
//                tvIntegralDeduction.setClickable(false);
//                tvIntegralDeduction.setTag("NO");
                break;
            case 3:
                tvWXPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvZfbPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvMallRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvShareRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvJiaoyiRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_y));

                tvRedmiDeduction.setClickable(true);
                tvIntegralDeduction.setClickable(true);

//                tvRedmiDeduction.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_open_n));
//                tvRedmiDeduction.setClickable(false);
//                tvRedmiDeduction.setTag("NO");
//
//                tvIntegralDeduction.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_open_n));
//                tvIntegralDeduction.setClickable(false);
//                tvIntegralDeduction.setTag("NO");
                break;
            case 4:
                tvWXPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvZfbPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_y));
                tvMallRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvShareRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvJiaoyiRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));

                tvRedmiDeduction.setClickable(true);
                tvIntegralDeduction.setClickable(true);
                break;
            case 5:
                tvWXPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_y));
                tvZfbPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvMallRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvShareRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
                tvJiaoyiRedmiPay.setRightDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));

                tvRedmiDeduction.setClickable(true);
                tvIntegralDeduction.setClickable(true);
                break;
        }
        int is_balance = 0;
        int is_gold = 0;
        if ("NO".equals(tvRedmiDeduction.getTag())) {
            is_balance = 0;
        } else if ("YES".equals(tvRedmiDeduction.getTag())) {
            is_balance = 1;
        }
        if ("NO".equals(tvIntegralDeduction.getTag())) {
            is_gold = 0;
        } else if ("YES".equals(tvIntegralDeduction.getTag())) {
            is_gold = 1;
        }
        settlement(is_balance, is_gold);
    }


    private void settlement(int is_balance, int is_gold) {
        LogUtils.e("pay_type = " + pay_type + "，is_balance = " + is_balance + "，is_gold = " + is_gold);
        HttpsUtil.getInstance(this).settlement(pay_type, is_balance, is_gold, object -> {
            ShopCarSettlementBean settlementBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), ShopCarSettlementBean.class);
            refreshUI(settlementBean);
        });
    }

    /**
     * 微信、支付宝支付
     *
     * @param id
     */
    private void pay(String id) {
        HttpsUtil.getInstance(this).pay(id, pay_type, object -> {
            LogUtils.e("支付 = " + object.toString());
            try {
                JSONObject jsonObject = new JSONObject(GsonUtils.toJson(object));
                String date = jsonObject.getString("data");
                if (pay_type == 4) {//支付宝
                    Runnable payRunnable = () -> {
                        PayTask alipay = new PayTask(this);
                        Map<String, String> result = alipay.payV2(date, true);
                        Message msg = new Message();
                        msg.what = pay_type;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    };
                    // 必须异步调用
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                } else if (pay_type == 5) {//微信
                    Message message = new Message();
                    message.what = pay_type;
                    message.obj = date;
                    mHandler.sendMessage(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 刷新UI
     *
     * @param settlementBean
     */
    @SuppressLint("DefaultLocale")
    private void refreshUI(ShopCarSettlementBean settlementBean) {
        tvGoodsPrice.setText(String.format("商品价：¥%.2f", settlementBean.getPrice()));
        tvExpressFee.setText(String.format("运费：￥%.2f", settlementBean.getFarefee()));
        tvPayPrice.setText(String.format("应付金额：¥%.2f", settlementBean.getTotalprice()));
        tvTotal.setText(String.format("￥%.2f", settlementBean.getTotalprice()));
        if (settlementBean.getDe_gold_isshow() == 1) {
            tvJifen1.setVisibility(View.VISIBLE);
            tvJifen1.setText(String.format("积分抵扣：¥%.2f", settlementBean.getDe_gold()));
        } else {
            tvJifen1.setVisibility(View.GONE);
        }
        if (settlementBean.getBalanceprice_isshow() == 1) {
            tvRedmi.setVisibility(View.VISIBLE);
            tvRedmi.setText(String.format("红米余额抵扣：¥%.2f", settlementBean.getBalanceprice()));
        } else {
            tvRedmi.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
        mHandler.removeCallbacksAndMessages(null);
    }
}