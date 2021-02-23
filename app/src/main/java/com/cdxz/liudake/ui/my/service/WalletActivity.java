package com.cdxz.liudake.ui.my.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.bean.MyWalletBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.my.InviteCodeActivity;
import com.cdxz.liudake.util.ParseUtils;

import butterknife.BindView;

public class WalletActivity extends BaseActivity {

    @BindView(R.id.tvRedmiScore)
    TextView tvRedmiScore;

    @BindView(R.id.tvUseScore)
    TextView tvUseScore;

    @BindView(R.id.tvLingquScore)
    TextView tvLingquScore;

    public static void startWalletActivity(Context context) {
        Intent intent = new Intent(context, WalletActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initViews() {
        setTitleText("我的钱包");
    }

    @Override
    protected void initDatas() {
        HttpsUtil.getInstance(this).myWallet(object -> {
            MyWalletBean walletBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), MyWalletBean.class);
            tvRedmiScore.setText(walletBean.getBalance());
            tvUseScore.setText(walletBean.getIntegral());
            tvLingquScore.setText(walletBean.getWait_integral());
            findViewById(R.id.tvTiXian).setOnClickListener(v -> {
                WithdrawalActivity.startWithdrawalActivity(this, walletBean.getBalance());
            });
        });
    }

    @Override
    protected void initListener() {
        findViewById(R.id.allLayout).setOnClickListener(v -> {
            RedmiBillActivity.startRedmiBillActivity(this);
        });
        findViewById(R.id.useLayout).setOnClickListener(v -> {
            ScoreBillActivity.startScoreBillActivity(this);
        });
        findViewById(R.id.tvTuiguang).setOnClickListener(v -> {
            ToPromoteActivity.startToPromoteActivity(this);
        });
        findViewById(R.id.shareLayout).setOnClickListener(v -> {
            InviteCodeActivity.startInviteCodeActivity(this);
        });
    }
}