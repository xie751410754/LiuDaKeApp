package com.cdxz.liudake.ui.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.LiudakeApplication;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.GetSetBean;
import com.cdxz.liudake.bean.JDSecondCategoryDto;
import com.cdxz.liudake.bean.LoginBean;
import com.cdxz.liudake.bean.ShopCarListBean;
import com.cdxz.liudake.bean.WeiXin;
import com.cdxz.liudake.bean.WeiXinToken;
import com.cdxz.liudake.databinding.ActivityLoginBinding;
import com.cdxz.liudake.databinding.ActivityLoginNewBinding;
import com.cdxz.liudake.ui.WebActivity;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.base.BaseTitleActivity;
import com.cdxz.liudake.ui.main.MainActivity;
import com.cdxz.liudake.ui.my.AboutUsActivity;
import com.cdxz.liudake.util.ACache;
import com.cdxz.liudake.util.ParseUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseTitleActivity<ActivityLoginNewBinding> {
    CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            binding.sendAgainBtn.setText(String.valueOf((int) l / 1000));
        }

        @Override
        public void onFinish() {
            binding.sendAgainBtn.setText("发送");
        }
    };

    public static void startLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private IWXAPI wxAPI;
    private OkHttpClient okHttpClient;

    boolean check = false;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login_new;
    }




    @Override
    public void initViewObservable() {
        super.initViewObservable();
        BusUtils.register(this);
        wxAPI = WXAPIFactory.createWXAPI(this,Constants.WX_APP_ID,true);
        wxAPI.registerApp(Constants.WX_APP_ID);
        okHttpClient = new OkHttpClient();

        if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
            toMain();
            finish();
        }

        binding.ivCheckbox.setSelected(check);

        binding.ivCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check =! check;


                binding.ivCheckbox.setSelected(check);
            }
        });
        binding.forgetPwd.setOnClickListener(v -> {
            ResetPwdActivity.startResetPwdActivity(this);
        });
        binding.registerAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RegisterActivity.class);
//                login();
            }


        });

        binding.wechatLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });


        binding.tvZhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RegisterActivity.class);
            }
        });
//        binding.loginType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (binding.loginType.getText().toString().equals("手机号登录")) {
//                    binding.loginType.setText("密码登录");
//                    binding.sendAgainBtn.setVisibility(View.VISIBLE);
//                    binding.passwordEdit.setHint("输入验证码");
//                    binding.tvPwdTips.setText("验证码");
//                } else {
//                    binding.loginType.setText("手机号登录");
//                    binding.sendAgainBtn.setVisibility(View.GONE);
//                    binding.passwordEdit.setHint("输入密码");
//                    binding.tvPwdTips.setText("密码");
//                }
//            }
//        });
        binding.sendAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.sendAgainBtn.getText().toString().equals("发送")) {
                    downTimer.start();
                }
            }
        });
        binding.loginBtn.setOnClickListener(v -> {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            if (!check){
                ToastUtils.showShort("隐私协议未同意，请勾选");
                return;
            }
            String phone = binding.phoneNumerEdit.getText().toString();
            if (StringUtils.isEmpty(phone)) {
                ToastUtils.showShort("请输入手机号");
                return;
            }
//            if (!RegexUtils.isMobileExact(phone)) {
//                ToastUtils.showShort("请输入正确的手机号");
//                return;
//            }
            if (binding.forgetPwd.getText().toString().equals("忘记密码")) {
                String pwd = binding.passwordEdit.getText().toString();
                if (StringUtils.isEmpty(pwd)) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                String registrationID = JPushInterface.getRegistrationID(LiudakeApplication.getContext());
                LogUtils.e("registrationID ="+registrationID);

                HttpsUtil.getInstance(this).loginByPwd(phone, pwd,registrationID, object -> {
                    SPUtils.getInstance().put(Constants.IS_LOGIN, true);
                    LoginBean loginBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), LoginBean.class);
                    ACache.get(this).put(Constants.CACHE_LOGIN, loginBean);

                    if (loginBean.getIs_connect_wechat()==0){
                        showDialog();
                    }else {
                        MainActivity.startMainActivity(this);
                        finish();
                    }
                });
            } else {

            }
        });
        binding.tvXy.setOnClickListener(v -> {
            get_set(1);
        });
        binding.tvYs.setOnClickListener(v -> {
            get_set(2);
        });
    }

    private void showDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("系统发现您暂未绑定微信，点击绑定按钮绑定微信交易更方便哦")
                .setPositiveButton("绑定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        login();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        toMain();
                        finish();
                        dialogInterface.dismiss();

                    }
                })
                .create();

        alertDialog.show();
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



        HttpsUtil.getInstance(this).loginByWechat(weiXinToken.getOpenid(),weiXinToken.getUnionid(),object -> {

            if (!SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
                SPUtils.getInstance().put(Constants.IS_LOGIN, true);
                LoginBean loginBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), LoginBean.class);
                ACache.get(this).put(Constants.CACHE_LOGIN, loginBean);
            }
            toMain();
            finish();


        });


    }

    private void get_set(int i) {
        HttpsUtil.getInstance(this).get_set(object -> {
            if ("-1".equals(object)) {
                switch (i) {
                    case 0:
                        ToastUtils.showShort("关于溜达客获取失败");
                        break;
                    case 1:
                        ToastUtils.showShort("用户协议获取失败");
                        break;
                    case 2:
                        ToastUtils.showShort("隐私协议获取失败");
                        break;
                }
            } else {
                List<GetSetBean> getSetBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), GetSetBean.class);
                switch (i) {
                    case 0:
                        for (GetSetBean setBean : getSetBeanList) {
                            if ("about".equals(setBean.getKey())) {
                                WebActivity.startWebActivity(LoginActivity.this, "关于溜达客", setBean.getValue());
                            }
                        }
                        break;
                    case 1:
                        for (GetSetBean setBean : getSetBeanList) {
                            if ("user_xieyi".equals(setBean.getKey())) {
                                WebActivity.startWebActivity(LoginActivity.this, "用户协议", setBean.getValue());
                            }
                        }
                        break;
                    case 2:
                        for (GetSetBean setBean : getSetBeanList) {
                            if ("yishi_xieyi".equals(setBean.getKey())) {
                                WebActivity.startWebActivity(LoginActivity.this, "隐私协议", setBean.getValue());
                            }
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downTimer.cancel();
        downTimer = null;
        BusUtils.unregister(this);
    }


    @Override
    public Resources getResources() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 750);
        } else {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 750);
        }
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