package com.cdxz.liudake.adapter.my.service;

import com.blankj.utilcode.util.TimeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.RedmiBillBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class RedmiBillAdapter extends BaseQuickAdapter<RedmiBillBean.ListBean, BaseViewHolder> {
    public RedmiBillAdapter(List<RedmiBillBean.ListBean> data) {
        super(R.layout.item_redmi_bill, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, RedmiBillBean.ListBean bean) {
        baseViewHolder.setText(R.id.tvTitle, bean.getRemark())
                .setText(R.id.tvDesc, bean.getRemark())
                .setText(R.id.tvDate, TimeUtils.millis2String(Long.parseLong(bean.getCreate_time()) * 1000, "yyyy.MM.dd HH:mm"))
                .setText(R.id.tvJinE, bean.getAmount());
    }
}
