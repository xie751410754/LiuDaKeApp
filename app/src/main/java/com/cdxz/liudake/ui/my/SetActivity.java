package com.cdxz.liudake.ui.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.LoginBean;
import com.cdxz.liudake.bean.WeiXinToken;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.login.LoginActivity;
import com.cdxz.liudake.util.ACache;
import com.cdxz.liudake.util.GlideCacheUtil;
import com.cdxz.liudake.util.ParseUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SetActivity extends BaseActivity {

    @BindView(R.id.tvCacheSize)
    TextView tvCacheSize;
    private IWXAPI wxAPI;
    private OkHttpClient okHttpClient;


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
        BusUtils.register(this);

        wxAPI = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID,true);
        wxAPI.registerApp(Constants.WX_APP_ID);
        okHttpClient = new OkHttpClient();

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

        findViewById(R.id.bindWX).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });
    }





    /**
     * 微信登陆(三个步骤)
     * 1.微信授权登陆
     * 2.根据授权登陆code 获取该用户token
     * 3.根据token获取用户资料
     */
    public void login(){
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = String.valueOf(System.currentTimeMillis());
        wxAPI.sendReq(req);
    }

    @BusUtils.Bus(tag = "xzl")
    public void onVXResponse(String code) {
        getAccessToken(code);

    }

    public void getAccessToken(String code){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid="+Constants.WX_APP_ID+"&secret="+Constants.WX_SECRET+
                "&code="+code+"&grant_type=authorization_code";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showShort("数据获取失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();
                WeiXinToken baseBean = ParseUtils.parseJsonObject(responseBody, WeiXinToken.class);

                if(baseBean.getErrcode()==0){//请求成功
                    getWeiXinUserInfo(baseBean);
                }else{//请求失败
                    ToastUtils.showShort("授权失败");
                }


            }
        });

    }

    public void getWeiXinUserInfo(WeiXinToken weiXinToken){


        HttpsUtil.getInstance(this).loginByWechat(weiXinToken.getOpenid(),weiXinToken.getUnionid(), object -> {

            if (!SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
                SPUtils.getInstance().put(Constants.IS_LOGIN, true);
                LoginBean loginBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), LoginBean.class);
                ACache.get(this).put(Constants.CACHE_LOGIN, loginBean);
            }
//            toMain();
            finish();


        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
    }
}