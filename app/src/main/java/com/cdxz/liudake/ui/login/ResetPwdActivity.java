package com.cdxz.liudake.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.ui.base.BaseActivity;

import butterknife.BindView;

public class ResetPwdActivity extends BaseActivity {

    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.etCode)
    EditText etCode;

    @BindView(R.id.tvGetCode)
    TextView tvGetCode;

    @BindView(R.id.etPwd)
    EditText etPwd;

    @BindView(R.id.etPwd2)
    EditText etPwd2;

    @BindView(R.id.btn_switchPwd)
    ImageView btn_switchPwd;

    @BindView(R.id.btn_switchPwd2)
    ImageView btn_switchPwd2;


    CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            tvGetCode.setText(String.valueOf((int) l / 1000));
            tvGetCode.setClickable(false);
        }

        @Override
        public void onFinish() {
            tvGetCode.setText("重新获取");
            tvGetCode.setClickable(true);
        }
    };

    public static void startResetPwdActivity(Context context) {
        Intent intent = new Intent(context, ResetPwdActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_reset_pwd_new;
    }

    @Override
    protected void initViews() {
        setTitleText("重置密码");
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListener() {

        findViewById(R.id.btn_switchPwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchPwd(btn_switchPwd,etPwd);
            }
        });
        findViewById(R.id.btn_switchPwd2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchPwd(btn_switchPwd2,etPwd2);
            }
        });

        tvGetCode.setOnClickListener(v -> {
            String phone = etPhone.getText().toString();
            if (StringUtils.isEmpty(phone)) {
                ToastUtils.showShort("请输入手机号");
                return;
            }
//            if (!RegexUtils.isMobileExact(phone)) {
//                ToastUtils.showShort("请输入正确的手机号");
//                return;
//            }
            HttpsUtil.getInstance(this).getSmsCode(phone, 1, object -> {
                downTimer.start();
            });
        });
        findViewById(R.id.tvSubmitReset).setOnClickListener(v -> {
            String phone = etPhone.getText().toString();
            String code = etCode.getText().toString();
            String pwd = etPwd.getText().toString();
            String pwd2 = etPwd2.getText().toString();

            if (StringUtils.isEmpty(phone)) {
                ToastUtils.showShort("请输入手机号");
                return;
            }
//            if (!RegexUtils.isMobileExact(phone)) {
//                ToastUtils.showShort("请输入正确的手机号");
//                return;
//            }
            if (StringUtils.isEmpty(code)) {
                ToastUtils.showShort("请输入验证码");
                return;
            }
            if (StringUtils.isEmpty(pwd)) {
                ToastUtils.showShort("请输入密码");
                return;
            }
            if (StringUtils.isEmpty(pwd2)) {
                ToastUtils.showShort("请再次输入密码");
                return;
            }
            if (!pwd.equals(pwd2)) {
                ToastUtils.showShort("两次密码输入不一致，请重新输入");
                return;
            }
            HttpsUtil.getInstance(this).resetPassword(phone, pwd, code, object -> {
                ToastUtils.showShort("密码重置成功");
                finish();
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downTimer.cancel();
        downTimer = null;
    }

    public static void switchPwd(ImageView ivPwd, EditText edtPwd) {
        ivPwd.setSelected(!ivPwd.isSelected());
        if (ivPwd.isSelected()) {
            edtPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            edtPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        edtPwd.setSelection(edtPwd.getText().length());
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