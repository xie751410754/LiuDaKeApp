package com.cdxz.liudake.pop;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cdxz.liudake.R;
import com.lxj.xpopup.core.BottomPopupView;

public class PopLifeCirclePrice extends BottomPopupView {

    TextView tv1;
    TextView tv2;
    TextView tvCancel;
    private OnPriceListener onPriceListener;

    public PopLifeCirclePrice(@NonNull Context context, OnPriceListener onPriceListener) {
        super(context);
        this.onPriceListener = onPriceListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_life_circle_price;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tvCancel = findViewById(R.id.tvCancel);

        tv1.setOnClickListener(v -> {
            onPriceListener.onPrice(3, tv1.getText().toString());
            dismiss();
        });
        tv2.setOnClickListener(v -> {
            onPriceListener.onPrice(4, tv2.getText().toString());
            dismiss();
        });
        tvCancel.setOnClickListener(v -> {
            dismiss();
        });
    }

    public interface OnPriceListener {
        void onPrice(int sort, String text);
    }
}
