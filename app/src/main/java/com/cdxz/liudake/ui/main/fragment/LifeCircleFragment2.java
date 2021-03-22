package com.cdxz.liudake.ui.main.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.BasePagerAdapter;
import com.cdxz.liudake.adapter.shop_mall.HomeBannerAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.BannerBean;
import com.cdxz.liudake.bean.Bus.GetStoreIdBean;
import com.cdxz.liudake.bean.Bus.PopSuggestionBean;
import com.cdxz.liudake.bean.LifeCircleCatBean;
import com.cdxz.liudake.ui.ScanQRCodeActivity;
import com.cdxz.liudake.ui.base.BaseFragment;
import com.cdxz.liudake.ui.life_circle.LifeCircleChildFragment;
import com.cdxz.liudake.ui.life_circle.LifeCircleMapActivity;
import com.cdxz.liudake.ui.life_circle.StoreListActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.DrawableTextView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//import com.uuzuche.lib_zxing.activity.CaptureActivity;
//import com.uuzuche.lib_zxing.activity.CaptureFragment;
//import com.uuzuche.lib_zxing.activity.CodeUtils;
//import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class LifeCircleFragment2 extends BaseFragment {

    @BindView(R.id.tvAddress)
    DrawableTextView tvAddress;

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPagerList)
    ViewPager2 viewPagerList;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    private final List<Fragment> fragmentList = new ArrayList<>();

    public LifeCircleFragment2() {
        // Required empty public constructor
    }

//    // TODO: Rename and change types and number of parameters
//    public static LifeCircleFragment newInstance() {
//        LifeCircleFragment fragment = new LifeCircleFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }


    @Override
    protected void lazyLoadData() {
    }

    @Override
    protected int getResource() {
        return R.layout.fragment_shop_mall_new2;
    }

    @Override
    protected void initView() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initData() {
        BusUtils.register(this);
        //
        mLocationClient = new LocationClient(getContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        //
        getBannerList();
        getStoreCat(true);
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
        getActivity().findViewById(R.id.tvSearch).setOnClickListener(v -> {
            StoreListActivity.startStoreListActivity(getContext());
//            SearchActivity.startSearchActivity(getContext(), null);
        });
        getActivity().findViewById(R.id.dtv_eat).setOnClickListener(v -> {
            tabLayout.getTabAt(1).select();

        });
        getActivity().findViewById(R.id.dtv_travel).setOnClickListener(v -> {
            tabLayout.getTabAt(4).select();

        });
        getActivity().findViewById(R.id.dtv_delicious).setOnClickListener(v -> {
            tabLayout.getTabAt(2).select();

        });
        getActivity().findViewById(R.id.dtv_accommodation).setOnClickListener(v -> {
            tabLayout.getTabAt(3).select();

        });

    }

    private void getStoreCat(boolean isCat) {
        HttpsUtil.getInstance(getContext()).nearShopCat(1, object -> {
            List<LifeCircleCatBean> catBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), LifeCircleCatBean.class);
            LifeCircleCatBean bean = new LifeCircleCatBean();
            bean.setId("");
            bean.setName("推荐");
            catBeanList.add(0, bean);
            //
            parseData(catBeanList);
            if (isCat) {
                for (int i = 0; i < catBeanList.size(); i++) {
                    fragmentList.add(LifeCircleChildFragment.newInstance(catBeanList.get(i).getId()));
                }
                viewPagerList.setAdapter(new BasePagerAdapter(getActivity(), fragmentList));
                TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPagerList, (tab, position) -> {
                    tab.setText(catBeanList.get(position).getName());
                });
                mediator.attach();
            } else {

            }
            getActivity().findViewById(R.id.tvShaixuan).setOnClickListener(v -> {

                OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (options1, options2, options3, v1) -> {
                    BusUtils.post(BusTag.GET_STORE_ID,
                            new GetStoreIdBean(catBeanList.get(options1).getId(),
                                    childBeanList.get(options1).get(options2).getId())
                    );
                })
                        .setCancelColor(ContextCompat.getColor(getContext(), R.color.appColor))
                        .setSubmitColor(ContextCompat.getColor(getContext(), R.color.appColor))
                        .build();
                pvOptions.setPicker(catBeanList, childBeanList);
                pvOptions.show();
            });
        });
    }

    /**
     * banner数据
     */
    private void getBannerList() {
        HttpsUtil.getInstance(getContext()).positionList(4, Constants.LNG, Constants.LAT, object -> {
            List<BannerBean> bannerBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), BannerBean.class);
            banner.setAdapter(new HomeBannerAdapter(bannerBeanList), true)
                    .setIndicator(new CircleIndicator(getContext()));
        });
    }

    List<List<LifeCircleCatBean.ChildBean>> childBeanList = new ArrayList<>();

    private void parseData(List<LifeCircleCatBean> catBeanList) {
        for (LifeCircleCatBean oneBean : catBeanList) {
            List<LifeCircleCatBean.ChildBean> childrenBeans = new ArrayList<>();
            if (CollectionUtils.isEmpty(oneBean.getChild())) {
                LifeCircleCatBean.ChildBean emptyBean = new LifeCircleCatBean.ChildBean();
                emptyBean.setId("");
                emptyBean.setName("暂无数据");
                childrenBeans.add(emptyBean);
            } else {
                for (LifeCircleCatBean.ChildBean twoBean : oneBean.getChild()) {
                    childrenBeans.add(twoBean);
                }
            }
            childBeanList.add(childrenBeans);
        }
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
                tvAddress.setText(city + district + street);
                tvAddress.setOnClickListener(v -> {
                    LifeCircleMapActivity.startLifeCircleMapActivity(getContext(), city);
                });
            } else {
                tvAddress.setText("定位失败");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
        mLocationClient.stop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 0) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    //http://47.108.198.70/Application/Common/zhifu_page/zhifu_start.php?wap_shopid=8889
//                    if (result.contains("zhifu_start.php?wap_shopid=")) {
//                        String[] s = result.split("wap_shopid=");
//                        StorePayActivity.startStorePayActivity(getContext(), s[1]);
//                    } else {
//                        ToastUtils.showShort("二维码扫描失败或不支持该类型");
//                    }
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    ToastUtils.showShort("二维码扫描失败或不支持该类型");
//                }
//            }
//        }
    }
}