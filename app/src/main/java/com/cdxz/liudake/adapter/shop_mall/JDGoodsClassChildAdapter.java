package com.cdxz.liudake.adapter.shop_mall;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.CategoryListBean;
import com.cdxz.liudake.bean.JDSecondCategoryDto;
import com.cdxz.liudake.ui.shop_mall.GoodsListActivity;
import com.cdxz.liudake.ui.shop_mall.JDGoodsListActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class JDGoodsClassChildAdapter extends BaseQuickAdapter<JDSecondCategoryDto.DataDTO.ClassArrDTO, BaseViewHolder> {
    public JDGoodsClassChildAdapter(List<JDSecondCategoryDto.DataDTO.ClassArrDTO> data) {
        super(R.layout.item_goods_class_child_child, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, JDSecondCategoryDto.DataDTO.ClassArrDTO bean) {
        Glide.with(getContext())
                .load(bean.getMainPic())
                .placeholder(R.mipmap.img_default)
                .into((ImageView) baseViewHolder.getView(R.id.ivPic));
        baseViewHolder.setText(R.id.tvName, bean.getName());
        baseViewHolder.itemView.setOnClickListener(v -> {

            JDGoodsListActivity.starJDGoodsListActivity(getContext(),bean.getCatId(),bean.getName());
        });
    }
}
