package com.cdxz.liudake.adapter.life_circle;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.LifeCircleCatBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class LifecircleGoodsImageDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public LifecircleGoodsImageDetailAdapter(List<String> data) {
        super(R.layout.item_goods_image_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        Glide.with(getContext())
                .load(Constants.PICTURE_HTTPS_URL + s)
                .placeholder(R.mipmap.img_default)
                .into((ImageView) baseViewHolder.getView(R.id.iv_logo));
    }
}
