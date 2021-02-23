package com.cdxz.liudake.ui.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.ApiRetrofit;
import com.cdxz.liudake.base.BaseObserver;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends FragmentActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    protected Activity context;
    private Unbinder unbinder;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        unbinder = ButterKnife.bind(this);
        context = this;
        ScreenUtils.setPortrait(this);
        BarUtils.setStatusBarLightMode(this, true);
        initViews();
        initDatas();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    protected abstract int getContentViewId();//放layoutId

    protected abstract void initViews();

    protected abstract void initDatas();

    protected abstract void initListener();

    protected void onRightListener() {

    }

    protected void hideSystemUi() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    //    public void setTitleBackgroundColor(int colorId) {
//        RelativeLayout rootTitle = findViewById(R.id.rootTitle);
//        if (rootTitle != null) {
//            rootTitle.setBackgroundColor(colorId);
//        }
//    }
//
//    public void setTitleWhiteBackImage() {
//        ImageView titleBackImage = findViewById(R.id.titleBackImage);
//        if (titleBackImage != null) {
//            titleBackImage.setImageResource(R.mipmap.icon_white_back);
//        }
//    }
//
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

    //
//    public void setTitleTextColor(int colorId) {
//        TextView titleText = findViewById(R.id.titleText);
//        if (titleText != null) {
//            titleText.setTextColor(ContextCompat.getColor(this, colorId));
//        }
//    }
//
//
//    public void hideTitleView() {
//        View titleView = findViewById(R.id.titleView);
//        if (titleView != null) {
//            titleView.setVisibility(View.GONE);
//        }
//    }
//
//
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
