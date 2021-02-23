package com.cdxz.liudake.pop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.PopGoodsSpecificaAdapter;
import com.cdxz.liudake.bean.GoodsDetailBean;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PopGoodsDesc extends BottomPopupView {

    private TextView tvGoodsDesc;
    private GoodsDetailBean bean;

    public PopGoodsDesc(@NonNull Context context, GoodsDetailBean bean) {
        super(context);
        this.bean = bean;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_goods_desc;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate() {
        super.onCreate();
        tvGoodsDesc = findViewById(R.id.tvGoodsDesc);
        tvGoodsDesc.setText(bean.getDescription());
        findViewById(R.id.tvSubmit).setOnClickListener(v -> {
            dismiss();
        });
    }
}
