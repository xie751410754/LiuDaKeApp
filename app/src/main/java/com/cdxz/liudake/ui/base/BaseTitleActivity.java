package com.cdxz.liudake.ui.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.cdxz.liudake.R;
import com.gyf.immersionbar.ImmersionBar;

public abstract class BaseTitleActivity<T extends ViewDataBinding> extends AppCompatActivity {
    protected T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        binding.setLifecycleOwner(this);

        initViewObservable();
        initData();
    }

    public abstract int initContentView(Bundle savedInstanceState);

    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void initToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void initToolbar(Toolbar toolbar, View.OnClickListener onClickListener){
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(onClickListener);
    }

    public void initData() {
        int statusBarColor = ContextCompat.getColor(this, R.color.transparent);
        ImmersionBar.with(this)
                .fitsSystemWindows(false)
                .statusBarColorInt(statusBarColor)
                .statusBarDarkFont(true).init();
    }


    public void initViewObservable() {

    }



}
