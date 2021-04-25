package com.cdxz.liudake.adapter.shop_mall;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.HomeIndexBean;
import com.cdxz.liudake.ui.WebActivity;
import com.cdxz.liudake.ui.shop_mall.GoodsClassActivity;
import com.cdxz.liudake.ui.shop_mall.GoodsListActivity;
import com.cdxz.liudake.ui.shop_mall.HomeToGoodsListActivity;
import com.cdxz.liudake.util.UserInfoUtil;
import com.cdxz.liudake.view.DrawableTextView;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;
import java.util.Random;

public class MenuAdapter extends BaseQuickAdapter<HomeIndexBean.GoodsActivityClassBean, BaseViewHolder> {
    public MenuAdapter(List<HomeIndexBean.GoodsActivityClassBean> data) {
        super(R.layout.item_shop_mall_menu, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeIndexBean.GoodsActivityClassBean bean) {
        Glide.with(getContext())
                .load(bean.getUrl().startsWith("http") ? bean.getUrl() : Constants.PICTURE_HTTPS_URL + bean.getUrl())
                .placeholder(R.mipmap.img_default)
                .into((RoundedImageView) baseViewHolder.getView(R.id.ivPic));
        baseViewHolder.setText(R.id.tvTitle, bean.getName());
        baseViewHolder.itemView.setOnClickListener(v -> {
            if (baseViewHolder.getAdapterPosition() + 1 == getData().size()) {
                GoodsClassActivity.startGoodsClassActivity(getContext());
            } else if (baseViewHolder.getAdapterPosition() == 0) {
                WebActivity.startWebActivity(getContext(),6,"http://jd.liudake.cn/#/pages/index/index?uid="+ UserInfoUtil.getUid()+"&rd="+new Random().nextInt(100));
            } else {
                HomeToGoodsListActivity.startHomeToGoodsListActivity(
                        getContext(), bean.getId(), bean.getName(), HomeToGoodsListActivity.HOME1
                );
            }
        });
    }
}
