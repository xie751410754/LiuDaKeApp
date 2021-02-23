package com.cdxz.liudake.adapter.shop_mall;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.HomeIndexBean;
import com.cdxz.liudake.bean.Menu2Bean;
import com.cdxz.liudake.ui.SearchActivity;
import com.cdxz.liudake.ui.shop_mall.GoodsClassActivity;
import com.cdxz.liudake.ui.shop_mall.GoodsListActivity;
import com.cdxz.liudake.ui.shop_mall.HomeToGoodsListActivity;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class Menu2Adapter extends BaseQuickAdapter<HomeIndexBean.GoodsCuxiao1Bean, BaseViewHolder> {
    public Menu2Adapter(List<HomeIndexBean.GoodsCuxiao1Bean> data) {
        super(R.layout.item_shop_mall_menu2, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeIndexBean.GoodsCuxiao1Bean bean) {
        baseViewHolder.setText(R.id.tvTips, bean.getTitle())
                .setText(R.id.tvTips2, bean.getSubtitle());
        String[] urls = bean.getUrl().split(",");
        Glide.with(getContext())
                .load(urls[0].startsWith("http") ? urls[0] : Constants.PICTURE_HTTPS_URL + urls[0])
                .placeholder(R.mipmap.img_default)
                .into((RoundedImageView) baseViewHolder.getView(R.id.ivImage1));
        Glide.with(getContext())
                .load(urls[1].startsWith("http") ? urls[1] : Constants.PICTURE_HTTPS_URL + urls[1])
                .placeholder(R.mipmap.img_default)
                .into((RoundedImageView) baseViewHolder.getView(R.id.ivImage2));
//        baseViewHolder.itemView.setOnClickListener(v -> {
////            GoodsListActivity.startGoodsListActivity(getContext());
//            SearchActivity.startSearchActivity(getContext(), "手机");
//        });
        baseViewHolder.itemView.setOnClickListener(v -> {
            HomeToGoodsListActivity.startHomeToGoodsListActivity(
                    getContext(), bean.getId(), bean.getTitle(), HomeToGoodsListActivity.HOME2
            );
        });
    }
}
