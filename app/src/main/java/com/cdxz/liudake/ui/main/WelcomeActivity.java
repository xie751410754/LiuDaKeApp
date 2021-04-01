package com.cdxz.liudake.ui.main;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.HomeCXBannerAdapter;
import com.cdxz.liudake.adapter.shop_mall.WelcomeBannerAdapter;
import com.cdxz.liudake.bean.CXBannerBean;
import com.cdxz.liudake.bean.WelcomeDto;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends BaseActivity {

    private Banner banner;
    private ImageView imgGo;
    private TextView tvGo;
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

        WelcomeDto welcomeDto1 = new WelcomeDto(R.mipmap.welcome_bg1);
        WelcomeDto welcomeDto2 = new WelcomeDto(R.mipmap.welcome_bg2);
        WelcomeDto welcomeDto3 = new WelcomeDto(R.mipmap.welcome_bg3);
        list.add(welcomeDto1);
        list.add(welcomeDto2);
        list.add(welcomeDto3);

        banner.isAutoLoop(false);
        banner.setAdapter(new WelcomeBannerAdapter(list))
                .setIndicator(new CircleIndicator(this));
    }

    @Override
    protected void initListener() {

    }
}
