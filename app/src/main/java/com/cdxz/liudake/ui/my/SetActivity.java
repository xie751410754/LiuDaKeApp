package com.cdxz.liudake.ui.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.login.LoginActivity;
import com.cdxz.liudake.util.ACache;
import com.cdxz.liudake.util.GlideCacheUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import butterknife.BindView;

public class SetActivity extends BaseActivity {

    @BindView(R.id.tvCacheSize)
    TextView tvCacheSize;

    public static void startSetActivity(Context context) {
        Intent intent = new Intent(context, SetActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initViews() {
        setTitleText("设置");
    }

    @Override
    protected void initDatas() {
        tvCacheSize.setText(GlideCacheUtil.getInstance().getCacheSize(this));
    }

    @Override
    protected void initListener() {
        tvCacheSize.setOnClickListener(v -> {
            new XPopup.Builder(this)
                    .asConfirm("清除", "是否清除缓存？", () -> {
                        GlideCacheUtil.getInstance().clearImageAllCache(this);
                        ToastUtils.showShort("清除成功");
                        tvCacheSize.setText(GlideCacheUtil.getInstance().getCacheSize(this));
                    }).show();
        });
        findViewById(R.id.tvAboutUs).setOnClickListener(v -> {
            AboutUsActivity.startAboutUsActivity(this);
        });
        findViewById(R.id.tvLogout).setOnClickListener(v -> {
            new XPopup.Builder(this)
                    .asConfirm("退出登录", "是否退出登录？", () -> {
                        ACache.get(SetActivity.this).clear();
                        SPUtils.getInstance().clear();
                        ActivityUtils.finishAllActivities();
                        LoginActivity.startLoginActivity(SetActivity.this);
                    }).show();
        });
        findViewById(R.id.tvSetPayPwd).setOnClickListener(v -> {
            SetPayPwdActivity.startSetPayPwdActivity(this);
        });
    }
}