package com.cdxz.liudake.ui;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.store_manager.MerchantAdapter;
import com.cdxz.liudake.api.HttpsCallback;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.bean.AddressRegionBean;
import com.cdxz.liudake.bean.StoreBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LookUpActivity extends BaseActivity {
    @BindView(R.id.ivTitleBack)
    ImageView ivTitleBack;
    @BindView(R.id.tvTitleText)
    TextView tvTitleText;
    @BindView(R.id.tvTitleRight)
    TextView tvTitleRight;
    @BindView(R.id.titleRootLayout)
    ConstraintLayout titleRootLayout;
    @BindView(R.id.tv_region)
    TextView tvRegion;
    @BindView(R.id.iv_region)
    ImageView ivRegion;
    @BindView(R.id.ll_region)
    LinearLayout llRegion;
    @BindView(R.id.tv_cat)
    TextView tvCat;
    @BindView(R.id.iv_cat)
    ImageView ivCat;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.tv_filter)
    TextView tvFilter;
    @BindView(R.id.iv_filter)
    ImageView ivFilter;
    @BindView(R.id.ll_filter)
    LinearLayout llFilter;
    @BindView(R.id.ll_filter_p)
    LinearLayout llFilterP;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.sml)
    SmartRefreshLayout sml;
    @BindView(R.id.divider_top)
    View divederTop;

    int page = 1;
    int pageSize = 10;
    int region = 0;

    ArrayList<StoreBean> datas = new ArrayList<>();
    MerchantAdapter merchantAdapter;

    ArrayList<AddressRegionBean> provinceData = new ArrayList<>();


    private int provincePosition;
    private int cityPosition;
    private int regionPosition;


    private RegionPopu provincePopu,cityPopu,regionPoPu;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_lookup;
    }

    @OnClick({R.id.ll_region,R.id.ll_type,R.id.ll_filter})
    public void onchooseRegion(View view){

        switch (view.getId()){

            case R.id.ll_region:
                //省
                if(provincePopu==null){

                    provincePopu = new RegionPopu(this, provinceData, new RegionPopu.Callback() {
                        @Override
                        public void onChoose(int position, AddressRegionBean addressRegionBean) {
                            provincePopu.dismiss();
                            region = Integer.parseInt(addressRegionBean.getId());
                            provincePosition = position;
                            cityPosition=0;
                            regionPosition = 0;
                            tvRegion.setText(provincePosition==0?"省":addressRegionBean.getName());
                            tvCat.setText(cityPosition==0?"市":addressRegionBean.getName());
                            tvFilter.setText(regionPosition==0?"区":addressRegionBean.getName());
                            LogUtils.e("选择了-->"+addressRegionBean.toString());
                            datas.clear();

                            if(region==-1){
                                region = 0;
                            }

                            merchantAdapter.notifyDataSetChanged();
                            getData();
                        }
                    });

                }
                provincePopu.showPopwindow(divederTop);
                break;

            case R.id.ll_type:

                if(provincePosition==0){
                    Toast.makeText(context,"请先选择要查询的省份",Toast.LENGTH_SHORT).show();
                    return;
                }

                cityPopu = new RegionPopu(this, provinceData.get(provincePosition).getSub(), new RegionPopu.Callback() {
                    @Override
                    public void onChoose(int position, AddressRegionBean addressRegionBean) {
                        cityPopu.dismiss();
                        if(Integer.parseInt(addressRegionBean.getId())==-1){
                            region = Integer.parseInt(provinceData.get(provincePosition).getId());
                        }else{
                            region = Integer.parseInt(addressRegionBean.getId());
                        }
                        cityPosition = position;
                        regionPosition = 0;
                        tvCat.setText(cityPosition==0?"市":addressRegionBean.getName());
                        tvFilter.setText(regionPosition==0?"区":addressRegionBean.getName());
                        LogUtils.e("选择了-->"+addressRegionBean.toString());
                        datas.clear();
                        merchantAdapter.notifyDataSetChanged();


                        getData();
                    }
                });
                cityPopu.showPopwindow(divederTop);

                break;

            case R.id.ll_filter:
                if(cityPosition==0){
                    Toast.makeText(context,"请先选择要查询的城市",Toast.LENGTH_SHORT).show();
                    return;
                }
                regionPoPu = new RegionPopu(this, provinceData.get(provincePosition)
                        .getSub().get(cityPosition).getSub(), new RegionPopu.Callback() {
                    @Override
                    public void onChoose(int position, AddressRegionBean addressRegionBean) {
                        regionPoPu.dismiss();
                        if(Integer.parseInt(addressRegionBean.getId())==-1){
                            region = Integer.parseInt( provinceData.get(provincePosition)
                                    .getSub().get(cityPosition).getId());
                        }else{
                            region = Integer.parseInt(addressRegionBean.getId());
                        }
                        regionPosition = position;
                        tvFilter.setText(regionPosition==0?"区":addressRegionBean.getName());
                        LogUtils.e("选择了-->"+addressRegionBean.toString());
                        datas.clear();
                        merchantAdapter.notifyDataSetChanged();
                        getData();
                    }
                });
                regionPoPu.showPopwindow(divederTop);

                break;

        }



    }

    @Override
    protected void initViews() {

        tvTitleText.setText("运营中心");

        sml.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                page = 1;
                getData();
            }
        });


        sml.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getData();
            }
        });


        merchantAdapter = new MerchantAdapter(R.layout.item_merchant,datas);
        rv.setLayoutManager(new LinearLayoutManager(context));


        View empty = LayoutInflater.from(context).inflate(R.layout.public_no_data,null);
        merchantAdapter.setEmptyView(empty);

        rv.setAdapter(merchantAdapter);

        provinceData = new Gson().fromJson(getJson(context)
                ,new TypeToken<ArrayList<AddressRegionBean>>(){}.getType());

        provinceData = provinceData.get(0).getSub();

        getData();

    }


    private String getJson(Context context){
        AssetManager assetManager = context.getAssets();
        InputStreamReader inputStreamReader = null;
        String json = "";
        try {
            inputStreamReader = new InputStreamReader(assetManager.open("region_json.json"),"UTF-8");
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine())!=null){
                builder.append(line);
            }
            br.close();
            inputStreamReader.close();
            json = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }



    private void getData(){

        HttpsUtil.getInstance(context).getMerchant(region, page, pageSize, new HttpsCallback() {
            @Override
            public void onResult(Object object) {
                sml.finishRefresh();
                sml.finishLoadMore();

                if(object==null){
                    return;
                }


                String json = GsonUtils.toJson(object);
                try {
                    BaseBean<List<StoreBean>> baseBean = new Gson().
                            fromJson(json,new TypeToken<BaseBean<List<StoreBean>>>(){}.getType());
                    if(baseBean.getData()!=null){

                        if (pageSize>baseBean.getData().size()){
                            sml.finishLoadMoreWithNoMoreData();

                        }
                        if(page==1){
                            datas.clear();
                            datas.addAll(baseBean.getData());

                        }else{
                            datas.addAll(baseBean.getData());
                        }
                        merchantAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){


                }






            }
        });

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
