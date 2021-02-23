package com.cdxz.liudake.adapter.order;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.ShopCarSettlementBean;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class OrderSubmitAdapter extends BaseQuickAdapter<ShopCarSettlementBean.ListBeanX.ListBean, BaseViewHolder> {
    public OrderSubmitAdapter(List<ShopCarSettlementBean.ListBeanX.ListBean> data) {
        super(R.layout.item_order_submit_child, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder baseViewHolder, ShopCarSettlementBean.ListBeanX.ListBean listBean) {
        Glide.with(getContext())
                .load(listBean.getLogo())
                .placeholder(R.mipmap.img_default)
                .into((RoundedImageView) baseViewHolder.getView(R.id.ivGoodsPic));
        baseViewHolder.setText(R.id.tvGoodsName, listBean.getName())
                .setText(R.id.tvPrice, "￥" + listBean.getPrice())
                .setText(R.id.tvGoodsNum, "x" + listBean.getBuycount());
        TextView tvJifen = baseViewHolder.getView(R.id.tvJifen);
        if (listBean.getGold().equals("0")) {
            tvJifen.setVisibility(View.GONE);
        } else {
            tvJifen.setVisibility(View.VISIBLE);
            tvJifen.setText("+积分" + listBean.getGold());
        }
    }
}
