package com.cdxz.liudake.adapter.my.service;

import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.ToPromoteBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class ToPromoteAdapter extends BaseQuickAdapter<ToPromoteBean.ListBean, BaseViewHolder> {
    public ToPromoteAdapter(List<ToPromoteBean.ListBean> data) {
        super(R.layout.item_top_romote_new, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ToPromoteBean.ListBean bean) {
        baseViewHolder.setText(R.id.tvUser, bean.getPhone())
//                .setText(R.id.tvRemark, bean.getRemark())
                .setText(R.id.tvDate, TimeUtils.millis2String(Long.parseLong(bean.getCreatetime()) * 1000, "yyyy.MM.dd HH:mm"))
                .setText(R.id.tvShouyi, "+" + bean.getAmount());
        TextView tvRemark = baseViewHolder.getView(R.id.tvRemark);
        if (Integer.parseInt(bean.getShopnums()) > 0) {
            tvRemark.setText("直推商户");
        } else {
            tvRemark.setText("直推用户");
        }
    }
}
