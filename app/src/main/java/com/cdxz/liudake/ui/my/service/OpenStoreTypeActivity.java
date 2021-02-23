package com.cdxz.liudake.ui.my.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cdxz.liudake.R;
import com.cdxz.liudake.ui.base.BaseActivity;

import butterknife.BindView;

public class OpenStoreTypeActivity extends BaseActivity {

    @BindView(R.id.tvGeTiGong)
    TextView tvGeTiGong;

    @BindView(R.id.tvXiaoWei)
    TextView tvXiaoWei;

    @BindView(R.id.tvQiye)
    TextView tvQiye;

    private int type = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_open_store_type;
    }

    @Override
    protected void initViews() {
        setTitleText("入驻类型选择");
        type = 1;
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListener() {
        tvGeTiGong.setOnClickListener(v -> {
            type = 1;
            tvGeTiGong.setTextColor(ContextCompat.getColor(this, R.color.white));
            tvGeTiGong.setBackgroundResource(R.drawable.btn_r4);

            tvXiaoWei.setTextColor(ContextCompat.getColor(this, R.color.color_FF5033));
            tvXiaoWei.setBackgroundResource(R.drawable.shape_order_button_bg);

            tvQiye.setTextColor(ContextCompat.getColor(this, R.color.color_FF5033));
            tvQiye.setBackgroundResource(R.drawable.shape_order_button_bg);
        });
        tvXiaoWei.setOnClickListener(v -> {
            type = 2;
            tvXiaoWei.setTextColor(ContextCompat.getColor(this, R.color.white));
            tvXiaoWei.setBackgroundResource(R.drawable.btn_r4);

            tvGeTiGong.setTextColor(ContextCompat.getColor(this, R.color.color_FF5033));
            tvGeTiGong.setBackgroundResource(R.drawable.shape_order_button_bg);

            tvQiye.setTextColor(ContextCompat.getColor(this, R.color.color_FF5033));
            tvQiye.setBackgroundResource(R.drawable.shape_order_button_bg);
        });
        tvQiye.setOnClickListener(v -> {
            type = 3;
            tvQiye.setTextColor(ContextCompat.getColor(this, R.color.white));
            tvQiye.setBackgroundResource(R.drawable.btn_r4);

            tvGeTiGong.setTextColor(ContextCompat.getColor(this, R.color.color_FF5033));
            tvGeTiGong.setBackgroundResource(R.drawable.shape_order_button_bg);

            tvXiaoWei.setTextColor(ContextCompat.getColor(this, R.color.color_FF5033));
            tvXiaoWei.setBackgroundResource(R.drawable.shape_order_button_bg);
        });
        findViewById(R.id.tvSubmitType).setOnClickListener(v -> {
            switch (type) {
                case 1:
                    startActivity(new Intent(this, OpenStoreType1Activity.class));
                    break;
                case 2:
                    startActivity(new Intent(this, OpenStoreType2Activity.class));
                    break;
                case 3:
                    startActivity(new Intent(this, OpenStoreType3Activity.class));
                    break;
            }
        });
    }
}