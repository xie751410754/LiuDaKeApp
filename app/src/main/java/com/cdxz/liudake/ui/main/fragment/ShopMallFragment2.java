package com.cdxz.liudake.ui.main.fragment;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.ActivityAdapter;
import com.cdxz.liudake.adapter.shop_mall.ClassAdapter;
import com.cdxz.liudake.adapter.shop_mall.HomeBannerAdapter;
import com.cdxz.liudake.adapter.shop_mall.HomeCXBannerAdapter;
import com.cdxz.liudake.adapter.shop_mall.HomeGoodsAdapter;
import com.cdxz.liudake.adapter.shop_mall.HomeMenuAdapter;
import com.cdxz.liudake.adapter.shop_mall.HomeQianggouAdapter;
import com.cdxz.liudake.adapter.shop_mall.Menu2Adapter;
import com.cdxz.liudake.adapter.shop_mall.MenuAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.BannerBean;
import com.cdxz.liudake.bean.Bus.PopSuggestionBean;
import com.cdxz.liudake.bean.CXBannerBean;
import com.cdxz.liudake.bean.HomeIndexBean;
import com.cdxz.liudake.ui.ScanQRCodeActivity;
import com.cdxz.liudake.ui.SearchActivity;
import com.cdxz.liudake.ui.base.BaseFragment;
import com.cdxz.liudake.ui.life_circle.LifeCircleMapActivity;
import com.cdxz.liudake.ui.shop_mall.GoodsClassActivity;
import com.cdxz.liudake.ui.shop_mall.HomeToGoodsListActivity;
import com.cdxz.liudake.ui.shop_mall.MessageListActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.DrawableTextView;
import com.cdxz.liudake.view.GridSpacingItemDecoration;
import com.cdxz.liudake.view.SpacesItemDecoration2;
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
import com.lxj.xpopup.XPopup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopMallFragment2 extends BaseFragment {

    @BindView(R.id.tvAddress)
    DrawableTextView tvAddress;

    @BindView(R.id.refreshHome)
    SmartRefreshLayout refreshHome;
    //Banner
    @BindView(R.id.banner)
    Banner banner;
    //Banner
    @BindView(R.id.cxBanner)
    Banner cxBanner;
    //
    @BindView(R.id.tvMessageNum)
    TextView tvMessageNum;
    //菜单
    @BindView(R.id.recyclerMenu)
    RecyclerView recyclerMenu;

    @BindView(R.id.ivRoundedImageView)
    RoundedImageView ivRoundedImageView;

    @BindView(R.id.recyclerMenu2)
    RecyclerView recyclerMenu2;
    //活动专栏
    @BindView(R.id.tvActivityTips)
    TextView tvActivityTips;
    @BindView(R.id.recyclerActivity)
    RecyclerView recyclerActivity;
    //分类推荐
    @BindView(R.id.tvClassTips)
    TextView tvClassTips;
    @BindView(R.id.recyclerClass)
    RecyclerView recyclerClass;
    //推荐商品
    @BindView(R.id.recyclerTuijianGoods)
    RecyclerView recyclerTuijianGoods;
    //抢购
    @BindView(R.id.recyclerQiangGou)
    RecyclerView recyclerQiangGou;

    @BindView(R.id.qiangGouLayout)
    LinearLayout qiangGouLayout;

    @BindView(R.id.tvQiangGouTime)
    TextView tvQiangGouTime;

    @BindView(R.id.tvCountDown)
    TextView tvCountDown;

    @BindView(R.id.tvChaodiTips)
    TextView tvChaodiTips;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.tvTips)
    TextView activeName1;
    @BindView(R.id.tvTips2)
    TextView activeSubName1;
    @BindView(R.id.ivImage1)
    RoundedImageView ivImage1;
    @BindView(R.id.ivImage2)
    RoundedImageView ivImage2;
    @BindView(R.id.tv_jifen)
    TextView tv_jifen1;
    @BindView(R.id.tv_jifen2)
    TextView tv_jifen2;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_price2)
    TextView tv_price2;
    @BindView(R.id.tv_priceType)
    TextView tv_priceType1;
    @BindView(R.id.tv_priceType2)
    TextView tv_priceType2;

    @BindView(R.id.two_tvTips)
    TextView two_tvTips;
    @BindView(R.id.two_tvTips2)
    TextView two_tvTips2;
    @BindView(R.id.two_ivImage1)
    RoundedImageView two_ivImage1;
    @BindView(R.id.two_ivImage2)
    RoundedImageView two_ivImage2;
    @BindView(R.id.two_tv_jifen)
    TextView two_tv_jifen;
    @BindView(R.id.two_jifen2)
    TextView two_jifen2;
    @BindView(R.id.two_tv_price)
    TextView two_tv_price;
    @BindView(R.id.two_tv_price2)
    TextView two_tv_price2;
    @BindView(R.id.two_tv_priceType)
    TextView two_tv_priceType;
    @BindView(R.id.two_tv_priceType2)
    TextView two_tv_priceType2;

    @BindView(R.id.three_tvTips)
    TextView three_tvTips;
    @BindView(R.id.three_tvTips2)
    TextView three_tvTips2;
    @BindView(R.id.three_ivImage1)
    RoundedImageView three_ivImage1;
    @BindView(R.id.three_ivImage2)
    RoundedImageView three_ivImage2;
    @BindView(R.id.three_tv_jifen)
    TextView three_tv_jifen;
    @BindView(R.id.three_jifen2)
    TextView three_jifen2;
    @BindView(R.id.three_tv_price)
    TextView three_tv_price;
    @BindView(R.id.three_tv_price2)
    TextView three_tv_price2;
    @BindView(R.id.three_tv_priceType)
    TextView three_tv_priceType;
    @BindView(R.id.three_tv_priceType2)
    TextView three_tv_priceType2;

    @BindView(R.id.four_tvTips)
    TextView four_tvTips;
    @BindView(R.id.four_tvTips2)
    TextView four_tvTips2;
    @BindView(R.id.four_ivImage1)
    RoundedImageView four_ivImage1;
    @BindView(R.id.four_ivImage2)
    RoundedImageView four_ivImage2;
    @BindView(R.id.four_tv_jifen)
    TextView four_tv_jifen;
    @BindView(R.id.four_jifen2)
    TextView four_jifen2;
    @BindView(R.id.four_tv_price)
    TextView four_tv_price;
    @BindView(R.id.four_tv_price2)
    TextView four_tv_price2;
    @BindView(R.id.four_tv_priceType)
    TextView four_tv_priceType;
    @BindView(R.id.four_tv_priceType2)
    TextView four_tv_priceType2;
    @BindView(R.id.ll_miaosha4)
    LinearLayout ll_miaosha4;

    @BindView(R.id.four_tv_hour)
    TextView tv_hour;
    @BindView(R.id.four_tv_min)
    TextView tv_min;
    @BindView(R.id.four_tv_sec)
    TextView tv_sec;
    @BindView(R.id.active4)
    View miaoshaView;

    public LocationClient mLocationClient = null;
    private ShopMallFragment2.MyLocationListener myListener = new ShopMallFragment2.MyLocationListener();


    //
    private CountDownTimer downTimer;
    private MenuAdapter menuAdapter;
    private Menu2Adapter menu2Adapter;
    private HomeMenuAdapter homeMenuAdapter;
    private ActivityAdapter activityAdapter;
    private ClassAdapter classAdapter;
    private HomeGoodsAdapter goodsAdapter;
    private HomeQianggouAdapter qianggouAdapter;
    private List<HomeIndexBean.GoodsActivityClassBean> menuList = new ArrayList<>();
    private List<HomeIndexBean.GoodsActivityClassBean> homeMenuList = new ArrayList<>();
    private List<HomeIndexBean.GoodsCuxiaoBean> menu2List = new ArrayList<>();
    private List<HomeIndexBean.GoodsCuxiao2Bean> activityList = new ArrayList<>();
    private List<HomeIndexBean.GoodsCuxiao3Bean> classList = new ArrayList<>();
    private List<HomeIndexBean.GoodsCuxiao4Bean> tuijianGoodsList = new ArrayList<>();
    private List<HomeIndexBean.TimeBean.ListBean> qianggouList = new ArrayList<>();

    public ShopMallFragment2() {
    }

//    public static ShopMallFragment newInstance(String param1, String param2) {
//        ShopMallFragment fragment = new ShopMallFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected int getResource() {
        return R.layout.fragment_shop_mall_test;
    }

    @Override
    protected void initView() {
        //菜单
        GridLayoutManager menuLayout = new GridLayoutManager(getContext(), 5);
        recyclerMenu.setLayoutManager(menuLayout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //菜单2
        GridLayoutManager menu2Layout = new GridLayoutManager(getContext(), 2);
        recyclerMenu2.setLayoutManager(menu2Layout);
        recyclerMenu2.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(5), false));
        //活动专栏
        GridLayoutManager activityLayout = new GridLayoutManager(getContext(), 2);
        recyclerActivity.setLayoutManager(activityLayout);
        recyclerActivity.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(10), false));
        //分类推荐
        GridLayoutManager classLayout = new GridLayoutManager(getContext(), 2);
        recyclerClass.setLayoutManager(classLayout);
        recyclerClass.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(10), false));
        //推荐商品

        StaggeredGridLayoutManager recyclerViewLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerTuijianGoods.addItemDecoration(new SpacesItemDecoration2(20));
//        GridLayoutManager goodsLayout = new GridLayoutManager(getContext(), 2);
        recyclerTuijianGoods.setLayoutManager(recyclerViewLayoutManager);
//        recyclerTuijianGoods.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(10), false));
        //抢购
        LinearLayoutManager qianggouLayout = new LinearLayoutManager(getContext());
        qianggouLayout.setOrientation(RecyclerView.HORIZONTAL);
        recyclerQiangGou.setLayoutManager(qianggouLayout);
    }

    @Override
    protected void initData() {
        BusUtils.register(this);

        mLocationClient = new LocationClient(getContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);

        PermissionUtils.permission(
                PermissionConstants.LOCATION,
                PermissionConstants.STORAGE,
                PermissionConstants.PHONE,
                PermissionConstants.CAMERA
        ).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
//                handler.sendEmptyMessageDelayed(1, 500);
                mLocationClient.start();

            }

            @Override
            public void onDenied() {
                ToastUtils.showShort("获取权限失败");
//                AppUtils.exitApp();
//                handler.sendEmptyMessageDelayed(1, 500);

            }
        }).request();



        //
        menuAdapter = new MenuAdapter(menuList);
        recyclerMenu.setAdapter(menuAdapter);
        //
        homeMenuAdapter = new HomeMenuAdapter(homeMenuList, new HomeMenuAdapter.OnSelectListener() {
            @Override
            public void onClick(int position) {

                for (int i = 0; i < homeMenuList.size(); i++) {
                    homeMenuList.get(i).setSelected(false);

                }
                homeMenuList.get(position).setSelected(true);
                homeMenuAdapter.notifyDataSetChanged();


            }
        });
        recyclerView.setAdapter(homeMenuAdapter);
        //
        menu2Adapter = new Menu2Adapter(menu2List);
        recyclerMenu2.setAdapter(menu2Adapter);
        //
        activityAdapter = new ActivityAdapter(activityList);
        recyclerActivity.setAdapter(activityAdapter);
        //
        classAdapter = new ClassAdapter(classList);
        recyclerClass.setAdapter(classAdapter);
        //
        goodsAdapter = new HomeGoodsAdapter(tuijianGoodsList);
        recyclerTuijianGoods.setAdapter(goodsAdapter);
        //
        qianggouAdapter = new HomeQianggouAdapter(qianggouList);
        recyclerQiangGou.setAdapter(qianggouAdapter);
        //
        tvActivityTips.setVisibility(View.GONE);
        recyclerActivity.setVisibility(View.GONE);
        //
        tvClassTips.setVisibility(View.GONE);
        recyclerClass.setVisibility(View.GONE);
        //
        homeIndex();
        getcxList();
    }

    @BusUtils.Bus(tag = BusTag.POP_SUGGESTION)
    public void onPopSuggestion(PopSuggestionBean bean) {
        mLocationClient.stop();
        Constants.LNG = String.valueOf(bean.getLng());
        Constants.LAT = String.valueOf(bean.getLat());
        tvAddress.setText(bean.getAddress());
    }

    @Override
    protected void initListener() {
        refreshHome.setOnRefreshListener(refreshLayout -> homeIndex());
        getActivity().findViewById(R.id.ivScan).setOnClickListener(v -> {
            ScanQRCodeActivity.startScanQRCodeActivity(getContext(), ScanQRCodeActivity.TYPE_PAY);
//            Intent intent = new Intent(getContext(), CaptureActivity.class);
//            startActivityForResult(intent, 0);
        });
        getActivity().findViewById(R.id.dtv_more).setOnClickListener(v -> {
            GoodsClassActivity.startGoodsClassActivity(getContext());

        });

    }

    @BusUtils.Bus(tag = BusTag.UPDATE_MESSAGE_NUM)
    public void onUpdateMessage() {
        getNoreadMessage();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.tvHomeSearch, R.id.ivMsg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvHomeSearch:
                SearchActivity.startSearchActivity(getContext(), null);
                break;
            case R.id.ivMsg:
//                ToastUtils.showShort("暂无消息");
                MessageListActivity.startMessageListActivity(getContext(), 1);
                break;
        }
    }

    /**
     * banner数据
     */
    private void getBannerList() {
        HttpsUtil.getInstance(getContext()).positionList(5, Constants.LNG, Constants.LAT, object -> {
            LogUtils.e("banner = " + GsonUtils.toJson(object));
            List<BannerBean> bannerBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), BannerBean.class);

            //http://47.108.198.70/Uploads/Picture/20201030/s_b3f05628067dc322.jpg
        });
    }

    /**
     * 促销banner数据
     */
    private void getcxList() {
        HttpsUtil.getInstance(getContext()).cxBannerList(5, Constants.LNG, Constants.LAT, object -> {
            LogUtils.e("banner = " + GsonUtils.toJson(object));
            List<CXBannerBean> bannerBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), CXBannerBean.class);

            List<CXBannerBean> bannerBeans = new ArrayList<>();
            for (int i = 0; i < bannerBeanList.size(); i++) {
                bannerBeans.add(bannerBeanList.get(i));
            }
            cxBanner.setAdapter(new HomeCXBannerAdapter(bannerBeans), true)
                    .setIndicator(new CircleIndicator(getContext()));

            //http://47.108.198.70/Uploads/Picture/20201030/s_b3f05628067dc322.jpg
        });
    }

    private void homeIndex() {
        HttpsUtil.getInstance(getContext()).homeIndex(object -> {
            if (refreshHome.getState() == RefreshState.Refreshing) {
                refreshHome.finishRefresh();
            }
            getNoreadMessage();
            HomeIndexBean homeIndexBean = ParseUtils.parseJsonObject(GsonUtils.toJson(object), HomeIndexBean.class);
            //
            List<BannerBean> bannerBeans = new ArrayList<>();
            for (int i = 0; i < homeIndexBean.getImgs().size(); i++) {
                if (homeIndexBean.getImgs().get(i).getType().equals("5")) {
                    bannerBeans.add(homeIndexBean.getImgs().get(i));
                }
            }
            banner.setAdapter(new HomeBannerAdapter(bannerBeans), true)
                    .setIndicator(new CircleIndicator(getContext()));
            //
            menuList.clear();
            menuList.addAll(homeIndexBean.getGoods_activity_class());
            menuAdapter.notifyDataSetChanged();
            //
            homeMenuList.clear();
            homeMenuList.addAll(homeIndexBean.getGoods_activity_class());
            homeIndexBean.getGoods_activity_class().get(0).setSelected(true);
            homeMenuAdapter.notifyDataSetChanged();
            //
            if (ObjectUtils.isNotEmpty(homeIndexBean.getAdvertisement2())) {
                Glide.with(getContext())
                        .load(homeIndexBean.getAdvertisement2().getImg().startsWith("http") ? homeIndexBean.getAdvertisement2().getImg() : Constants.BANNER_HTTPS_URL + homeIndexBean.getAdvertisement2().getImg())
                        .placeholder(R.mipmap.img_default)
                        .into(ivRoundedImageView);
            }
            //
            menu2List.clear();
            menu2List.addAll(homeIndexBean.getGoods_cuxiao());
            menu2Adapter.notifyDataSetChanged();

            initActiveView(homeIndexBean.getGoods_cuxiao());

//            //活动专栏
//            if (CollectionUtils.isEmpty(homeIndexBean.getGoods_cuxiao2())) {
//                tvActivityTips.setVisibility(View.GONE);
//                recyclerActivity.setVisibility(View.GONE);
//            } else {
//                tvActivityTips.setVisibility(View.VISIBLE);
//                recyclerActivity.setVisibility(View.VISIBLE);
//                activityList.clear();
//                activityList.addAll(homeIndexBean.getGoods_cuxiao2());
//                activityAdapter.notifyDataSetChanged();
//            }
//            //分类推荐
//            if (CollectionUtils.isEmpty(homeIndexBean.getGoods_cuxiao3())) {
//                tvClassTips.setVisibility(View.GONE);
//                recyclerClass.setVisibility(View.GONE);
//            } else {
//                tvClassTips.setVisibility(View.VISIBLE);
//                recyclerClass.setVisibility(View.VISIBLE);
//                classList.clear();
//                classList.addAll(homeIndexBean.getGoods_cuxiao3());
//                classAdapter.notifyDataSetChanged();
//            }
            //推荐商品
            tuijianGoodsList.clear();
            tuijianGoodsList.addAll(homeIndexBean.getGoods_cuxiao4());
            goodsAdapter.notifyDataSetChanged();
            //抢购
            if (ObjectUtils.isNotEmpty(homeIndexBean.getTime()) && CollectionUtils.isNotEmpty(homeIndexBean.getTime().getList())) {
                qiangGouLayout.setVisibility(View.GONE);
                tvChaodiTips.setText(homeIndexBean.getTime().getTitle());
                String[] s = homeIndexBean.getTime().getStart_time().split(" ");
                String[] ss = s[1].split(":");
                tvQiangGouTime.setText(ss[0] + "点场");

                long startMills = TimeUtils.string2Millis(homeIndexBean.getTime().getStart_time(), "yyyy-MM-dd HH:mm:ss");
                long endMills = TimeUtils.string2Millis(homeIndexBean.getTime().getEnd_time(), "yyyy-MM-dd HH:mm:ss");
                long nowMills = TimeUtils.getNowMills();

                LogUtils.e("开始时间 = " + startMills);
                LogUtils.e("结束时间 = " + startMills);
                LogUtils.e("现在时间 = " + nowMills);
                if (nowMills < startMills) {
                    tvCountDown.setText("活动未开始");
                } else if (nowMills > endMills) {
                    tvCountDown.setText("活动已结束");
                } else {
                    if (downTimer != null) {
                        downTimer.cancel();
                        downTimer = null;
                    }
                    long timeSpan = endMills - nowMills;
//                    TimeUtils.getM
                    LogUtils.e("时间差1 = " + timeSpan);
                    downTimer = new CountDownTimer(timeSpan, 1000) {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onTick(long millisUntilFinished) {
                            long ss = millisUntilFinished / 1000;
                            long h = ss / 3600;
                            long m = ss % 3600 / 60;
                            long s = ss % 3600 % 60;

                            String H;
                            if (h < 10) {
                                H = "0" + h;
                            } else {
                                H = String.valueOf(h);
                            }
                            String M;
                            if (m < 10) {
                                M = "0" + m;
                            } else {
                                M = String.valueOf(m);
                            }
                            String S;
                            if (s < 10) {
                                S = "0" + s;
                            } else {
                                S = String.valueOf(s);
                            }

                            tvCountDown.setText(H + ":" + M + ":" + S);
                        }

                        @Override
                        public void onFinish() {
                            tvCountDown.setText("活动已结束");
                        }
                    };
                    downTimer.start();
                }
                qianggouList.clear();
                qianggouList.addAll(homeIndexBean.getTime().getList());
                qianggouAdapter.notifyDataSetChanged();
            } else {
                qiangGouLayout.setVisibility(View.GONE);
            }
        });
    }

    public void initActiveView(List<HomeIndexBean.GoodsCuxiaoBean> list) {
        if (list.size() >= 1 && list.get(0) != null && list.get(0).getGoods() != null) {


            activeName1.setText(list.get(0).getTitle());
            activeSubName1.setText(list.get(0).getSubtitle());

            if (list.get(0).getGoods().size() > 1 && list.get(0).getGoods().get(1) != null) {
                Glide.with(getContext())
                        .load(list.get(0).getGoods().get(1).getUrl())
                        .placeholder(R.mipmap.img_default)
                        .into(ivImage2);
//                tv_jifen2.setText(list.get(0).getGoods().get(1).getCx_points() + "积分");
                tv_price2.setText(list.get(0).getGoods().get(1).getSaleprice());
                tv_price2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                tv_priceType2.setText(list.get(0).getGoods().get(1).getCx_points() + "积分");
                switch (list.get(0).getGoods().get(1).getPrice_type()) {
                    case "0":
                        tv_jifen2.setText("运费￥" + list.get(0).getGoods().get(1).getCx_postage());
                        break;
                    case "1":
                        tv_jifen2.setText("运费￥" + list.get(0).getGoods().get(1).getCx_postage());

                        break;
                    case "2":

                        tv_jifen2.setText("￥" + list.get(0).getGoods().get(1).getCx_price());


                        break;
                    case "3":
                        tv_jifen2.setText("￥" + list.get(0).getGoods().get(1).getCx_price());

                        break;
                    default:
                        tv_jifen2.setText("￥" + list.get(0).getGoods().get(1).getCx_price());


                }
            }
            Glide.with(getContext())
                    .load(list.get(0).getGoods().get(0).getUrl())
                    .placeholder(R.mipmap.img_default)
                    .into(ivImage1);

            tv_jifen1.setText(list.get(0).getGoods().get(0).getCx_points() + "积分");
            tv_price.setText(list.get(0).getGoods().get(0).getSaleprice());
            tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);


            switch (list.get(0).getGoods().get(0).getPrice_type()) {
                case "0":
                    tv_priceType1.setText("运费￥" + list.get(0).getGoods().get(0).getCx_postage());
                    break;
                case "1":
                    tv_priceType1.setText("运费￥" + list.get(0).getGoods().get(0).getCx_postage());

                    break;
                case "2":

                    tv_priceType1.setText("￥" + list.get(0).getGoods().get(0).getCx_price());

                    break;
                case "3":
                    tv_priceType1.setText("￥" + list.get(0).getGoods().get(0).getCx_price());

                    break;
                default:
                    tv_priceType1.setText("￥" + list.get(0).getGoods().get(0).getCx_price());


            }

        }

        if (list.size() >= 2 && list.get(1) != null && list.get(1).getGoods() != null) {


            two_tvTips.setText(list.get(1).getTitle());
            two_tvTips2.setText(list.get(1).getSubtitle());
            if (list.get(1).getGoods().size() > 1 && list.get(1).getGoods().get(1) != null) {
                Glide.with(getContext())
                        .load(list.get(1).getGoods().get(1).getUrl())
                        .placeholder(R.mipmap.img_default)
                        .into(two_ivImage2);
//                two_jifen2.setText(list.get(1).getGoods().get(1).getCx_points() + "积分");
                two_tv_price2.setText(list.get(1).getGoods().get(1).getSaleprice());
                two_tv_price2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                two_tv_priceType2.setText(list.get(1).getGoods().get(1).getCx_points() + "积分");
                switch (list.get(1).getGoods().get(1).getPrice_type()) {
                    case "0":
                        two_jifen2.setText("运费￥" + list.get(1).getGoods().get(1).getCx_postage());
                        break;
                    case "1":
                        two_jifen2.setText("运费￥" + list.get(1).getGoods().get(1).getCx_postage());

                        break;
                    case "2":

                        two_jifen2.setText("￥" + list.get(1).getGoods().get(1).getCx_price());


                        break;
                    case "3":
                        two_jifen2.setText("￥" + list.get(1).getGoods().get(1).getCx_price());

                        break;
                    default:
                        two_jifen2.setText("￥" + list.get(1).getGoods().get(1).getCx_price());


                }

            }

            Glide.with(getContext())
                    .load(list.get(1).getGoods().get(0).getUrl())
                    .placeholder(R.mipmap.img_default)
                    .into(two_ivImage1);

            two_tv_jifen.setText(list.get(1).getGoods().get(0).getCx_points() + "积分");
            two_tv_price.setText(list.get(1).getGoods().get(0).getSaleprice());
            two_tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

            switch (list.get(1).getGoods().get(0).getPrice_type()) {
                case "0":
                    two_tv_priceType.setText("运费￥" + list.get(1).getGoods().get(0).getCx_postage());
                    break;
                case "1":
                    two_tv_priceType.setText("运费￥" + list.get(1).getGoods().get(0).getCx_postage());

                    break;
                case "2":

                    two_tv_priceType.setText("￥" + list.get(1).getGoods().get(0).getCx_price());


                    break;
                case "3":
                    two_tv_priceType.setText("￥" + list.get(1).getGoods().get(0).getCx_price());

                    break;
                default:
                    two_tv_priceType.setText("￥" + list.get(1).getGoods().get(0).getCx_price());


            }
        }

        if (list.size() >= 3 && list.get(2) != null && list.get(2).getGoods() != null) {


            three_tvTips.setText(list.get(2).getTitle());
            three_tvTips2.setText(list.get(2).getSubtitle());

            if (list.get(2).getGoods().size() > 1 && list.get(2).getGoods().get(1) != null) {
                Glide.with(getContext())
                        .load(list.get(2).getGoods().get(1).getUrl())
                        .placeholder(R.mipmap.img_default)
                        .into(three_ivImage2);
//                three_jifen2.setText(list.get(2).getGoods().get(1).getCx_points() + "积分");
                three_tv_price2.setText(list.get(2).getGoods().get(1).getSaleprice());
                three_tv_price2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                three_tv_priceType2.setText(list.get(2).getGoods().get(1).getCx_points() + "积分");

                switch (list.get(2).getGoods().get(1).getPrice_type()) {
                    case "0":
                        three_jifen2.setText("运费￥" + list.get(2).getGoods().get(1).getCx_postage());
                        break;
                    case "1":
                        three_jifen2.setText("运费￥" + list.get(2).getGoods().get(1).getCx_postage());

                        break;
                    case "2":

                        three_jifen2.setText("￥" + list.get(2).getGoods().get(1).getCx_price());


                        break;
                    case "3":
                        three_jifen2.setText("￥" + list.get(2).getGoods().get(1).getCx_price());

                        break;
                    default:
                        three_jifen2.setText("￥" + list.get(2).getGoods().get(1).getCx_price());


                }
            }
            Glide.with(getContext())
                    .load(list.get(2).getGoods().get(0).getUrl())
                    .placeholder(R.mipmap.img_default)
                    .into(three_ivImage1);

            three_tv_jifen.setText(list.get(2).getGoods().get(0).getCx_points() + "积分");
            three_tv_price.setText(list.get(2).getGoods().get(0).getSaleprice());
            three_tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);


            switch (list.get(2).getGoods().get(0).getPrice_type()) {
                case "0":
                    three_tv_priceType.setText("运费￥" + list.get(2).getGoods().get(0).getCx_postage());
                    break;
                case "1":
                    three_tv_priceType.setText("运费￥" + list.get(2).getGoods().get(0).getCx_postage());

                    break;
                case "2":

                    three_tv_priceType.setText("￥" + list.get(2).getGoods().get(0).getCx_price());


                    break;
                case "3":
                    three_tv_priceType.setText("￥" + list.get(2).getGoods().get(0).getCx_price());

                    break;
                default:
                    three_tv_priceType.setText("￥" + list.get(2).getGoods().get(0).getCx_price());


            }
        }

        if (list.size() >= 4 && list.get(3) != null && list.get(3).getGoods() != null) {


            four_tvTips.setText(list.get(3).getTitle());
            four_tvTips2.setText(list.get(3).getSubtitle());

            if (list.get(3).getGoods().size() > 1 && list.get(3).getGoods().get(1) != null) {
                Glide.with(getContext())
                        .load(list.get(3).getGoods().get(1).getUrl())
                        .placeholder(R.mipmap.img_default)
                        .into(four_ivImage2);
//                    four_jifen2.setText(list.get(3).getGoods().get(1).getCx_points() + "积分");
                four_tv_price2.setText(list.get(3).getGoods().get(1).getSaleprice());
                four_tv_price2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                four_tv_priceType2.setText(list.get(3).getGoods().get(1).getSaleprice());

                switch (list.get(3).getGoods().get(0).getPrice_type()) {
                    case "0":
                        four_jifen2.setText("运费￥" + list.get(3).getGoods().get(1).getCx_postage());
                        break;
                    case "1":
                        four_jifen2.setText("运费￥" + list.get(3).getGoods().get(1).getCx_postage());

                        break;
                    case "2":

                        four_jifen2.setText("￥" + list.get(3).getGoods().get(1).getCx_price());


                        break;
                    case "3":
                        four_jifen2.setText("￥" + list.get(3).getGoods().get(1).getCx_price());

                        break;
                    default:
                        four_jifen2.setText("￥" + list.get(3).getGoods().get(1).getCx_price());

                }

            }
            Glide.with(getContext())
                    .load(list.get(3).getGoods().get(0).getUrl())
                    .placeholder(R.mipmap.img_default)
                    .into(four_ivImage1);

            four_tv_jifen.setText(list.get(3).getGoods().get(0).getCx_points() + "积分");
            four_tv_price.setText(list.get(3).getGoods().get(0).getSaleprice());
            four_tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);


            four_tvTips2.setVisibility(View.GONE);
            ll_miaosha4.setVisibility(View.VISIBLE);

            switch (list.get(3).getGoods().get(0).getPrice_type()) {
                case "0":
                    four_tv_priceType.setText("运费￥" + list.get(3).getGoods().get(0).getCx_postage());
                    break;
                case "1":
                    four_tv_priceType.setText("运费￥" + list.get(3).getGoods().get(0).getCx_postage());

                    break;
                case "2":

                    four_tv_priceType.setText("￥" + list.get(3).getGoods().get(0).getCx_price());


                    break;
                case "3":
                    four_tv_priceType.setText("￥" + list.get(3).getGoods().get(0).getCx_price());

                    break;
                default:
                    four_tv_priceType.setText("￥" + list.get(3).getGoods().get(0).getCx_price());

            }

            long startMills2 = TimeUtils.string2Millis(list.get(3).getStart_time(), "yyyy-MM-dd HH:mm:ss");
            long endMills2 = TimeUtils.string2Millis(list.get(3).getEnd_time(), "yyyy-MM-dd HH:mm:ss");
            long nowMills = TimeUtils.getNowMills();

            LogUtils.e("开始时间 = " + startMills2);
            LogUtils.e("结束时间 = " + endMills2);
            LogUtils.e("现在时间 = " + nowMills);
            if (nowMills < startMills2) {
//            tvTips2.setText("活动未开始");
            } else if (nowMills > endMills2) {
//            tvTips2.setText("活动已结束");
            } else {
                if (downTimer != null) {
                    downTimer.cancel();
                    downTimer = null;
                }
                long timeSpan = endMills2 - nowMills;
//                    TimeUtils.getM
                LogUtils.e("时间差1 = " + timeSpan);
                downTimer = new CountDownTimer(timeSpan, 1000) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long ss = millisUntilFinished / 1000;
                        long h = ss / 3600;
                        long m = ss % 3600 / 60;
                        long s = ss % 3600 % 60;

                        String H;
                        if (h < 10) {
                            H = "0" + h;
                        } else {
                            H = String.valueOf(h);
                        }
                        String M;
                        if (m < 10) {
                            M = "0" + m;
                        } else {
                            M = String.valueOf(m);
                        }
                        String S;
                        if (s < 10) {
                            S = "0" + s;
                        } else {
                            S = String.valueOf(s);
                        }

//                    tvTips2.setText(H + ":" + M + ":" + S);

                        tv_hour.setText(H);
                        tv_min.setText(M);
                        tv_sec.setText(S);
                    }

                    @Override
                    public void onFinish() {
//                    tvTips2.setText("活动已结束");
                        miaoshaView.setAlpha(0.6f);
                        miaoshaView.setClickable(false);
                    }
                };
                downTimer.start();
            }
        } else {
            miaoshaView.setAlpha(0.6f);
            miaoshaView.setClickable(false);
        }


        getActivity().findViewById(R.id.active1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() >= 1) {
                    HomeToGoodsListActivity.startHomeToGoodsListActivity(getContext(), list.get(0).getId(), list.get(0).getTitle(), 5);

                }
            }
        });
        getActivity().findViewById(R.id.active2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() >= 2) {
                    HomeToGoodsListActivity.startHomeToGoodsListActivity(getContext(), list.get(1).getId(), list.get(0).getTitle(), 5);
                }
            }
        });
        getActivity().findViewById(R.id.active3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() >= 3) {
                    HomeToGoodsListActivity.startHomeToGoodsListActivity(getContext(), list.get(2).getId(), list.get(0).getTitle(), 5);

                }
            }
        });
        getActivity().findViewById(R.id.active4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() >= 4 && list.get(3).getGoods() != null) {
                    HomeToGoodsListActivity.startHomeToGoodsListActivity(getContext(), list.get(3).getId(), list.get(0).getTitle(), 5);
                }
            }
        });


    }

    /**
     * 获取未读消息数
     */
    private void getNoreadMessage() {

        HttpsUtil.getInstance(getContext()).getNoreadMessage(object -> {
            try {
                JSONObject jsonObject = new JSONObject(GsonUtils.toJson(object));
                int totalMessageToday = jsonObject.getInt("totalMessageToday");
                if (totalMessageToday > 0) {
                    tvMessageNum.setVisibility(View.GONE);
                    tvMessageNum.setText(String.valueOf(totalMessageToday));
                } else {
                    tvMessageNum.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
        if (downTimer != null) {
            downTimer.cancel();
            downTimer = null;
        }
        mLocationClient.stop();

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            String adcode = location.getAdCode();    //获取adcode
            LogUtils.e("定位结果 = " + GsonUtils.toJson(location));
            double lng = location.getLongitude();
            double lat = location.getLatitude();
            if (location.hasAddr()) {
                Constants.LNG = String.valueOf(lng);
                Constants.LAT = String.valueOf(lat);
                tvAddress.setText(city);
                tvAddress.setOnClickListener(v -> {
                    LifeCircleMapActivity.startLifeCircleMapActivity(getContext(), city);
                });
            } else {
                tvAddress.setText("定位失败");
            }
        }
    }
}