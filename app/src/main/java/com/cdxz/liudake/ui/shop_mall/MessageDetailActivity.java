package com.cdxz.liudake.ui.shop_mall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.bean.MessageListBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;

import butterknife.BindView;

public class MessageDetailActivity extends BaseActivity {

    @BindView(R.id.tvMessageTitle)
    TextView tvMessageTitle;

    @BindView(R.id.tvMessageDate)
    TextView tvMessageDate;

    @BindView(R.id.tvMessageContent)
    TextView tvMessageContent;

    public static void startMessageDetailActivity(Context context, String id) {
        Intent intent = new Intent(context, MessageDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void initViews() {
        setTitleText("消息详情");
    }

    @Override
    protected void initDatas() {
        HttpsUtil.getInstance(this).getMessageDetail(getIntent().getStringExtra("id"), object -> {
            MessageListBean bean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), MessageListBean.class);
            switch (bean.getMessage_type()) {
                case "1"://认证消息
                    tvMessageTitle.setText("认证消息");
                    break;
                case "2"://结算消息
                    tvMessageTitle.setText("结算消息");
                    break;
                case "3"://订单消息
                    tvMessageTitle.setText("订单消息");
                    break;
                case "4"://系统消息
                    tvMessageTitle.setText("系统消息");
                    break;
            }
            tvMessageDate.setText(String.format("%s", TimeUtils.millis2String(Long.parseLong(bean.getCreate_time()) * 1000, "yyyy-MM-dd HH:mm")));
            tvMessageContent.setText(bean.getContent());
        });
    }

    @Override
    protected void initListener() {

    }
}