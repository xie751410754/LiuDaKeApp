package com.cdxz.liudake.adapter.life_circle;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.LifeCircleBean;
import com.cdxz.liudake.ui.life_circle.StoreDetailActivity;
import com.cdxz.liudake.ui.life_circle.StoreDetailActivity2;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class LifeCircleAdapter extends BaseQuickAdapter<LifeCircleBean, BaseViewHolder> {
    public LifeCircleAdapter(List<LifeCircleBean> data) {
        super(R.layout.item_store_new, data);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void convert(BaseViewHolder baseViewHolder, LifeCircleBean bean) {
        baseViewHolder.itemView.setOnClickListener(v -> {
            StoreDetailActivity2.startStoreDetailActivity(getContext(), bean.getId());
        });
        if (bean.getLogo().startsWith("http://") || bean.getLogo().startsWith("https://")) {
            Glide.with(getContext())
                    .load(bean.getLogo())
                    .placeholder(R.mipmap.img_default)
                    .into((RoundedImageView) baseViewHolder.getView(R.id.ivStorePic));
        } else {
            Glide.with(getContext())
                    .load(Constants.PICTURE_HTTPS_URL + bean.getLogo())
                    .placeholder(R.mipmap.img_default)
                    .into((RoundedImageView) baseViewHolder.getView(R.id.ivStorePic));
        }
        baseViewHolder.setText(R.id.tvStoreName, bean.getName())
                .setText(R.id.tvDistance, String.format("%.2f km", bean.getDistance() / 1000))
                .setText(R.id.tvAverageMoney, String.format("人均 ¥%s", bean.getAverage_money()))
                .setText(R.id.tv_sales, "销量 "+bean.getSales())
                .setText(R.id.tv_achievement, "销售额 "+bean.getMoney())
                .setText(R.id.tvTime, String.format("营业时间 %s-%s", bean.getOpen_start_time(), bean.getOpen_end_time()));
    }
}
