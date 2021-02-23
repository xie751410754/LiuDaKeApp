package com.cdxz.liudake.adapter.shop_mall;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.HomeIndexBean;
import com.cdxz.liudake.ui.shop_mall.GoodsClassActivity;
import com.cdxz.liudake.ui.shop_mall.GoodsListActivity;
import com.cdxz.liudake.ui.shop_mall.HomeToGoodsListActivity;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class ActivityAdapter extends BaseQuickAdapter<HomeIndexBean.GoodsCuxiao2Bean, BaseViewHolder> {
    public ActivityAdapter(List<HomeIndexBean.GoodsCuxiao2Bean> data) {
        super(R.layout.item_shop_mall_activity, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeIndexBean.GoodsCuxiao2Bean bean) {
        Glide.with(getContext())
                .load(bean.getUrl().startsWith("http") ? bean.getUrl() : Constants.PICTURE_HTTPS_URL + bean.getUrl())
                .placeholder(R.mipmap.img_default)
                .into((RoundedImageView) baseViewHolder.getView(R.id.ivImage));
        baseViewHolder.setText(R.id.tvTitle, bean.getTitle());
        baseViewHolder.itemView.setOnClickListener(v -> {
            HomeToGoodsListActivity.startHomeToGoodsListActivity(
                    getContext(), bean.getId(), bean.getTitle(), HomeToGoodsListActivity.HOME2
            );
        });
    }
}
