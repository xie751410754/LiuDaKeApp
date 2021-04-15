package com.cdxz.liudake.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.LogUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.UserInfoUtil;

import butterknife.BindView;

public class WebActivity extends BaseActivity {

    public static final int USER_XIE_YI = 1;
    public static final int PRIVATE_XIE_YI = 2;
    public static final int ABOUT = 3;
    public static final int WALLET_STRATEGY = 4;
    public static final int SHOP_START = 5;

    @BindView(R.id.webView)
    WebView webView;

    public static void startWebActivity(Context context, int type, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initViews() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    protected void initDatas() {
        switch (getIntent().getIntExtra("type", USER_XIE_YI)) {
            case USER_XIE_YI:
                setTitleText("用户协议");
                break;
            case PRIVATE_XIE_YI:
                setTitleText("隐私协议");
                break;
            case ABOUT:
                setTitleText("关于溜达客");
                break;
            case WALLET_STRATEGY:
                setTitleText("钱包攻略");
                break;
            case SHOP_START:
                setTitleText("开店指南");
                break;
            default:
                setTitleText("溜达客");
                break;
        }
        String url = getIntent().getStringExtra("url");
        assert url != null;
        if (url.startsWith("http://") || url.startsWith("https://")) {
            webView.loadUrl(url);
        } else {
            webView.loadDataWithBaseURL(null, url, "text/html", "UTF-8", null);
        }
    }

    @Override
    protected void initListener() {

    }
}