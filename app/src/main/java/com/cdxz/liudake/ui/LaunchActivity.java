package com.cdxz.liudake.ui;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.login.LoginActivity;
import com.cdxz.liudake.ui.main.MainActivity;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

public class LaunchActivity extends BaseActivity {

    private Handler handler = new Handler(msg -> {
        switch (msg.what) {
            case 1:
                if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
                    toMain();
                } else {
                    toLogin();
                }
                finish();
                break;
        }
        return false;
    });

    @Override
    protected int getContentViewId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initViews() {
        hideSystemUi();
    }

    @Override
    protected void initDatas() {
        PermissionUtils.permission(
                PermissionConstants.LOCATION,
                PermissionConstants.STORAGE,
                PermissionConstants.PHONE,
                PermissionConstants.CAMERA
        ).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                handler.sendEmptyMessageDelayed(1, 500);
            }

            @Override
            public void onDenied() {
                ToastUtils.showShort("获取权限失败");
                AppUtils.exitApp();
            }
        }).request();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 跳转到登录页面
     */
    private void toLogin() {
        LoginActivity.startLoginActivity(this);
    }

    /**
     * 跳转到主页面
     */
    private void toMain() {
        MainActivity.startMainActivity(this);
    }
}