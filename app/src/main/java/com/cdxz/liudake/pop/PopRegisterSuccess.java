package com.cdxz.liudake.pop;

import android.content.Context;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.lxj.xpopup.core.CenterPopupView;

public class PopRegisterSuccess extends CenterPopupView {

    private OnPwdListener onPwdListener;

    public PopRegisterSuccess(@NonNull Context context, OnPwdListener onPwdListener) {
        super(context);
        this.onPwdListener = onPwdListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_register_success;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.btn_return).setOnClickListener(v -> {
            dismiss();
        });
        findViewById(R.id.tvSubmitReset).setOnClickListener(v -> {

            onPwdListener.onSubmit();
            dismiss();
        });
    }

    public interface OnPwdListener {
        void onSubmit();
    }
}
