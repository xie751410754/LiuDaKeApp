package com.cdxz.liudake.adapter.shop_mall;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.HomeIndexBean;
import com.cdxz.liudake.ui.shop_mall.GoodsDetailActivity;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HomeQianggouAdapter extends BaseQuickAdapter<HomeIndexBean.TimeBean.ListBean, BaseViewHolder> {
    public HomeQianggouAdapter(@Nullable List<HomeIndexBean.TimeBean.ListBean> data) {
        super(R.layout.item_qianggou, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeIndexBean.TimeBean.ListBean bean) {
        Glide.with(getContext())
                .load(bean.getUrl())
                .placeholder(R.mipmap.img_default)
                .into((RoundedImageView) baseViewHolder.getView(R.id.ivQiangGouPicture));
        baseViewHolder.setText(R.id.tvQiangGouName, bean.getName())
                .setText(R.id.tvQiangGouPrice, String.format("￥%s", bean.getGold() > 0 ? bean.getTime_price() + "+" + bean.getGold() + "积分" : bean.getTime_price()));
        baseViewHolder.itemView.setOnClickListener(v -> {
            GoodsDetailActivity.startGoodsDetailActivity(getContext(), bean.getGoods_id(),true);
        });
    }
}
