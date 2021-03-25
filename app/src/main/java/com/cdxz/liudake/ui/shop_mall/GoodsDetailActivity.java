package com.cdxz.liudake.ui.shop_mall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.GoodsCommentAdapter;
import com.cdxz.liudake.adapter.shop_mall.GoodsDetailBannerAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.GoodsDetailBean;
import com.cdxz.liudake.bean.ShopCarSettlementBean;
import com.cdxz.liudake.pop.PopGoodsDesc;
import com.cdxz.liudake.pop.PopGoodsSpecifica;
import com.cdxz.liudake.ui.SearchActivity;
import com.cdxz.liudake.ui.SearchResultActivity;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.order.OrderSubmitActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.UserInfoUtil;
import com.cdxz.liudake.view.DrawableTextView;
import com.cdxz.liudake.view.SpacesItemDecoration;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.lxj.xpopup.XPopup;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodsDetailActivity extends BaseActivity {

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.tvGoodsPrice)
    TextView tvGoodsPrice;

    @BindView(R.id.tvGoodsOldPrice)
    TextView tvGoodsOldPrice;

    @BindView(R.id.tvScoreNum)
    TextView tvScoreNum;

    @BindView(R.id.tvSellNum)
    TextView tvSellNum;

    @BindView(R.id.tvClickNum)
    TextView tvClickNum;

    @BindView(R.id.tvGoodsName)
    TextView tvGoodsName;

    @BindView(R.id.ivStorePic)
    RoundedImageView ivStorePic;

    @BindView(R.id.tvStoreName)
    TextView tvStoreName;

    @BindView(R.id.tvPinzhi)
    TextView tvPinzhi;

    @BindView(R.id.tvYouzhi)
    TextView tvYouzhi;

    @BindView(R.id.tvQitian)
    TextView tvQitian;

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.recyclerComment)
    RecyclerView recyclerComment;

    @BindView(R.id.tvCollect)
    DrawableTextView tvCollect;

    @BindView(R.id.tvSelectCanShu)
    DrawableTextView tvSelectCanShu;

    @BindView(R.id.topLayout)
    ConstraintLayout topLayout;

    @BindView(R.id.detailNestedScrollView)
    NestedScrollView detailNestedScrollView;

    @BindView(R.id.ivTitleBack)
    ImageView ivTitleBack;

    @BindView(R.id.ivShare)
    ImageView ivShare;

    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;

    @BindView(R.id.tv_index)
    TextView tvIndex;

    int bannerSize = 0;

    //
    private GoodsCommentAdapter commentAdapter;

    boolean needPhoneNumber = false;

    public static void startGoodsDetailActivity(Context context, String goodsId) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("goodsId", goodsId);
        context.startActivity(intent);
    }

    public static void startGoodsDetailActivity(Context context, String goodsId, String activeId) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("goodsId", goodsId);
        intent.putExtra("cuxiao_id", activeId);
        context.startActivity(intent);
    }

    public static void startGoodsDetailActivity(Context context, String goodsId, boolean needPhoneNumber) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("goodsId", goodsId);
        intent.putExtra("need_phone_number", needPhoneNumber);
        context.startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initViews() {
        ivTitleBack.setColorFilter(ContextCompat.getColor(this, R.color.color_343434));
        ivShare.setColorFilter(ContextCompat.getColor(this, R.color.color_343434));

        tvGoodsOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        needPhoneNumber = getIntent().getBooleanExtra("need_phone_number", false);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerComment.setLayoutManager(layoutManager);
        recyclerComment.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(20), false));


        if (needPhoneNumber) {
            marqueeView.setVisibility(View.VISIBLE);
            List<String> messages = new ArrayList<>();
            messages.add("186xxxx9792成功抢购");
            messages.add("135xxxx5569成功抢购");
            messages.add("135xxxx3394成功抢购");
            messages.add("169xxxx2441成功抢购");
            messages.add("199xxxx1238成功抢购");
            messages.add("136xxxx7647成功抢购");
            messages.add("135xxxx8371成功抢购");
            messages.add("189xxxx1379成功抢购");
            messages.add("186xxxx8474成功抢购");
            messages.add("189xxxx8257成功抢购");
            marqueeView.startWithList(messages);


        }


        banner.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                try {
                    tvIndex.setText((position + 1) + "/" + bannerSize);
                } catch (Exception e) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    protected void initDatas() {
        List<Object> objects = new ArrayList<>();
        commentAdapter = new GoodsCommentAdapter(objects);
        recyclerComment.setAdapter(commentAdapter);
        commentAdapter.setEmptyView(R.layout.public_no_data);
        getGoodsDetail();
    }

    @Override
    protected void initListener() {
        detailNestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, i, i1, i2, i3) -> {
            int height = SizeUtils.dp2px(50);
            if (i1 <= 0) {
//                topLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
                topLayout.setAlpha(0);
            } else if (i1 > 0 && i1 < height) {
                topLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                //获取渐变率
                float scale = (float) i1 / height;
                //获取渐变数值
                float alpha = (1.0f * scale);
                topLayout.setAlpha(alpha);
            } else {
                topLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                topLayout.setAlpha(1f);
            }
        });
        findViewById(R.id.tvGoShopCar).setOnClickListener(v -> {
            ActivityUtils.finishActivity(GoodsClassActivity.class);
            ActivityUtils.finishActivity(GoodsListActivity.class);
            ActivityUtils.finishActivity(HomeToGoodsListActivity.class);
            ActivityUtils.finishActivity(SearchResultActivity.class);
            ActivityUtils.finishActivity(SearchActivity.class);
            BusUtils.post(BusTag.GOODS_DETAIL_TO_CAR);
            finish();
        });
        findViewById(R.id.tvKefu).setOnClickListener(v -> {
            new XPopup.Builder(this)
                    .asConfirm("拨打电话", "是否拨打4008880871", () -> {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:4008880871");
                        intent.setData(data);
                        startActivity(intent);
                    }).show();
        });

        findViewById(R.id.ivShare).setOnClickListener(v -> {
            Intent textIntent = new Intent(Intent.ACTION_SEND);
            textIntent.setType("text/plain");
            textIntent.putExtra(Intent.EXTRA_TEXT, Constants.BASE_HTTPS_URL + "Home/index/regist/invitecode/" + UserInfoUtil.getUid());
            startActivity(Intent.createChooser(textIntent, "分享"));
        });
    }

    @SuppressLint("SetTextI18n")
    private void getGoodsDetail() {
        HttpsUtil.getInstance(this).goodsDetail(getIntent().getStringExtra("goodsId"), Constants.LNG, Constants.LAT, null, Constants.CITY,getIntent().getStringExtra("cuxiao_id") == null ? "0" : getIntent().getStringExtra("cuxiao_id"), object -> {
            GoodsDetailBean detailBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), GoodsDetailBean.class);


            try {
                if (Double.parseDouble(detailBean.getSaleprice()) == 0 && Double.parseDouble(detailBean.getGold()) > 0) {
                    tvSellNum.setVisibility(View.INVISIBLE);
                    tvClickNum.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {

            }


            tvGoodsPrice.setText("￥" + detailBean.getSaleprice());
            tvGoodsOldPrice.setText("￥" + detailBean.getOrginalprice());
            tvScoreNum.setText("积分 " + detailBean.getGold());
            tvSellNum.setText("销售 " + detailBean.getSalescount());
            tvClickNum.setText("点击 " + detailBean.getHit());
            tvGoodsName.setText(detailBean.getName());
            if (detailBean.getIscollection() == 1) {
                tvCollect.setTopDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_star_yellow));
                tvCollect.setText("已收藏");
            } else {
                tvCollect.setTopDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_colloction));
                tvCollect.setText("收藏");
            }
            tvSelectCanShu.setOnClickListener(v -> {
                new XPopup.Builder(this)
                        .asCustom(new PopGoodsSpecifica(this, detailBean, (s, num) -> {
                            tvSelectCanShu.setText("已选择：" + s);
                            orderAddCar(s, num);
                        })).show();
            });
            findViewById(R.id.tvGoodsExplain).setOnClickListener(v -> {
                new XPopup.Builder(this)
                        .asCustom(new PopGoodsDesc(this, detailBean))
                        .show();
            });
            findViewById(R.id.tvAddShopCar).setOnClickListener(v -> {
                if (CollectionUtils.isEmpty(detailBean.getSize())) {
                    orderAddCar(null, 1);
                } else {
                    new XPopup.Builder(this)
                            .asCustom(new PopGoodsSpecifica(this, detailBean, (s, num) -> {
                                tvSelectCanShu.setText("已选择：" + s);
                                orderAddCar(s, num);
                            })).show();
                }
            });
            findViewById(R.id.tvBuy).setOnClickListener(v -> {
                new XPopup.Builder(this)
                        .asCustom(new PopGoodsSpecifica(this, detailBean, (s, num) -> {
                            tvSelectCanShu.setText("已选择：" + s);
                            buy(detailBean.getId(), num, s, getIntent().getStringExtra("cuxiao_id") == null ? "0" : getIntent().getStringExtra("cuxiao_id"));
                        })).show();
            });
            tvCollect.setOnClickListener(v -> {
                if (detailBean.getIscollection() == 1) {
                    HttpsUtil.getInstance(this).goodsCollect(detailBean.getId(), 1, obj -> {
                        detailBean.setIscollection(0);
                        tvCollect.setTopDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_colloction));
                        tvCollect.setText("收藏");
                    });
                } else {
                    HttpsUtil.getInstance(this).goodsCollect(detailBean.getId(), 0, obj -> {
                        detailBean.setIscollection(1);
                        tvCollect.setTopDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_star_yellow));
                        tvCollect.setText("已收藏");
                    });
                }
            });
            banner.setAdapter(new GoodsDetailBannerAdapter(detailBean.getGallery()), true)
                    .setIndicator(new CircleIndicator(this));

            bannerSize = detailBean.getGallery().size();

            if (detailBean.getGallery() != null && detailBean.getGallery().size() > 1) {

                tvIndex.setVisibility(View.VISIBLE);
                tvIndex.setText("1/" + detailBean.getGallery().size());
            } else {
                tvIndex.setVisibility(View.INVISIBLE);
            }

//            if (detailBean.getShop_info().getLogo().startsWith("http://") || detailBean.getShop_info().getLogo().startsWith("https://")) {
//                Glide.with(this)
//                        .load(detailBean.getShop_info().getLogo())
//                        .placeholder(R.mipmap.logo)
//                        .into(ivStorePic);
//
//
//            } else {
//                Glide.with(this)
//                        .load(Constants.PICTURE_HTTPS_URL + detailBean.getShop_info().getLogo())
//                        .placeholder(R.mipmap.logo)
//                        .into(ivStorePic);
//            }

            ivStorePic.setImageResource(R.mipmap.logo);

//            tvStoreName.setText(detailBean.getShop_info().getName());
            if (detailBean.getIs_pinzhi() == null) {
                tvPinzhi.setVisibility(View.GONE);
            } else {
                if (detailBean.getIs_pinzhi().equals("1")) {
                    tvPinzhi.setVisibility(View.VISIBLE);
                } else {
                    tvPinzhi.setVisibility(View.GONE);
                }
            }
            if (detailBean.getIs_youzhi() == null) {
                tvYouzhi.setVisibility(View.GONE);
            } else {
                if (detailBean.getIs_youzhi().equals("1")) {
                    tvYouzhi.setVisibility(View.VISIBLE);
                } else {
                    tvYouzhi.setVisibility(View.GONE);
                }
            }
            if (detailBean.getIs_qitian() == null) {
                tvQitian.setVisibility(View.GONE);
            } else {
                if (detailBean.getIs_qitian().equals("1")) {
                    tvQitian.setVisibility(View.VISIBLE);
                } else {
                    tvQitian.setVisibility(View.GONE);
                }
            }
            webView.loadUrl(detailBean.getDetail());
        });
    }

    /**
     * 加入购物车
     */
    private void orderAddCar(String size, int count) {
        HttpsUtil.getInstance(this).orderAddCar(size, getIntent().getStringExtra("goodsId"), count, object -> {
            BusUtils.post(BusTag.UPDATE_CAR);
        });
    }

    /**
     * 立即购买
     *
     * @param id
     * @param count
     * @param size
     */
    private void buy(String id, int count, String size, String cuxiao_id) {
        HttpsUtil.getInstance(this).buy(id, count, size, cuxiao_id, object -> {
            settlement();
        });
    }

    /**
     * 购物车结算
     */
    private void settlement() {
        HttpsUtil.getInstance(this).settlement(2, 0, 0, object -> {
            ShopCarSettlementBean settlementBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), ShopCarSettlementBean.class);
            OrderSubmitActivity.startOrderSubmitActivity(this, settlementBean);
        });
    }
}