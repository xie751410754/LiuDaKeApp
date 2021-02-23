package com.cdxz.liudake.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.databinding.ActivityMapBinding;
import com.cdxz.liudake.ui.base.BaseTitleActivity;
import com.cdxz.liudake.util.SoftKeyBoardListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapActivity extends BaseTitleActivity<ActivityMapBinding> {

    private MapHelper mapHelper;
    private MapHelper.Picker picker;
    private MapHelper.LatLng beginLatLng;
    private MapHelper.LatLng currentLatLng;
    private NearPositionAdapter nearPositionAdapter;
    private List<MapHelper.Place> placesSeach = new ArrayList<>();
    private List<MapHelper.Place> seachPlace = new ArrayList<>();
    private Map<String, MapHelper.Place> placeMap = new HashMap<>();
    private String city = "";
    SuggestionSearch mSuggestionSearch;
    private boolean showTitle = true;
    private NearPositionAdapter.OnRecyclerItemClickListener itemClickListener = new NearPositionAdapter.OnRecyclerItemClickListener() {
        @Override
        public void onItemClick(int Position, MapHelper.Place dataBean) {
            placeMap.clear();
            placeMap.put("place", dataBean);
            picker.moveMap(dataBean.getLatLng());



            Log.d("MapActivity", "onItemClick: "+dataBean.getAddress());
        }
    };

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_map;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        initToolbar(binding.toolbar);


        nearPositionAdapter = new NearPositionAdapter(this);
        nearPositionAdapter.setRecyclerItemClickListener(itemClickListener);

        findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (placeMap.get("place") == null){
                    ToastUtils.showShort("请选择地点");
                    return;
                }

                MapHelper.Place dataBean = placeMap.get("place");

                Intent intent = new Intent();
                intent.putExtra("address",dataBean.getAddress());
                intent.putExtra("lng",dataBean.getLatLng().getLongitude()+"");
                intent.putExtra("lat",dataBean.getLatLng().getLatitude()+"");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        initMap();
        initEvent();

    }

    private void initMap() {
        mapHelper = MapHelper.getInstance();
        mSuggestionSearch = SuggestionSearch.newInstance();
        picker = mapHelper.getPicker(this);
        getLifecycle().addObserver(picker);

        picker.attack(binding.mapViewContainer, () -> {
            // 初始化底部周边相关动画，
            // 中心打上图标，
            picker.addCenterMarker(R.drawable.ic_position, "pos");
            mapHelper.requestLatLng(new MapHelper.OnSuccessListener<MapHelper.LatLng>() {
                @Override
                public void onSuccess(MapHelper.LatLng latLng) {
                    // 记录开始时定位的位置，用来点击按钮跳回来，
                    beginLatLng = latLng;
                    BaiduMapHelper baiduMapHelper = (BaiduMapHelper)MapHelper.getInstance(MapHelper.MapType.BAIDU);
                    binding.tvPostion.setText(baiduMapHelper.currentAddress);
                    picker.moveMap(latLng);
                    // 加载周边位置信息，
                    // 记录当前位置也在这个方法里，
                    MapActivity.this.loadMapDatas(latLng);
                }
            }, new MapHelper.OnErrorListener() {
                @Override
                public void onError(Throwable t) {
                    ToastUtils.showShort("定位失败"+t.getMessage());
                    // 总有个默认的经纬度，拿出来，
                    beginLatLng = picker.currentLatLng();
                    picker.moveMap(beginLatLng);
                    MapActivity.this.loadMapDatas(beginLatLng);
                }
            });
        });

        picker.setOnMapStatusChangeListener(new MapHelper.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapHelper.MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapHelper.MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeFinish(MapHelper.MapStatus mapStatus) {
                loadMapDatas(mapStatus.target);
            }
        });
        mSuggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                Log.d("地点",GsonUtils.toJson(suggestionResult.getAllSuggestions()));
                if (suggestionResult.getAllSuggestions() != null&& suggestionResult.getAllSuggestions().size()>0){

                    for (SuggestionResult.SuggestionInfo info :suggestionResult.getAllSuggestions()){

                        if (info.getPt()!=null){
                            seachPlace.add(new MapHelper.Place(info.getKey(),info.address,new MapHelper.LatLng(info.getPt().latitude,info.getPt().longitude)));
                        }



                    }
                    nearPositionAdapter.setData(seachPlace);
                }else {
                    seachPlace.clear();

                    loadMapDatas(currentLatLng);

//                    seachPlace.addAll(placesSeach);
                }




//                nearPositionAdapter.setData(seachPlace);
            }
        });
    }

    private void initEvent() {
        binding.ivLocation.setOnClickListener(v -> {
            currentLatLng = beginLatLng;
            picker.moveMap(beginLatLng);
            loadMapDatas(currentLatLng);
            binding.ceMapPosition.setText("");
        });

        binding.ceMapPosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                        .citylimit(false)
                        .city(mapHelper.currentCity())
                        .keyword(s.toString()));

            }

            @Override
            public void afterTextChanged(Editable s) {


//                if (TextUtils.isEmpty(s.toString()))
//                    loadMapDatas(currentLatLng);
//                for (int i = 0; i < placesSeach.size(); i++) {
//                    if (placesSeach.get(i).getName().contains(s.toString())) {
//                        seachPlace.add(placesSeach.get(i));
//                    }
//                }


            }
        });

        findViewById(R.id.rl_map_position).setOnClickListener(v -> {
            // 点击空白区域隐藏软键盘
            InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(findViewById(R.id.rl_map_position).getWindowToken(), 0); //强制隐藏键盘
            }
        });

//        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
//
//            @Override
//            public void keyBoardShow(int height) {
//                startTranslateAnim(false);
//                findViewById(R.id.tv_keyboard).setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void keyBoardHide(int height) {
//                startTranslateAnim(true);
//                findViewById(R.id.tv_keyboard).setVisibility(View.GONE);
//            }
//        });
    }

    private void loadMapDatas(MapHelper.LatLng latLng) {
        currentLatLng = latLng;

        binding.ivLocation.setVisibility(View.VISIBLE);

        mapHelper.requestPlaceList(latLng, places -> {
            nearPositionAdapter.setData(places);
            placesSeach.clear();
            placesSeach.addAll(places);
            LinearLayoutManager layoutManager = new LinearLayoutManager(MapActivity.this);
            binding.rvMapPosition.setLayoutManager(layoutManager);
            binding.rvMapPosition.setAdapter(nearPositionAdapter);
        }, t -> ToastUtils.showShort("获取周边地址失败"));
    }

    public void startTranslateAnim(boolean show) {
        if (showTitle == show) {
            return;
        }
        showTitle = show;
        float fromy = -(ScreenUtils.getScreenHeight() / 3);
        float toy = 0;

        if (!show) {
            fromy = 0;
            toy = -(ScreenUtils.getScreenHeight() / 3);
        }

        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.llMap, "translationY", fromy, toy);
        animator.setDuration(300);
        animator.start();
    }

    public void cancelKeyBoard(View view) {
        // 点击空白区域隐藏软键盘
        InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(findViewById(R.id.tv_keyboard).getWindowToken(), 0); //强制隐藏键盘
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSuggestionSearch!=null){
            mSuggestionSearch.destroy();
        }
    }
}
