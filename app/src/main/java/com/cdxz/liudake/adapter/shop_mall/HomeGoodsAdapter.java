package com.cdxz.liudake.adapter.shop_mall;

import android.graphics.Paint;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.HomeIndexBean;
import com.cdxz.liudake.ui.shop_mall.GoodsDetailActivity;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class HomeGoodsAdapter extends BaseQuickAdapter<HomeIndexBean.GoodsCuxiao4Bean, BaseViewHolder> {
    public HomeGoodsAdapter(List<HomeIndexBean.GoodsCuxiao4Bean> data) {
        super(R.layout.item_shop_mall_goods, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeIndexBean.GoodsCuxiao4Bean goodsBean) {
        baseViewHolder.itemView.setOnClickListener(v -> {
            GoodsDetailActivity.startGoodsDetailActivity(getContext(), goodsBean.getId());
        });
//        LogUtils.e("数据 = " + GsonUtils.toJson(goodsBean));
        if (!StringUtils.isEmpty(goodsBean.getUrl())) {
            Glide.with(getContext())
                    .load(goodsBean.getUrl().startsWith("http") ? goodsBean.getUrl() : Constants.PICTURE_HTTPS_URL + goodsBean.getUrl())
                    .placeholder(R.mipmap.img_default)
                    .into((RoundedImageView) baseViewHolder.getView(R.id.ivImage));
        }
        baseViewHolder.setText(R.id.tvGoodsName, goodsBean.getName())
                .setText(R.id.tvGoodsNewPrice, "¥" + goodsBean.getSaleprice())
                .setText(R.id.tvGoodsPrice, "¥" + goodsBean.getOrginalprice())
                .setText(R.id.tvSellNum, "已售 " + goodsBean.getSalescount())
                .setText(R.id.tvScore, "积分 " + goodsBean.getGold());

        TextView tvGoodsPrice = baseViewHolder.getView(R.id.tvGoodsPrice);
        tvGoodsPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }
}
