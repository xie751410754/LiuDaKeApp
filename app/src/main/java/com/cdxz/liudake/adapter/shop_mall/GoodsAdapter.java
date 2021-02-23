package com.cdxz.liudake.adapter.shop_mall;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.GoodsBean;
import com.cdxz.liudake.ui.shop_mall.GoodsDetailActivity;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class GoodsAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
    public GoodsAdapter(List<GoodsBean> data) {
        super(R.layout.item_shop_mall_goods, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, GoodsBean goodsBean) {
        baseViewHolder.itemView.setOnClickListener(v -> {
            GoodsDetailActivity.startGoodsDetailActivity(getContext(), goodsBean.getId());
        });
        Glide.with(getContext())
                .load(goodsBean.getLogo().startsWith("http") ? goodsBean.getLogo() : Constants.PICTURE_HTTPS_URL + goodsBean.getLogo())
                .placeholder(R.mipmap.img_default)
                .into((RoundedImageView) baseViewHolder.getView(R.id.ivImage));
        baseViewHolder.setText(R.id.tvGoodsName, goodsBean.getName())
                .setText(R.id.tvGoodsNewPrice, "¥" + goodsBean.getSaleprice())
                .setText(R.id.tvGoodsPrice, "¥" + goodsBean.getOrginalprice())
                .setText(R.id.tvSellNum, "已售 " + goodsBean.getSalescount())
                .setText(R.id.tvScore, "积分 " + goodsBean.getGold());



        try {
            if(Double.parseDouble(goodsBean.getSaleprice())==0&&Double.parseDouble(goodsBean.getGold())>0){
                baseViewHolder.getView(R.id.tvSellNum).setVisibility(View.INVISIBLE);
            }else{
                baseViewHolder.getView(R.id.tvSellNum).setVisibility(View.VISIBLE);
            }
        }catch (Exception e){

        }


        TextView tvGoodsPrice = baseViewHolder.getView(R.id.tvGoodsPrice);
        tvGoodsPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }
}

