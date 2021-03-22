package com.cdxz.liudake.pop;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cdxz.liudake.R;
import com.lxj.xpopup.core.BottomPopupView;

public class PopSelector extends BottomPopupView {


    String tel;
    TextView tv_tel;

    TextView cancel;
    private OnSelectListener onSelectListener;

    public PopSelector(@NonNull Context context, String number,OnSelectListener onSelectListener) {
        super(context);
        this.onSelectListener = onSelectListener;
        this.tel = number;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_phone_number;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tv_tel = findViewById(R.id.tv_tel);
        tv_tel.setText(tel);
        cancel = findViewById(R.id.tv_cancel);

        tv_tel.setOnClickListener(v -> {
            onSelectListener.onClick();
            dismiss();
        });

        cancel.setOnClickListener(v -> {
            dismiss();
        });
    }

    public interface OnSelectListener {
        void onClick();
    }
}
