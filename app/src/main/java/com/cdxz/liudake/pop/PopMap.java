package com.cdxz.liudake.pop;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cdxz.liudake.R;
import com.lxj.xpopup.core.BottomPopupView;

public class PopMap extends BottomPopupView {

    public static final int GAODEMAP = 1;
    public static final int BAIDUMAP = 2;
    public static final int TENCENTMAP = 3;

    TextView gaodeMap;
    TextView baiduMap;
    TextView tencentMap;
    TextView cancel;
    private OnSelectListener onSelectListener;

    public PopMap(@NonNull Context context, OnSelectListener onSelectListener) {
        super(context);
        this.onSelectListener = onSelectListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_map;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        gaodeMap = findViewById(R.id.tv_gaodeMap);
        baiduMap = findViewById(R.id.baiduMap);
        tencentMap = findViewById(R.id.tv_tencentMap);
        cancel = findViewById(R.id.tv_cancel);

        gaodeMap.setOnClickListener(v -> {
            onSelectListener.onSelect(GAODEMAP);
            dismiss();
        });
        baiduMap.setOnClickListener(v -> {
            onSelectListener.onSelect(BAIDUMAP);
            dismiss();
        });
        tencentMap.setOnClickListener(v -> {
            onSelectListener.onSelect(TENCENTMAP);
            dismiss();
        });
        cancel.setOnClickListener(v -> {
            dismiss();
        });
    }

    public interface OnSelectListener {
        void onSelect(int position);
    }
}
