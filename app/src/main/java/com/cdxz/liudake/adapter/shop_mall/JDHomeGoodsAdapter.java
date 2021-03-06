package com.cdxz.liudake.adapter.shop_mall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.HomeIndexBean;
import com.cdxz.liudake.bean.JDGoodsDto;
import com.cdxz.liudake.ui.WebActivity;
import com.cdxz.liudake.ui.shop_mall.GoodsDetailActivity;
import com.cdxz.liudake.util.UserInfoUtil;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;
import java.util.Random;

public class JDHomeGoodsAdapter extends BaseQuickAdapter<JDGoodsDto.DataDTO, BaseViewHolder> {
    public JDHomeGoodsAdapter(List<JDGoodsDto.DataDTO> data) {
        super(R.layout.item_shop_mall_goods_new, data);
    }
    boolean isBaopin=true;

    public void setScore(boolean isBaoPin){
        this.isBaopin = isBaoPin;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, JDGoodsDto.DataDTO goodsBean) {
        baseViewHolder.itemView.setOnClickListener(v -> {
            WebActivity.startWebActivity(getContext(), "京东商品", "http://jd.liudake.cn/#/pagesA/goodDetail/goodDetail?goodNum=" + goodsBean.getGoodNum() + "&uid=" + UserInfoUtil.getUid()+"&rd="+new Random().nextInt(100));
        });
//        LogUtils.e("数据 = " + GsonUtils.toJson(goodsBean));
        if (!StringUtils.isEmpty(goodsBean.getImagePath())) {
            Glide.with(getContext())
                    .asDrawable()
                    .load(goodsBean.getImagePath().startsWith("http") ? goodsBean.getImagePath() : Constants.PICTURE_HTTPS_URL + goodsBean.getImagePath())
                    .placeholder(R.mipmap.img_default)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(200,200)
                    .into((RoundedImageView) baseViewHolder.getView(R.id.ivImage));
        }
        if (isBaopin){
            baseViewHolder.itemView.findViewById(R.id.tvScore).setVisibility(View.VISIBLE);
        }else {
            baseViewHolder.itemView.findViewById(R.id.tvScore).setVisibility(View.GONE);

        }
        baseViewHolder.setText(R.id.tvGoodsName, "                "+goodsBean.getName())
                .setText(R.id.tvGoodsNewPrice, "¥" + goodsBean.getSalePrice() )
                .setText(R.id.tvGoodsPrice, "¥" + goodsBean.getJD_Price())
                .setText(R.id.tvSellNum, "已售 " + goodsBean.getSaleCount())
                .setText(R.id.tvScore, "+"+goodsBean.getJifen() + "积分");

        TextView tvGoodsPrice = baseViewHolder.getView(R.id.tvGoodsPrice);
        tvGoodsPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }


}
