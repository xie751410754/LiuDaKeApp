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

        switch (bean.getIn_out()) {
            case "1":
                String t1 = "直推用户返佣";
                String j1 = "+";
//                tvTitle.setText(t1);
                tvJinE.setText(j1 + bean.getAmount());
                tvJinE.setTextColor(ContextCompat.getColor(getContext(), R.color.color_FF6600));

                break;
            case "2":
                String t2 = "直推商家返佣";
                String j2 = "-";
//                tvTitle.setText(t2);
                tvJinE.setText(j2 + bean.getAmount());
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
