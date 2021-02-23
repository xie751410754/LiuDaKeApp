package com.cdxz.liudake.adapter.order;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.OrderDetailBean;
import com.cdxz.liudake.bean.OrderListBean;
import com.cdxz.liudake.ui.order.ApplyAfterSalesActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class OrderDetailGoodsAdapter extends BaseQuickAdapter<OrderListBean.ListBean, BaseViewHolder> {
    private String order_id;

    public OrderDetailGoodsAdapter(List<OrderListBean.ListBean> data, String order_id) {
        super(R.layout.item_order_detail_goods, data);
        this.order_id = order_id;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void convert(BaseViewHolder baseViewHolder, OrderListBean.ListBean bean) {
        Glide.with(getContext())
                .load(bean.getLogo())
                .placeholder(R.mipmap.img_default)
                .into((ImageView) baseViewHolder.getView(R.id.ivGoodsPic));
        baseViewHolder.setText(R.id.tvGoodsName, bean.getName())
                .setText(R.id.tvGoodsPrice, String.format("￥%s", bean.getGoods_price()))
                .setText(R.id.tvGoodsNum, "x" + bean.getBuycount());
        TextView tvJifen = baseViewHolder.getView(R.id.tvJifen);
        if (bean.getGold().equals("0")) {
            tvJifen.setVisibility(View.GONE);
        } else {
            tvJifen.setVisibility(View.VISIBLE);
            tvJifen.setText("+积分" + bean.getGold());
        }

        TextView tvShouhou = baseViewHolder.getView(R.id.tvShouhou);
        switch (bean.getStatus()) {//0-正常 1-售后中 2-售后完成 3-已拒绝
            case "0":
                tvShouhou.setVisibility(View.VISIBLE);
                tvShouhou.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("order_id", order_id);
                    bundle.putSerializable("goods", bean);
                    ActivityUtils.startActivity(bundle, ApplyAfterSalesActivity.class);
                });
                break;
            case "1":
                tvShouhou.setVisibility(View.VISIBLE);
                tvShouhou.setText("售后中");
                break;
            case "2":
                tvShouhou.setVisibility(View.VISIBLE);
                tvShouhou.setText("售后完成");
                break;
            case "3":
                tvShouhou.setVisibility(View.VISIBLE);
                tvShouhou.setText("已拒绝");
                break;
            default:
                tvShouhou.setVisibility(View.GONE);
                break;
        }
    }
}
