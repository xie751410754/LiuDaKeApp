package com.cdxz.liudake.adapter.my;

import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.LifeCircleCatBean;
import com.cdxz.liudake.bean.ServiceBean;
import com.cdxz.liudake.view.DrawableTextView;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class LifecircleCategoryAdapter extends BaseQuickAdapter<LifeCircleCatBean, BaseViewHolder> {
    public LifecircleCategoryAdapter(List<LifeCircleCatBean> data) {
        super(R.layout.item_life_category, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, LifeCircleCatBean serviceBean) {
        baseViewHolder.setText(R.id.tv_name, serviceBean.getName());
        Glide.with(getContext())
                .load(Constants.PICTURE_HTTPS_URL + serviceBean.getLogo())
                .placeholder(R.mipmap.img_default)
                .into((ImageView) baseViewHolder.getView(R.id.iv_logo));
    }
}
