package com.cdxz.liudake.adapter.shop_mall;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.CategoryListBean;
import com.cdxz.liudake.view.DrawableTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class GoodsClassNameAdapter extends BaseQuickAdapter<CategoryListBean, BaseViewHolder> {
    public GoodsClassNameAdapter(List<CategoryListBean> data) {
        super(R.layout.item_goods_class, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CategoryListBean categoryListBean) {
        View view = baseViewHolder.getView(R.id.view);
        TextView tvName = baseViewHolder.getView(R.id.tvName);
        tvName.setText(categoryListBean.getName());
        if (categoryListBean.isSelect()) {
            view.setVisibility(View.VISIBLE);
            tvName.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FF5033));
        } else {
            view.setVisibility(View.INVISIBLE);
            tvName.setTextColor(ContextCompat.getColor(getContext(), R.color.color_999999));
        }
    }
}
