package com.cdxz.liudake.ui.life_circle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.pop.PopSuggestion;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.lxj.xpopup.XPopup;

import org.json.JSONObject;

import butterknife.BindView;

public class LifeCircleMapActivity extends BaseActivity {

    @BindView(R.id.searchLayout)
    LinearLayout searchLayout;

    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.tvSearch)
    TextView tvSearch;

    @BindView(R.id.bmapView)
    MapView bmapView;

    private SuggestionSearch mSuggestionSearch;
    private LocationClient mLocationClient;
    //百度地图
    private BaiduMap baiduMap;
    private PopSuggestion popupView;

    public static void startLifeCircleMapActivity(Context context, String city) {
        Intent intent = new Intent(context, LifeCircleMapActivity.class);
        intent.putExtra("city", city);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_life_circle_map;
    }

    @Override
    protected void initViews() {
        setTitleText("选择地址");
    }

    @Override
    protected void initDatas() {
        Constants.CITY = getIntent().getExtras().getString("city", "北京市");
        LogUtils.e("city = " + Constants.CITY);
        //
        baiduMap = bmapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通图
        baiduMap.setTrafficEnabled(false);
        //关闭缩放按钮
        bmapView.showZoomControls(true);
        baiduMap.setMyLocationEnabled(true);
        //定位初始化
        mLocationClient = new LocationClient(this);
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        //设置locationClientOption
        mLocationClient.setLocOption(option);
        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();
        //
        mSuggestionSearch = SuggestionSearch.newInstance();
        SuggestionSearchOption searchOption = new SuggestionSearchOption();
        searchOption.citylimit(false);
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
    }

    @Override
    protected void initListener() {
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = etSearch.getText().toString();
                if (StringUtils.isEmpty(keyword)) {
                    return;
                }
                mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                        .city(Constants.CITY)
                        .keyword(keyword));
            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = etSearch.getText().toString();
                    if (!StringUtils.isEmpty(keyword)) {
                        mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                                .city(Constants.CITY)
                                .keyword(keyword));
                    }
                }
                return false;
            }
        });
    }

    OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            //处理sug检索结果
            if (suggestionResult.error == SearchResult.ERRORNO.NO_ERROR) {
                popupView = (PopSuggestion) new XPopup.Builder(LifeCircleMapActivity.this)
                        .atView(searchLayout)
                        .asCustom(new PopSuggestion(LifeCircleMapActivity.this, suggestionResult.getAllSuggestions()));
                popupView.show();
            } else {
                ToastUtils.showShort("没有搜索到相关内容");
            }
        }
    };

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            LogUtils.e("定位信息 = " + GsonUtils.toJson(location));
            //mapView 销毁后不在处理新接收的位置
            if (location == null || bmapView == null) {
                return;
            }
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(location.getDirection()).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            baiduMap.setMyLocationData(locData);
            setPosition2Center(baiduMap, location, true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        bmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        bmapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (bmapView != null) {
            bmapView.onDestroy();
            bmapView = null;
        }
        mLocationClient.stop();
        if (baiduMap != null) {
            baiduMap.setMyLocationEnabled(false);
        }
    }

    /**
     * 设置中心点和添加marker
     *
     * @param map
     * @param bdLocation
     * @param isShowLoc
     */
    public void setPosition2Center(BaiduMap map, BDLocation bdLocation, Boolean isShowLoc) {
//        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(bdLocation.getRadius())
//                .direction(bdLocation.getRadius()).latitude(bdLocation.getLatitude())
//                .longitude(bdLocation.getLongitude()).build();
        Constants.CITY = bdLocation.getCity();
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(bdLocation.getDirection()).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();
        map.setMyLocationData(locData);

        if (isShowLoc) {
            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
//            builder.target(ll).zoom(18.0f);
            builder.target(ll);
            map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }

}