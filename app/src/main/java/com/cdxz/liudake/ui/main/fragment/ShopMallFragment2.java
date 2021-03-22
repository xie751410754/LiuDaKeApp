package com.cdxz.liudake.ui.main.fragment;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.ActivityAdapter;
import com.cdxz.liudake.adapter.shop_mall.ClassAdapter;
import com.cdxz.liudake.adapter.shop_mall.HomeBannerAdapter;
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
import com.cdxz.liudake.view.roundedImageView.RoundedImageView;
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
    private List<HomeIndexBean.GoodsCuxiao1Bean> menu2List = new ArrayList<>();
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
        GridLayoutManager goodsLayout = new GridLayoutManager(getContext(), 2);
        recyclerTuijianGoods.setLayoutManager(goodsLayout);
        recyclerTuijianGoods.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(15), false));
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
        mLocationClient.start();

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
            menu2List.addAll(homeIndexBean.getGoods_cuxiao1());
            menu2Adapter.notifyDataSetChanged();
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

    /**
     * 获取未读消息数
     */
    private void getNoreadMessage() {
        HttpsUtil.getInstance(getContext()).getNoreadMessage(object -> {
            try {
                JSONObject jsonObject = new JSONObject(GsonUtils.toJson(object));
                int totalMessageToday = jsonObject.getInt("totalMessageToday");
                if (totalMessageToday > 0) {
                    tvMessageNum.setVisibility(View.VISIBLE);
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