package com.cdxz.liudake.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.JsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.pop.PopUploadAvatar;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.PictureUtil;
import com.cdxz.liudake.util.UserInfoUtil;
import com.lxj.xpopup.XPopup;
import com.tamsiree.rxpay.wechat.share.WechatShareModel;
import com.tamsiree.rxpay.wechat.share.WechatShareTools;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.cdxz.liudake.base.Constants.WX_APP_ID;

public class WebActivity extends BaseActivity {

    public static final int USER_XIE_YI = 1;
    public static final int PRIVATE_XIE_YI = 2;
    public static final int ABOUT = 3;
    public static final int WALLET_STRATEGY = 4;
    public static final int SHOP_START = 5;
    public static final int JDSHOP_START = 6;
    public static final int INTERRITY_XIE_YI = 7;
    public static final int FOODSAFE_XIE_YI = 8;

    private ValueCallback<Uri[]> uploadFiles;
    private static final int CHOOSE_REQUEST_CODE = 0x9001;
    private ValueCallback<Uri> uploadFile;//定义接受返回值
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.titleBar)
    ViewGroup titleBar;

    private OkHttpClient okHttpClient;
    WechatShareModel mWechatShareModel;

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

        WechatShareTools.init(this, WX_APP_ID);//初始化

        okHttpClient = new OkHttpClient();

        webView.getSettings().setJavaScriptEnabled(true);
        // 设置webview加载的页面的模式,缩放至屏幕的大小
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "injectedObject");
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                super.onPermissionRequest(request);
            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
                uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android  > 4.1.1
//    @Override
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                uploadFile = uploadFile;
                openFileChooseProcess();
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                uploadFiles = filePathCallback;
                openFileChooseProcess();
                return true;

            }

            private void openFileChooseProcess() {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "Choose"), CHOOSE_REQUEST_CODE);
            }
        });
        webView.getSettings().setDatabaseEnabled(true);

        // 点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        webView.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (i == KeyEvent.KEYCODE_BACK && webView.canGoBack()) { // 表示按返回键
                        // 时的操作
                        webView.goBack();
                        // webview.goForward();//前进
                        return true; // 已处理
                    }
                }
                return false;

            }
        });


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    /**安装了支付宝或者微信客户端，打开对应支付**/
                    if (url.contains("platformapi/startApp") || url.contains("weixin://wap/pay?")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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

            case INTERRITY_XIE_YI:
                setTitle("诚信承诺书");
                break;
            case FOODSAFE_XIE_YI:
                setTitle("食品安全承诺书");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_REQUEST_CODE:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != Activity.RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    if (null != uploadFiles) {
                        Uri result = data == null || resultCode != Activity.RESULT_OK ? null
                                : data.getData();
                        uploadFiles.onReceiveValue(new Uri[]{result});
                        uploadFiles = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }
            if (null != uploadFiles) {
                uploadFiles.onReceiveValue(null);
                uploadFiles = null;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void startFunction(String html) {


            LogUtils.e("html", html);

            if (!TextUtils.isEmpty(html)) {
                String goodsName = JsonUtils.getString(html, "title", "溜达客");
                String goodsLogo = JsonUtils.getString(html, "url", "溜达客");


                String url = Constants.BASE_HTTPS_URL + "Home/index/regist/invitecode/" + UserInfoUtil.getUid();//网页链接

                String description = Constants.BASE_HTTPS_URL + "Home/index/regist/invitecode/" + UserInfoUtil.getUid();//描述


                Request request = new Request.Builder()
                        .url(goodsLogo)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        ToastUtils.showShort("图片获取失败");


                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.body() == null) {
                            return;
                        }

                        byte[] byte_image = response.body().bytes();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(byte_image, 0, byte_image.length);
                        byte[] bitmapByte = ImageUtils.compressByQuality(bitmap, 9549L);

                        mWechatShareModel = new WechatShareModel(url, goodsName, description, bitmapByte);

                        //Friend 分享微信好友,Zone 分享微信朋友圈,Favorites 分享微信收藏
                        WechatShareTools.shareURL(mWechatShareModel, WechatShareTools.SharePlace.Friend);//分享操作

                    }
                });


            }


        }


    }

}