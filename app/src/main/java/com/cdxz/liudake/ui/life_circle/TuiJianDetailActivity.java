package com.cdxz.liudake.ui.life_circle;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Paint;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.BasePagerAdapter;
import com.cdxz.liudake.adapter.life_circle.LifeCircleBannerAdapter;
import com.cdxz.liudake.adapter.life_circle.LifecircleGoodsImageDetailAdapter;
import com.cdxz.liudake.adapter.life_circle.StoreCommentAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.LifeCircleDetailBean;
import com.cdxz.liudake.bean.TuijianDetailDto;
import com.cdxz.liudake.pop.PopMap;
import com.cdxz.liudake.pop.PopSelector;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.ThirdPartyMapsGuide;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lxj.xpopup.XPopup;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class TuiJianDetailActivity extends BaseActivity {

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.tvStoreType)
    TextView tvStoreType;

    @BindView(R.id.tvStoreName)
    TextView tvStoreName;

    @BindView(R.id.tvStoreConsumption)
    TextView tvStoreConsumption;

    @BindView(R.id.tvStoreTime)
    TextView tvStoreTime;


    @BindView(R.id.tvStoreAddress)
    TextView tvStoreAddress;
    @BindView(R.id.tv_SalePrice)
    TextView tv_SalePrice;


    @BindView(R.id.tv_OrginalPrice)
    TextView tv_OrginalPrice;

    @BindView(R.id.imgDetail)
    ImageView imgDetail;
    @BindView(R.id.rv_imgDetail)
    RecyclerView rv_imgDetail;

    LifecircleGoodsImageDetailAdapter lifecircleGoodsImageDetailAdapter;


    public static void startTuiJianDetailActivity(Context context, String id,String shopId) {
        Intent intent = new Intent(context, TuiJianDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("shopId",shopId);
        context.startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_tuijian_detail;
    }

    @Override
    protected void initViews() {

        rv_imgDetail.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initDatas() {


        HttpsUtil.getInstance(this).getGoodsInfo(getIntent().getStringExtra("shopId"),getIntent().getStringExtra("id"), object -> {
            TuijianDetailDto detailBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), TuijianDetailDto.class);
            List<String> shopPhotos = new ArrayList<>();
           for (int i=0;i<detailBean.getGoods_image().size();i++){
               shopPhotos.addAll(detailBean.getGoods_image());
           }

            banner.setAdapter(new LifeCircleBannerAdapter(shopPhotos), true)
                    .setIndicator(new CircleIndicator(this));
           lifecircleGoodsImageDetailAdapter = new LifecircleGoodsImageDetailAdapter(detailBean.getDetail());
           rv_imgDetail.setAdapter(lifecircleGoodsImageDetailAdapter);
           tvStoreType.setText(detailBean.getDescription());
            tvStoreName.setText(detailBean.getName());
            tvStoreTime.setText(String.format("库存:%s",detailBean.getStock()));
            tvStoreConsumption.setText(String.format("¥%s",detailBean.getSaleprice()));
            tvStoreAddress.setText(String.format("¥%s",detailBean.getOrginalprice()));
            tvStoreAddress.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

            tv_SalePrice.setText(String.format("¥%s",detailBean.getSaleprice()));
            tv_OrginalPrice.setText(String.format("¥%s",detailBean.getOrginalprice()));
            tv_OrginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

            Glide.with(context)
                    .load(Constants.PICTURE_HTTPS_URL +detailBean.getGoods_image().get(0))
                    .placeholder(R.mipmap.img_default)
                    .into((imgDetail));
        });
    }

    @Override
    protected void initListener() {

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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