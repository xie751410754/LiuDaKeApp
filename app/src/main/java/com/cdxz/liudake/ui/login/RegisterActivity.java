package com.cdxz.liudake.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsCallback;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.GetSetBean;
import com.cdxz.liudake.databinding.ActivityRegisterBinding;
import com.cdxz.liudake.databinding.ActivityRegisterNewBinding;
import com.cdxz.liudake.pop.PopPayPwd;
import com.cdxz.liudake.pop.PopRegisterSuccess;
import com.cdxz.liudake.ui.WebActivity;
import com.cdxz.liudake.ui.base.BaseTitleActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.lxj.xpopup.XPopup;

import java.util.List;

public class RegisterActivity extends BaseTitleActivity<ActivityRegisterNewBinding> {

    CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            binding.sendAgainBtn.setText(String.valueOf((int) l / 1000));
            binding.sendAgainBtn.setClickable(false);
        }

        @Override
        public void onFinish() {
            binding.sendAgainBtn.setText("发送");
            binding.sendAgainBtn.setClickable(true);
        }
    };

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_register_new;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
//        initToolbar(binding.toolbar);


        binding.tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.sendAgainBtn.setOnClickListener(v -> {
            String phone = binding.phoneNumerEdit.getText().toString();
            if (StringUtils.isEmpty(phone)) {
                ToastUtils.showShort("请输入手机号");
                return;
            }
//            if (!RegexUtils.isMobileExact(phone)) {
//                ToastUtils.showShort("请输入正确的手机号");
//                return;
//            }
            HttpsUtil.getInstance(this).getSmsCode(phone, 0, object -> {
                downTimer.start();
            });
        });
        binding.nextStepBtn.setOnClickListener(v -> {
            register();
        });
        binding.tvUserXY.setOnClickListener(v -> {
            get_set(1);
        });
        binding.tvYs.setOnClickListener(v -> {
            get_set(2);
        });
    }

    private void register() {
        String name = binding.tvName.getText().toString();
        String phone = binding.phoneNumerEdit.getText().toString();
        String pwd = binding.passwordEdit.getText().toString();
        String pwd2 = binding.passwordEditSubmit.getText().toString();
        String invitationCode = binding.etInvitationCode.getText().toString();
        String code = binding.authCodeEdit.getText().toString();

        if (StringUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入姓名");
            return;
        }

        if (StringUtils.isEmpty(phone)) {
            ToastUtils.showShort("请输入手机号");
            return;
        }
//        if (!RegexUtils.isMobileExact(phone)) {
//            ToastUtils.showShort("请输入正确的手机号");
//            return;
//        }
        if (StringUtils.isEmpty(code)) {
            ToastUtils.showShort("请输入验证码");
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            ToastUtils.showShort("请输入密码");
            return;
        }
        if (StringUtils.isEmpty(pwd2)) {
            ToastUtils.showShort("请输入确认密码");
            return;
        }
        if (!pwd.equals(pwd2)) {
            ToastUtils.showShort("两次密码输入不一致");
            return;
        }
        HttpsUtil.getInstance(this).register(name ,phone, pwd, code, invitationCode, object -> {

            new XPopup.Builder(this).asCustom(new PopRegisterSuccess(this, new PopRegisterSuccess.OnPwdListener() {
                @Override
                public void onSubmit() {
                    finish();
                }
            })).show();

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
                                WebActivity.startWebActivity(RegisterActivity.this, "关于溜达客", setBean.getValue());
                            }
                        }
                        break;
                    case 1:
                        for (GetSetBean setBean : getSetBeanList) {
                            if ("user_xieyi".equals(setBean.getKey())) {
                                WebActivity.startWebActivity(RegisterActivity.this, "用户协议", setBean.getValue());
                            }
                        }
                        break;
                    case 2:
                        for (GetSetBean setBean : getSetBeanList) {
                            if ("yishi_xieyi".equals(setBean.getKey())) {
                                WebActivity.startWebActivity(RegisterActivity.this, "隐私协议", setBean.getValue());
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
