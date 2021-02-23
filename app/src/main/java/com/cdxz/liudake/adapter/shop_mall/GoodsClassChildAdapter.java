package com.cdxz.liudake.adapter.shop_mall;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.CategoryListBean;
import com.cdxz.liudake.ui.SearchActivity;
import com.cdxz.liudake.ui.shop_mall.GoodsListActivity;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class GoodsClassChildAdapter extends BaseQuickAdapter<CategoryListBean.ChildBean.Child2Bean, BaseViewHolder> {
    public GoodsClassChildAdapter(List<CategoryListBean.ChildBean.Child2Bean> data) {
        super(R.layout.item_goods_class_child_child, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CategoryListBean.ChildBean.Child2Bean bean) {
        Glide.with(getContext())
                .load(bean.getLogo())
                .placeholder(R.mipmap.img_default)
                .into((ImageView) baseViewHolder.getView(R.id.ivPic));
        baseViewHolder.setText(R.id.tvName, bean.getName());
        baseViewHolder.itemView.setOnClickListener(v -> {
            GoodsListActivity.startGoodsListActivity(getContext(), bean.getId(), bean.getName());
        });
    }
}
