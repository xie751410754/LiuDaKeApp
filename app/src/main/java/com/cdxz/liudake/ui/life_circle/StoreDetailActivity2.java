package com.cdxz.liudake.ui.life_circle;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.BasePagerAdapter;
import com.cdxz.liudake.adapter.life_circle.LifeCircleBannerAdapter;
import com.cdxz.liudake.adapter.life_circle.StoreCommentAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.bean.LifeCircleDetailBean;
import com.cdxz.liudake.pop.PopMap;
import com.cdxz.liudake.pop.PopSelector;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.ThirdPartyMapsGuide;
import com.cdxz.liudake.view.DrawableTextView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lxj.xpopup.XPopup;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class StoreDetailActivity2 extends BaseActivity {

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

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPagerList)
    ViewPager2 viewPagerList;

    private final List<Fragment> fragmentList = new ArrayList<>();
    private String mTitles[] = {
            "商家推荐",  "评价"};

    @BindView(R.id.recyclerStoreComment)
    RecyclerView recyclerStoreComment;

    private StoreCommentAdapter mAdapter;
    private String id;

    public static void startStoreDetailActivity(Context context, String id) {
        Intent intent = new Intent(context, StoreDetailActivity2.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_store_detail_new;
    }

    @Override
    protected void initViews() {
        recyclerStoreComment.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initDatas() {

        fragmentList.add(TuiJianStoreFragment.newInstance(getIntent().getStringExtra("id")));
        fragmentList.add(CommentFragment.newInstance(getIntent().getStringExtra("id")));
        viewPagerList.setAdapter(new BasePagerAdapter(this, fragmentList));
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPagerList, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(mTitles[position]);
            }
        });
        mediator.attach();


        List<Object> objects = new ArrayList<>();
        mAdapter = new StoreCommentAdapter(objects);
        recyclerStoreComment.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.public_no_comment);
        //
        HttpsUtil.getInstance(this).shopDetail(getIntent().getStringExtra("id"), object -> {
            LifeCircleDetailBean detailBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), LifeCircleDetailBean.class);
            List<String> shopPhotos = new ArrayList<>();
            if (!StringUtils.isEmpty(detailBean.getShop_photos())) {
                shopPhotos.addAll(Arrays.asList(detailBean.getShop_photos().split(",")));
            } else {
                shopPhotos.add(detailBean.getLogo());
            }
            banner.setAdapter(new LifeCircleBannerAdapter(shopPhotos), true)
                    .setIndicator(new CircleIndicator(this));
            if (StringUtils.isEmpty(detailBean.getParent_cate_name())) {
                tvStoreType.setText("暂无");
            } else {
                tvStoreType.setText(String.format("%s > %s", detailBean.getParent_cate_name(), detailBean.getCate_name()));
            }
            tvStoreName.setText(detailBean.getName());
            tvStoreConsumption.setText(String.format("人均 ¥%s", detailBean.getAverage_money()));
            tvStoreTime.setText(String.format("营业时间  %s-%s", detailBean.getOpen_start_time(), detailBean.getOpen_end_time()));
            tvStoreAddress.setText(detailBean.getAddress());
            findViewById(R.id.img_call).setOnClickListener(v -> {

                new XPopup.Builder(this)
                        .asCustom(new PopSelector(context, detailBean.getContact(), new PopSelector.OnSelectListener() {
                            @Override
                            public void onClick() {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                Uri data = Uri.parse("tel:" + detailBean.getContact());
                                intent.setData(data);
                                startActivity(intent);
                            }
                        })).show();

            });
            findViewById(R.id.img_go).setOnClickListener(v -> {
//                Uri uri = Uri.parse("geo:" + detailBean.getLng() + "," + detailBean.getLat()+"?q="+detailBean.getName());  //打开地图定位
//
//                Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                ComponentName cn = it.resolveActivity(getPackageManager());
//                if (cn == null) {
//                    ToastUtils.showShort("请先安装第三方导航软件");
//                } else {
//                    startActivity(it);
//                }


                new XPopup.Builder(this)
                        .asCustom(new PopMap(context, position -> {
                            switch (position) {
                                case 1:

//                                    ThirdPartyMapsGuide.goToGaoDeMap(context, Double.valueOf(detailBean.getLng()), Double.valueOf(detailBean.getLat()), detailBean.getName());
                                    ThirdPartyMapsGuide.transform(context, detailBean.getName(), Double.valueOf(detailBean.getLat()), Double.valueOf(detailBean.getLng()), 1);

                                    break;
                                case 2:

                                    ThirdPartyMapsGuide.baiduMap(context, Double.valueOf(detailBean.getLng()), Double.valueOf(detailBean.getLat()),detailBean.getName());

//                                    ThirdPartyMapsGuide.transform(context, detailBean.getName(), Double.valueOf(detailBean.getLat()), Double.valueOf(detailBean.getLng()), 2);


                                    break;
                                case 3:
//                                    ThirdPartyMapsGuide.goToTencentMap(context, detailBean.getName(), Double.valueOf(detailBean.getLng()), Double.valueOf(detailBean.getLat()));
                                    ThirdPartyMapsGuide.transform(context, detailBean.getName(), Double.valueOf(detailBean.getLat()), Double.valueOf(detailBean.getLng()), 3);


                                    break;
                                default:
                            }
                        })).show();

            });
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