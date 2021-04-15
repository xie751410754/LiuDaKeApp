package com.cdxz.liudake.adapter.my.service;

import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.TimeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.RedmiBillBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class RedmiBillAdapter extends BaseQuickAdapter<RedmiBillBean.ListBean, BaseViewHolder> {
    public RedmiBillAdapter(List<RedmiBillBean.ListBean> data) {
        super(R.layout.item_redmi_bill_new, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, RedmiBillBean.ListBean bean) {
        baseViewHolder.setText(R.id.tvTitle, bean.getRemark())
//                .setText(R.id.tvDesc, bean.getRemark())
                .setText(R.id.tvDate, TimeUtils.millis2String(Long.parseLong(bean.getCreate_time()) * 1000, "yyyy.MM.dd HH:mm"))
                .setText(R.id.tvJinE, bean.getAmount());
        TextView tvTitle = baseViewHolder.getView(R.id.tvTitle);
        TextView tvJinE = baseViewHolder.getView(R.id.tvJinE);

        switch (bean.getType()) {
            case "1"://购物下单 减
                String t1 = "直推用户返佣";
                String j1 = "+";
//                tvTitle.setText(t1);
                tvJinE.setText(j1 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FF6600));

                break;
            case "2"://订单完成 加
                String t2 = "直推商家返佣";
                String j2 = "+";
//                tvTitle.setText(t2);
                tvJinE.setText(j2 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FF6600));

                break;
            case "3"://线下消费 加
                String t3 = "代理商提成";
                String j3 = "+";
//                tvTitle.setText(t3);
                tvJinE.setText(j3 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FF6600));

                break;
            case "4"://积分转换为红米 减
                String t4 = "多级收益提成";
                String j4 = "+";
//                tvTitle.setText(t4);
                tvJinE.setText(j4 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FF6600));

                break;
            case "5":
                String t5 = "积分自动兑换为红米";
                String j5 = "+";
//                tvTitle.setText(t5);
                tvJinE.setText(j5 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FF6600));

                break;
            case "6":
                String t6 = "会员提现";
                String j6 = "-";
//                tvTitle.setText(t6);
                tvJinE.setText(j6 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_222222));
                break;
            case "7":
                String t7 = "商城消费";
                String j7 = "-";
//                tvTitle.setText(t7);
                tvJinE.setText(j7 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_222222));
                break;
            case "8":
                String t8 = "线下消费";
                String j8 = "-";
                tvTitle.setText(t8);
                tvJinE.setText(j8 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_222222));
                break;
            case "9":
                String t9 = "平台分红";
                String j9 = "-";
//                tvTitle.setText(t9);
                tvJinE.setText(j9 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_222222));
                break;
            default:
                String t10 = "购物下单使用";
                String j10 = "-";
//                tvTitle.setText(t10);
                tvJinE.setText(j10 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_222222));

                break;

        }


    }
}
