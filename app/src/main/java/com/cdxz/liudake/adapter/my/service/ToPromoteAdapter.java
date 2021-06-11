package com.cdxz.liudake.adapter.my.service;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.ToPromoteBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;

import java.util.List;

public class ToPromoteAdapter extends BaseQuickAdapter<ToPromoteBean.ListBean, BaseViewHolder> {
    public ToPromoteAdapter(List<ToPromoteBean.ListBean> data) {
        super(R.layout.item_top_romote_new, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ToPromoteBean.ListBean bean) {
        baseViewHolder.setText(R.id.tvUser, bean.getName()+"  "+bean.getPhone())
//                .setText(R.id.tvRemark, bean.getRemark())
                .setText(R.id.tvDate, TimeUtils.millis2String(Long.parseLong(bean.getCreatetime()) * 1000, "yyyy.MM.dd HH:mm"))
                .setText(R.id.tvShouyi, "+" + bean.getAmount());
        TextView tvRemark = baseViewHolder.getView(R.id.tvRemark);
        if (Integer.parseInt(bean.getShopnums()) > 0) {
            tvRemark.setText("直推商户");
        } else {
            tvRemark.setText("直推用户");
        }
        baseViewHolder.getView(R.id.tvUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XPopup.Builder(getContext())
                        .asConfirm("拨打电话", "是否拨打" + bean.getPhone(), () -> {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            Uri data = Uri.parse("tel:" + bean.getPhone());
                            intent.setData(data);
                            getContext().startActivity(intent);
                        }).show();
            }
        });
    }
}
