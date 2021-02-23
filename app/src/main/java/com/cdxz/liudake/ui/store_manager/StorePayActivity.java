package com.cdxz.liudake.ui.store_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.bean.LifeCircleDetailBean;
import com.cdxz.liudake.pop.PopPayPwd;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.order.OrderListActivity;
import com.cdxz.liudake.ui.order.OrderSubmitActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.DrawableTextView;
import com.lxj.xpopup.XPopup;

import butterknife.BindView;

public class StorePayActivity extends BaseActivity {

    @BindView(R.id.tvStoreName)
    TextView tvStoreName;

    @BindView(R.id.etMoney)
    EditText etMoney;

    @BindView(R.id.tvMallPay)
    DrawableTextView tvMallPay;

    @BindView(R.id.tvShareRedmiPay)
    DrawableTextView tvShareRedmiPay;

    @BindView(R.id.tvJiaoyiRedmiPay)
    DrawableTextView tvJiaoyiRedmiPay;

    private String shop_id;
    private int payment;

    public static void startStorePayActivity(Context context, String shop_id) {
        Intent intent = new Intent(context, StorePayActivity.class);
        intent.putExtra("shop_id", shop_id);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_store_pay;
    }

    @Override
    protected void initViews() {
        setTitleText("支付");
        payment = 1;
        tvMallPay.setLeftDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_y));
        tvShareRedmiPay.setLeftDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
        tvJiaoyiRedmiPay.setLeftDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
    }

    @Override
    protected void initDatas() {
        shop_id = getIntent().getStringExtra("shop_id");
        HttpsUtil.getInstance(this).shopDetail(shop_id, object -> {
            LifeCircleDetailBean detailBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), LifeCircleDetailBean.class);
            tvStoreName.setText(String.format("付款给%s的店铺", detailBean.getName()));
        });
    }

    @Override
    protected void initListener() {
        tvMallPay.setOnClickListener(v -> {
            payment = 1;
            tvMallPay.setLeftDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_y));
            tvShareRedmiPay.setLeftDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
            tvJiaoyiRedmiPay.setLeftDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
        });
        tvShareRedmiPay.setOnClickListener(v -> {
            payment = 2;
            tvMallPay.setLeftDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
            tvShareRedmiPay.setLeftDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_y));
            tvJiaoyiRedmiPay.setLeftDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
        });
        tvJiaoyiRedmiPay.setOnClickListener(v -> {
            payment = 3;
            tvMallPay.setLeftDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
            tvShareRedmiPay.setLeftDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_n));
            tvJiaoyiRedmiPay.setLeftDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_pay_y));
        });
        findViewById(R.id.tvSubmitPay).setOnClickListener(v -> {
            if (StringUtils.isEmpty(etMoney.getText().toString())) {
                ToastUtils.showShort("请输入支付金额");
                return;
            }
            new XPopup.Builder(this).asCustom(new PopPayPwd(this, text -> {
                HttpsUtil.getInstance(this).appCreateOrder(etMoney.getText().toString(), 1, shop_id, payment, text, object -> {
                    finish();
                });
            })).show();
        });
    }
}
