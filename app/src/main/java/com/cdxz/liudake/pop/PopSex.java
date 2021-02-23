package com.cdxz.liudake.pop;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cdxz.liudake.R;
import com.lxj.xpopup.core.BottomPopupView;

public class PopSex extends BottomPopupView {

    TextView tvBoy;
    TextView tvGirl;
    private OnSelectListener onSelectListener;

    public PopSex(@NonNull Context context, OnSelectListener onSelectListener) {
        super(context);
        this.onSelectListener = onSelectListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_sex;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tvBoy = findViewById(R.id.tvBoy);
        tvGirl = findViewById(R.id.tvGirl);

        tvBoy.setOnClickListener(v -> {
            onSelectListener.onSelect(tvBoy.getText().toString());
            dismiss();
        });
        tvGirl.setOnClickListener(v -> {
            onSelectListener.onSelect(tvGirl.getText().toString());
            dismiss();
        });
    }

    public interface OnSelectListener {
        void onSelect(String text);
    }
}
