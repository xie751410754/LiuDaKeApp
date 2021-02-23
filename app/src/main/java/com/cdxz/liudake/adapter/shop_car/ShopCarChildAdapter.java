package com.cdxz.liudake.adapter.shop_car;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.BusUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.bean.ShopCarListBean;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class ShopCarChildAdapter extends BaseQuickAdapter<ShopCarListBean.ListBean, BaseViewHolder> {

    public ShopCarChildAdapter(List<ShopCarListBean.ListBean> data) {
        super(R.layout.item_shop_car_child, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder baseViewHolder, ShopCarListBean.ListBean listBean) {
        Glide.with(getContext())
                .load(listBean.getLogo())
                .placeholder(R.mipmap.img_default)
                .into((RoundedImageView) baseViewHolder.getView(R.id.ivGoodsPic));
        baseViewHolder.setText(R.id.tvGoodsName, listBean.getName())
                .setText(R.id.tvGoodsPrice, "￥" + listBean.getSaleprice())
                .setText(R.id.tvNum, listBean.getBuycount());
        ImageView ivGoodsCheck = baseViewHolder.getView(R.id.ivGoodsCheck);
        if (listBean.getSelected().equals("1")) {
            ivGoodsCheck.setImageResource(R.mipmap.icon_pay_y);
        } else {
            ivGoodsCheck.setImageResource(R.mipmap.icon_pay_n);
        }
        TextView tvNum = baseViewHolder.getView(R.id.tvNum);
        TextView tvJifen = baseViewHolder.getView(R.id.tvJifen);
        if (listBean.getGold().equals("0")) {
            tvJifen.setVisibility(View.GONE);
        } else {
            tvJifen.setVisibility(View.VISIBLE);
            tvJifen.setText("+积分" + listBean.getGold());
        }
        baseViewHolder.getView(R.id.ivMinus).setOnClickListener(v -> {
            BusUtils.post(BusTag.CAR_NUM_MINUS, listBean);
        });
        baseViewHolder.getView(R.id.ivAdd).setOnClickListener(v -> {
            BusUtils.post(BusTag.CAR_NUM_ADD, listBean);
        });
    }

    public interface OnNumChangeListener {
        void onMinusClick(int position, int num);

        void onAddClick(int position, int num);
    }
}
