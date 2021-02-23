package com.cdxz.liudake.adapter.shop_mall;

import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.GoodsDetailBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PopGoodsSpecificaAdapter extends BaseQuickAdapter<GoodsDetailBean.SizeBean.ListBean, BaseViewHolder> {
    public PopGoodsSpecificaAdapter(@Nullable List<GoodsDetailBean.SizeBean.ListBean> data) {
        super(R.layout.item_pop_goods_specifica_child, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, GoodsDetailBean.SizeBean.ListBean listBean) {
        TextView tvText = baseViewHolder.getView(R.id.tvText);
        tvText.setText(listBean.getContent());
        if (listBean.isSelect()) {
            tvText.setTextColor(ContextCompat.getColor(getContext(), R.color.appColor));
            tvText.setBackgroundResource(R.drawable.shape_pop_select);
        } else {
            tvText.setTextColor(ContextCompat.getColor(getContext(), R.color.color_666666));
            tvText.setBackgroundResource(R.drawable.shape_item_history_bg);
        }
    }
}
