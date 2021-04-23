package com.cdxz.liudake.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
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
    public static final int JDSHOP_START = 6;

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.titleBar)
    ViewGroup titleBar;

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
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    /**安装了支付宝或者微信客户端，打开对应支付**/
                    if (url.contains("platformapi/startApp") || url.contains("weixin://wap/pay?")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    /**未安装微信客户端，屏蔽无法加载微信内部链接的错误页面提示，通过webview.loadUrl的方式加载自定义提示页面，
                     * 提示用户先下载并安装微信客户端，由于支付宝支持H5支付，不用另行处理**/
                    if (url.contains("weixin://wap/pay?")) {
                        /**加载自定义提示页面**/
                        ToastUtils.showShort("请先安装微信!");
                    }
                    return false;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

        });
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
            case JDSHOP_START:
                setTitleText("京东商品");
//                titleBar.setVisibility(View.GONE);
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