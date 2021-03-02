package com.cdxz.liudake.ui.my;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.ui.base.BaseActivity;

import butterknife.BindView;

public class SetPayPwdActivity extends BaseActivity {

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

    public static void startSetPayPwdActivity(Context context) {
        Intent intent = new Intent(context, SetPayPwdActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    protected void initViews() {
        setTitleText("重置支付密码");
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListener() {
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
            HttpsUtil.getInstance(this).getSmsCode(phone, 7, object -> {
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
            String mi = EncryptUtils.encryptMD5ToString(EncryptUtils.encryptMD5ToString(pwd));
//            LogUtils.e("mi = " + mi);
            HttpsUtil.getInstance(this).setPayPwd(phone, code, mi, object -> {
                ToastUtils.showShort("支付密码设置成功");
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
}