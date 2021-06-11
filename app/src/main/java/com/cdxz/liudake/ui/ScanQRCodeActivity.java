package com.cdxz.liudake.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;

import com.blankj.utilcode.util.LogUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.ui.base.BaseActivity;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanQRCodeActivity extends BaseActivity implements QRCodeView.Delegate {
    public static final int TYPE_PAY = 1;
    public static final int TYPE_STORE_HOME = 2;
    private int type;

    @BindView(R.id.zxingView)
    ZXingView zxingView;

    public static void startScanQRCodeActivity(Context context, int type) {
        Intent intent = new Intent(context, ScanQRCodeActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_scan_qrcode;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initDatas() {
        type = getIntent().getIntExtra("type", 0);
        if (zxingView != null) {
            zxingView.setDelegate(this);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (zxingView != null) {
            zxingView.startCamera();
//        zxingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
            zxingView.startSpot(); // 开始识别
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (zxingView != null) {
            zxingView.stopCamera();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (zxingView != null) {
            zxingView.onDestroy();
        }
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        LogUtils.e("result:" + result);
        vibrate();
        if (result.startsWith("http://www.liudashop.com/index.php/home/index/shop_SMQrcode_zhifu")) {
            String[] s = result.split("shop_id=");
            StorePayActivity.startStorePayActivity(this, s[1]);
        } else {
            WebActivity.startWebActivity(this, "溜达客", result);
//            new XPopup.Builder(this)
//                    .asConfirm("扫描结果", result, () -> {
//                        if (zxingView != null) {
//                            zxingView.startSpot();
//                        }
//                    }, () -> finish()).show();
        }
        finish();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        LogUtils.e("打开相机出错");
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}