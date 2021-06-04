package com.cdxz.liudake.ui.my;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.BusUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.ui.base.BaseActivity;

import butterknife.BindView;

public class TransferAccountSuccessfulActivity extends BaseActivity {

    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_order)
    TextView tv_order;




    String balance,phone,time,order;

    public static void startTransferAccountSuccessfulActivity(Context context,String balance,String phone,String time,String order) {
        Intent intent = new Intent(context, TransferAccountSuccessfulActivity.class);
        intent.putExtra("balance",balance);
        intent.putExtra("phone",phone);
        intent.putExtra("time",time);
        intent.putExtra("order" ,order);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_transfer_account_successful;
    }

    @Override
    protected void initViews() {

        balance = getIntent().getStringExtra("balance");
        phone = getIntent().getStringExtra("phone");
        time = getIntent().getStringExtra("time");
        order = getIntent().getStringExtra("order");
        tv_balance.setText("￥"+balance);
        tv_phone.setText("收款方："+phone);
        tv_time.setText("转账时间："+time);
        tv_order.setText("订单编号："+order);

    }



    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListener() {

        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusUtils.post("transfer");
                finish();
            }
        });

    }



    @Override
    public Resources getResources() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 750);
        } else {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 750);
        }
    }

}