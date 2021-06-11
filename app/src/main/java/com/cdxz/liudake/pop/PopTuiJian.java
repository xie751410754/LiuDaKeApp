package com.cdxz.liudake.pop;

import android.content.Context;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.lxj.xpopup.core.CenterPopupView;

public class PopTuiJian extends CenterPopupView {

    EditText etPwd;
    private OnPwdListener onPwdListener;

    public PopTuiJian(@NonNull Context context, OnPwdListener onPwdListener) {
        super(context);
        this.onPwdListener = onPwdListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_tuijian;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        etPwd = findViewById(R.id.etPwd);
        findViewById(R.id.tvCancel).setOnClickListener(v -> {
            dismiss();
        });
        findViewById(R.id.tvSubmit).setOnClickListener(v -> {
            String text = etPwd.getText().toString();
            if (StringUtils.isEmpty(text)) {
                ToastUtils.showShort("请输入推荐码");
                return;
            }
            onPwdListener.onSubmit(text);
            dismiss();
        });
    }

    public interface OnPwdListener {
        void onSubmit(String text);
    }
}
