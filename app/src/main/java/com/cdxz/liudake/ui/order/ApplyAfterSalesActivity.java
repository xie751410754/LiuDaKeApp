package com.cdxz.liudake.ui.order;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.bean.OrderListBean;
import com.cdxz.liudake.databinding.ActivityApplyAfterSalesBinding;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;

public class ApplyAfterSalesActivity extends AppCompatActivity {

    private ActivityApplyAfterSalesBinding binding;
    private String order_id;
    private OrderListBean.ListBean goods;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_apply_after_sales);
        ImmersionBar.with(this).statusBarColor(R.color.transparent).statusBarDarkFont(true).init();
        binding.setLifecycleOwner(this);
        //
        ((TextView) binding.include.findViewById(R.id.tvTitleText)).setText("申请售后");
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        order_id = bundle.getString("order_id");
        goods = (OrderListBean.ListBean) bundle.getSerializable("goods");
        Glide.with(this)
                .load(goods.getLogo())
                .placeholder(R.mipmap.img_default)
                .into(binding.ivGoodsPic);
        binding.tvGoodsName.setText(goods.getName());
        binding.tvGoodsPrice.setText(String.format("￥%s", goods.getGoods_price()));
        if ("0".equals(goods.getGold())) {
            binding.tvJifen.setVisibility(View.GONE);
        } else {
            binding.tvJifen.setVisibility(View.VISIBLE);
            binding.tvJifen.setText(String.format("+积分%s", goods.getGold()));
        }
        binding.tvGoodsNum.setText(String.format("x%s", goods.getBuycount()));
    }


    public void onBack(View view) {
        finish();
    }

    public void onSubmit(View view) {
        if (StringUtils.isEmpty(order_id) || ObjectUtils.isEmpty(goods)) {
            return;
        }
        int type = 0;
        if (!binding.rbtn1.isChecked() && !binding.rbtn2.isChecked()) {
            ToastUtils.showShort("请选择售后方式");
            return;
        }
        if (binding.rbtn1.isChecked()) {
            type = 1;
        }
        if (binding.rbtn2.isChecked()) {
            type = 2;
        }
        if (StringUtils.isEmpty(binding.etContent.getText().toString())) {
            ToastUtils.showShort("请输入售后理由");
            return;
        }
        HttpsUtil.getInstance(this).applyAfterSales(order_id, goods.getId(), type, binding.etContent.getText().toString(), object -> {
            finish();
        });
    }
}