package com.cdxz.liudake.adapter.my.service;

import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.TimeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.RedmiBillBean;
import com.cdxz.liudake.bean.ScoreBillBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class ScoreBillAdapter extends BaseQuickAdapter<ScoreBillBean.ListBean, BaseViewHolder> {
    public ScoreBillAdapter(List<ScoreBillBean.ListBean> data) {
        super(R.layout.item_redmi_bill_new, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder baseViewHolder, ScoreBillBean.ListBean bean) {
        baseViewHolder.setText(R.id.tvDate, TimeUtils.millis2String(Long.parseLong(bean.getCreate_time()) * 1000, "yyyy.MM.dd HH:mm"));
        TextView tvTitle = baseViewHolder.getView(R.id.tvTitle);
//        TextView tvDesc = baseViewHolder.getView(R.id.tvDesc);
        TextView tvJinE = baseViewHolder.getView(R.id.tvJinE);
        tvTitle.setText(bean.getRemark());
        tvJinE.setText(bean.getAmount());
        switch (bean.getIn_out()) {
            case "1"://购物下单 减
                String t1 = "购物下单使用";
                String j1 = "+";
//                tvTitle.setText(t1);
                tvJinE.setText(j1 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FF6600));

                break;
            case "2"://订单完成 加
                String t2 = "订单完成赠送";
                String j2 = "-";
//                tvTitle.setText(t2);
                tvJinE.setText(j2 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_222222));

                break;
            case "3"://线下消费 加
                String t3 = "线下消费赠送";
                String j3 = "+";
//                tvTitle.setText(t3);
                tvJinE.setText(j3 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FF6600));

                break;
            case "4"://积分转换为红米 减
                String t4 = "积分转换红米";
                String j4 = "-";
//                tvTitle.setText(t4);
                tvJinE.setText(j4 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_222222));

                break;
        }
    }
}
