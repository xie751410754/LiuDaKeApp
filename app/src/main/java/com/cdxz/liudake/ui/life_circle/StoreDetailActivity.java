package com.cdxz.liudake.ui.life_circle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.life_circle.LifeCircleBannerAdapter;
import com.cdxz.liudake.adapter.life_circle.StoreCommentAdapter;
import com.cdxz.liudake.adapter.shop_mall.GoodsDetailBannerAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.bean.LifeCircleDetailBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.DrawableTextView;
import com.lxj.xpopup.XPopup;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class StoreDetailActivity extends BaseActivity {

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

    @BindView(R.id.tvCollectNum)
    DrawableTextView tvCollectNum;

    @BindView(R.id.tvStoreAddress)
    DrawableTextView tvStoreAddress;

    @BindView(R.id.tvShopPhone)
    DrawableTextView tvShopPhone;

    @BindView(R.id.recyclerStoreComment)
    RecyclerView recyclerStoreComment;

    private StoreCommentAdapter mAdapter;
    private String id;

    public static void startStoreDetailActivity(Context context, String id) {
        Intent intent = new Intent(context, StoreDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_store_detail;
    }

    @Override
    protected void initViews() {
        setTitleText("商家主页");
        recyclerStoreComment.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initDatas() {
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
            tvCollectNum.setText(String.format("%s人 收藏", detailBean.getMembercount()));
            tvStoreAddress.setText(detailBean.getAddress());
            tvShopPhone.setText(detailBean.getContact());
            findViewById(R.id.tvGo).setOnClickListener(v -> {
                Uri uri = Uri.parse("geo:" + detailBean.getLng() + "," + detailBean.getLat());  //打开地图定位
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                ComponentName cn = it.resolveActivity(getPackageManager());
                if (cn == null) {
                    ToastUtils.showShort("请先安装第三方导航软件");
                } else {
                    startActivity(it);
                }
            });
        });
    }

    @Override
    protected void initListener() {
        tvShopPhone.setOnClickListener(v -> {
            new XPopup.Builder(this)
                    .asConfirm("拨打电话", "是否拨打" + tvShopPhone.getText().toString(), () -> {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + tvShopPhone.getText().toString());
                        intent.setData(data);
                        startActivity(intent);
                    }).show();
        });
        findViewById(R.id.tvStoreAppoint).setOnClickListener(v -> {
            new XPopup.Builder(this)
                    .asConfirm("拨打电话", "是否拨打" + tvShopPhone.getText().toString(), () -> {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + tvShopPhone.getText().toString());
                        intent.setData(data);
                        startActivity(intent);
                    }).show();
        });
    }
}