package com.cdxz.liudake.adapter.my.service;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.RedmiBillBean;
import com.cdxz.liudake.bean.ScoreBillBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class ScoreBillAdapter extends BaseQuickAdapter<ScoreBillBean.ListBean, BaseViewHolder> {
    public ScoreBillAdapter(List<ScoreBillBean.ListBean> data) {
        super(R.layout.item_redmi_bill, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder baseViewHolder, ScoreBillBean.ListBean bean) {
        baseViewHolder.setText(R.id.tvDate, TimeUtils.millis2String(Long.parseLong(bean.getCreate_time()) * 1000, "yyyy.MM.dd HH:mm"));
        TextView tvTitle = baseViewHolder.getView(R.id.tvTitle);
        TextView tvDesc = baseViewHolder.getView(R.id.tvDesc);
        TextView tvJinE = baseViewHolder.getView(R.id.tvJinE);
        tvTitle.setText(bean.getRemark());
        tvJinE.setText(bean.getAmount());
//        switch (bean.getType()) {
//            case "1"://购物下单 减
//                tvDesc.setText("兑换商品");
//                break;
//            case "2"://订单完成 加
//                if (bean.getStatus().equals("0")) {
//                    tvDesc.setText("待领积分");
//                }
//                break;
//            case "3"://线下消费 加
//                if (bean.getStatus().equals("0")) {
//                    tvDesc.setText("待领积分");
//                }
//                break;
//            case "4"://积分转换为红米 减
//                tvDesc.setText("兑换红米");
//                break;
//        }
    }
}
