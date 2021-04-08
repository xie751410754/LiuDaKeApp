package com.cdxz.liudake.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.LiudakeApplication;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.GetSetBean;
import com.cdxz.liudake.bean.LoginBean;
import com.cdxz.liudake.databinding.ActivityLoginBinding;
import com.cdxz.liudake.databinding.ActivityLoginNewBinding;
import com.cdxz.liudake.ui.WebActivity;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.base.BaseTitleActivity;
import com.cdxz.liudake.ui.main.MainActivity;
import com.cdxz.liudake.ui.my.AboutUsActivity;
import com.cdxz.liudake.util.ACache;
import com.cdxz.liudake.util.ParseUtils;

import java.util.List;

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


    boolean check = true;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login_new;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
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
                HttpsUtil.getInstance(this).loginByPwd(phone, pwd, object -> {
                    SPUtils.getInstance().put(Constants.IS_LOGIN, true);
                    LoginBean loginBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), LoginBean.class);
                    ACache.get(this).put(Constants.CACHE_LOGIN, loginBean);
                    MainActivity.startMainActivity(this);
                    finish();
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
                                WebActivity.startWebActivity(LoginActivity.this, WebActivity.ABOUT, setBean.getValue());
                            }
                        }
                        break;
                    case 1:
                        for (GetSetBean setBean : getSetBeanList) {
                            if ("user_xieyi".equals(setBean.getKey())) {
                                WebActivity.startWebActivity(LoginActivity.this, WebActivity.USER_XIE_YI, setBean.getValue());
                            }
                        }
                        break;
                    case 2:
                        for (GetSetBean setBean : getSetBeanList) {
                            if ("yishi_xieyi".equals(setBean.getKey())) {
                                WebActivity.startWebActivity(LoginActivity.this, WebActivity.PRIVATE_XIE_YI, setBean.getValue());
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
    }


    @Override
    public Resources getResources() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 750);
        } else {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 750);
        }
    }
}