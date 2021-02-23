package com.cdxz.liudake.ui.my.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.FAQBean;
import com.cdxz.liudake.ui.base.BaseActivity;

import butterknife.BindView;

public class FAQDetailActivity extends BaseActivity {

    @BindView(R.id.webViewFAQ)
    WebView webViewFAQ;

    public static void startFAQDetailActivity(Context context, FAQBean bean) {
        Intent intent = new Intent(context, FAQDetailActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_faq_detail;
    }

    @Override
    protected void initViews() {
        webViewFAQ.getSettings().setJavaScriptEnabled(true);
        webViewFAQ.setWebChromeClient(new WebChromeClient());
        webViewFAQ.setWebViewClient(new WebViewClient());
    }

    @Override
    protected void initDatas() {
        FAQBean bean = (FAQBean) getIntent().getSerializableExtra("bean");
        setTitleText(bean.getTitle());
        if (bean.getContent().startsWith("http://") || bean.getContent().startsWith("https://")) {
            webViewFAQ.loadUrl(bean.getContent());
        } else {
            webViewFAQ.loadDataWithBaseURL(null, bean.getContent(), "text/html", "UTF-8", null);
        }
    }

    @Override
    protected void initListener() {

    }
}