package com.cdxz.liudake.adapter.my.service;

import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.CollectGoodsBean;
import com.cdxz.liudake.ui.shop_mall.GoodsDetailActivity;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CollectGoodsAdapter extends BaseQuickAdapter<CollectGoodsBean, BaseViewHolder> {
    public CollectGoodsAdapter(@Nullable List<CollectGoodsBean> data) {
        super(R.layout.item_collect_goods, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CollectGoodsBean bean) {
        Glide.with(getContext())
                .load(bean.getLogo())
                .placeholder(R.mipmap.img_default)
                .into((ImageView) baseViewHolder.getView(R.id.ivGoodsPic));
        baseViewHolder.setText(R.id.tvGoodsName, bean.getName())
                .setText(R.id.tvGoodsPrice, "￥" + bean.getSaleprice())
                .setText(R.id.tvGoodsOldPrice, "￥" + bean.getMemberprice())
                .setText(R.id.tvSaleNum, "已售 " + bean.getSalescount())
                .setText(R.id.tvScore, "积分 " + bean.getGold());

        TextView tvGoodsOldPrice = baseViewHolder.getView(R.id.tvGoodsOldPrice);
        tvGoodsOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        baseViewHolder.itemView.setOnClickListener(v -> {
            GoodsDetailActivity.startGoodsDetailActivity(getContext(), bean.getId());
        });
    }
}
