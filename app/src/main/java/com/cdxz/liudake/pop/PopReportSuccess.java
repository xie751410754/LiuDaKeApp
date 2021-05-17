package com.cdxz.liudake.pop;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cdxz.liudake.R;
import com.lxj.xpopup.core.CenterPopupView;

public class PopReportSuccess extends CenterPopupView {

    TextView know;
    private KnowListener onPwdListener;

    public PopReportSuccess(@NonNull Context context, KnowListener onPwdListener) {
        super(context);
        this.onPwdListener = onPwdListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_report_success;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        findViewById(R.id.tv_know).setOnClickListener(v -> {
            onPwdListener.onSubmit();
            dismiss();
        });
    }

    public interface KnowListener {
        void onSubmit();
    }
}
