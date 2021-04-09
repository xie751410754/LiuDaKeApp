package com.cdxz.liudake.ui.main;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.WelcomeBannerAdapter;
import com.cdxz.liudake.bean.WelcomeDto;
import com.cdxz.liudake.ui.LaunchActivity;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.login.LoginActivity;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends BaseActivity {

    private Banner banner;
    private ImageView imgGo;
    private ImageView tvGo;
    List<WelcomeDto> list = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initViews() {

        banner = findViewById(R.id.banner);
        imgGo = findViewById(R.id.img_go);
        tvGo = findViewById(R.id.tv_go);
    }

    @Override
    protected void initDatas() {

        WelcomeDto welcomeDto1 = new WelcomeDto(R.mipmap.welcome_bg4);
        WelcomeDto welcomeDto3 = new WelcomeDto(R.mipmap.welcome_bg6);
        WelcomeDto welcomeDto2 = new WelcomeDto(R.mipmap.welcome_bg5);

        list.add(welcomeDto1);
        list.add(welcomeDto3);
        list.add(welcomeDto2);

        banner.isAutoLoop(false);
        banner.setAdapter(new WelcomeBannerAdapter(list))
                .setIndicator(new CircleIndicator(this));


        banner.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == list.size() - 1) {
                    imgGo.setVisibility(View.VISIBLE);
                    tvGo.setVisibility(View.VISIBLE);
//                    banner.setUserInputEnabled(false);
                } else {
                    imgGo.setVisibility(View.INVISIBLE);
                    tvGo.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initListener() {

        imgGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LaunchActivity.startLaunchActivity(context);
                LoginActivity.startLoginActivity(context);
            }
        });
        tvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LaunchActivity.startLaunchActivity(context);
                LoginActivity.startLoginActivity(context);


            }
        });


    }


    @Override
    public Resources getResources() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 750);
        } else {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 750);
        }
    }
}
