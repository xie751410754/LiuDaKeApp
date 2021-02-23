package com.cdxz.liudake.adapter.shop_mall;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.BusUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.bean.MessageListBean;
import com.cdxz.liudake.ui.shop_mall.MessageDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class MessageListAdapter extends BaseQuickAdapter<MessageListBean, BaseViewHolder> {
    public MessageListAdapter(List<MessageListBean> data) {
        super(R.layout.item_message_list, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MessageListBean messageListBean) {
        ImageView ivIsRead = baseViewHolder.getView(R.id.ivIsRead);
        ImageView ivImage = baseViewHolder.getView(R.id.ivImage);
        TextView tvMgTitle = baseViewHolder.getView(R.id.tvMgTitle);
        if (messageListBean.getIs_read().equals("1")) {
            ivIsRead.setVisibility(View.INVISIBLE);
        } else {
            ivIsRead.setVisibility(View.VISIBLE);
        }
        switch (messageListBean.getMessage_type()) {
            case "1"://认证消息
                ivImage.setImageResource(R.mipmap.img_message_renzheng);
                tvMgTitle.setText("认证消息");
                break;
            case "2"://结算消息
                ivImage.setImageResource(R.mipmap.img_message_jiesuan);
                tvMgTitle.setText("结算消息");
                break;
            case "3"://订单消息
                ivImage.setImageResource(R.mipmap.img_message_order);
                tvMgTitle.setText("订单消息");
                break;
            case "4"://系统消息
                ivImage.setImageResource(R.mipmap.img_message_system);
                tvMgTitle.setText("系统消息");
                break;
        }
        baseViewHolder.setText(R.id.tvMgContent, messageListBean.getContent());
        baseViewHolder.itemView.setOnClickListener(v -> {
            MessageDetailActivity.startMessageDetailActivity(getContext(), messageListBean.getId());
            BusUtils.post(BusTag.UPDATE_MESSAGE_NUM);
            messageListBean.setIs_read("1");
            notifyItemChanged(baseViewHolder.getAdapterPosition());
        });
    }
}
