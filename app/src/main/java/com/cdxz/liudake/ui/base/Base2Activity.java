package com.cdxz.liudake.ui.base;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.cdxz.liudake.R;

public abstract class Base2Activity<T extends ViewDataBinding> extends FragmentActivity {
    private static final String TAG = Base2Activity.class.getSimpleName();

    protected T binding;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getContentViewId(savedInstanceState));
        binding.setLifecycleOwner(this);
        //
        ScreenUtils.setPortrait(this);
        BarUtils.setStatusBarLightMode(this, true);
        initViews();
        initDatas();
        initListener();
    }

    protected abstract int getContentViewId(Bundle savedInstanceState);//放layoutId

    protected abstract void initViews();

    protected abstract void initDatas();

    protected abstract void initListener();

    protected void onRightListener() {

    }

    public void setTitleText(String title) {
        TextView titleText = findViewById(R.id.tvTitleText);
        if (titleText != null) {
            titleText.setText(title);
        }
    }

    public void showTitleRight() {
        TextView tvTitleRight = findViewById(R.id.tvTitleRight);
        if (tvTitleRight != null) {
            tvTitleRight.setVisibility(View.VISIBLE);
        }
    }

    public void hideTitleRight() {
        TextView tvTitleRight = findViewById(R.id.tvTitleRight);
        if (tvTitleRight != null) {
            tvTitleRight.setVisibility(View.GONE);
        }
    }

    public void setTitleRightText(String text) {
        TextView tvTitleRight = findViewById(R.id.tvTitleRight);
        if (tvTitleRight != null) {
            showTitleRight();
            tvTitleRight.setText(text);
        }
    }

    public void setTitleRightTextColor(int color) {
        TextView tvTitleRight = findViewById(R.id.tvTitleRight);
        if (tvTitleRight != null) {
            tvTitleRight.setTextColor(ContextCompat.getColor(this, color));
        }
    }

    public void onBack(View view) {
        if (view.getId() == R.id.ivTitleBack) {
            finish();
        }
    }

    public void onRightClick(View view) {
        if (view.getId() == R.id.tvTitleRight) {
            onRightListener();
        }
    }

    public void hideKeyboard() {
        if (KeyboardUtils.isSoftInputVisible(this)) {
            KeyboardUtils.hideSoftInput(this);
        }
    }
}
