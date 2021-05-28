package com.cdxz.liudake.ui.shop_mall;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.GoodsClassChildAdapter;
import com.cdxz.liudake.adapter.shop_mall.GoodsClassNameAdapter;
import com.cdxz.liudake.adapter.shop_mall.HomeBannerAdapter;
import com.cdxz.liudake.adapter.shop_mall.JDCategoryAdapter;
import com.cdxz.liudake.adapter.shop_mall.JDGoodsClassChildAdapter;
import com.cdxz.liudake.adapter.shop_mall.JDGoodsClassNameAdapter;
import com.cdxz.liudake.adapter.shop_mall.JDProBannerAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.BannerBean;
import com.cdxz.liudake.bean.CategoryListBean;
import com.cdxz.liudake.bean.JDBannerProDto;
import com.cdxz.liudake.bean.JDCategoryDto;
import com.cdxz.liudake.bean.JDCategoryMenuDto;
import com.cdxz.liudake.bean.JDGoodsDto;
import com.cdxz.liudake.bean.JDSecondCategoryDto;
import com.cdxz.liudake.ui.SearchActivity;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.GridSpacingItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodsClassActivity extends BaseActivity {

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.recyclerClass)
    RecyclerView recyclerClass;

    @BindView(R.id.goodsView)
    LinearLayout goodsView;

    private String cateid;
    private GoodsClassNameAdapter classNameAdapter;
    private JDGoodsClassNameAdapter jdClassNameAdapter;
    private OkHttpClient okHttpClient;
    private List<JDCategoryDto.DataDTO> jdCategoryList = new ArrayList<>();


    public static void startGoodsClassActivity(Context context) {
        Intent intent = new Intent(context, GoodsClassActivity.class);
        context.startActivity(intent);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    break;
                case 2:
                    List<JDBannerProDto.DataDTO> jdGoodsDto = (List<JDBannerProDto.DataDTO>) msg.obj;
                    banner.setAdapter(new JDProBannerAdapter(jdGoodsDto), true)
                            .setIndicator(new CircleIndicator(context));
                    break;
                case 3:
                    break;
                case 4:
                    jdCategoryList.clear();
                    List<JDCategoryDto.DataDTO> jdCategorry = (List<JDCategoryDto.DataDTO>) msg.obj;
                    jdCategoryList.addAll(jdCategorry);
                    for (int i = 0; i < jdCategorry.size(); i++) {
                        if (i == 0) {//默认第一个选中
                            jdCategorry.get(i).setSelected(true);
                            getSecondCategoryList(jdCategorry.get(i).getCatId());
                        } else {
                            jdCategorry.get(i).setSelected(false);
                        }
                    }
                    jdClassNameAdapter.notifyDataSetChanged();

                    break;
                case 5:
                    break;
                case 6:
                    List<JDSecondCategoryDto.DataDTO> jdSecondCategorry = (List<JDSecondCategoryDto.DataDTO>) msg.obj;
                    refreshUI(jdSecondCategorry);

                    break;
            }
        }




    };




    private void getSecondCategoryList(String catid) {
        String url = "http://liudake.cn/api/pub/get?param=getCategorysecond&fid=" +catid;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showShort("数据获取失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();
                JDSecondCategoryDto baseBean = ParseUtils.parseJsonObject(responseBody, JDSecondCategoryDto.class);

                if (CollectionUtils.isEmpty(baseBean.getData())) {
                    Message message = new Message();
                    message.what = 5;
                    mHandler.sendMessage(message);

                } else {
                    Message message = new Message();
                    message.what = 6;
                    message.obj = baseBean.getData();
                    mHandler.sendMessage(message);
                }

            }
        });

    }
    @Override
    protected int getContentViewId() {
        return R.layout.activity_goods_class;
    }

    @Override
    protected void initViews() {
        okHttpClient = new OkHttpClient();


        recyclerClass.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initDatas() {
//        getBannerList();
        getJDBannerList();
//        categoryLists();
        getJDCategoryList();


        jdClassNameAdapter =new JDGoodsClassNameAdapter(jdCategoryList);
        recyclerClass.setAdapter(jdClassNameAdapter);

        jdClassNameAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (int i = 0; i < jdCategoryList.size(); i++) {
                if (i == position) {
                    jdCategoryList.get(i).setSelected(true);
                    getSecondCategoryList(jdCategoryList.get(i).getCatId());
                } else {
                    jdCategoryList.get(i).setSelected(false);
                }
            }
            jdClassNameAdapter.notifyDataSetChanged();
        });
    }

    private void getJDBannerList() {
        String url = "http://liudake.cn/api/pub/get?param=getbanner&tpe=1" ;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showShort("数据获取失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();
                JDBannerProDto baseBean = ParseUtils.parseJsonObject(responseBody, JDBannerProDto.class);

                if (CollectionUtils.isEmpty(baseBean.getData())) {
                    Message message = new Message();
                    message.what = 1;
                    mHandler.sendMessage(message);

                } else {
                    Message message = new Message();
                    message.what = 2;
                    message.obj = baseBean.getData();
                    mHandler.sendMessage(message);
                }

            }
        });
    }

    private void getJDCategoryList() {

        String url = "http://liudake.cn/api/pub/get" + "?param=" + "getCategoryfirst" ;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showShort("数据获取失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();
                JDCategoryDto baseBean = ParseUtils.parseJsonObject(responseBody, JDCategoryDto.class);

                if (CollectionUtils.isEmpty(baseBean.getData())) {
                    Message message = new Message();
                    message.what = 3;
                    mHandler.sendMessage(message);

                } else {
                    Message message = new Message();
                    message.what = 4;
                    message.obj = baseBean.getData();
                    mHandler.sendMessage(message);
                }

            }
        });
    }

    @Override
    protected void initListener() {
        findViewById(R.id.tvSearch).setOnClickListener(v -> {
//            SearchActivity.startSearchActivity(this, null);
            JDGoodsListActivity.starJDGoodsListActivity(context,"","京东商品");

        });
    }

    private void categoryLists() {
        HttpsUtil.getInstance(this).categoryLists(object -> {
            List<CategoryListBean> categoryListBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), CategoryListBean.class);
            for (int i = 0; i < categoryListBeanList.size(); i++) {
                if (i == 0) {//默认第一个选中
                    categoryListBeanList.get(i).setSelect(true);
                    refresh(categoryListBeanList.get(0).getChild());
                } else {
                    categoryListBeanList.get(i).setSelect(false);
                }
            }
            classNameAdapter = new GoodsClassNameAdapter(categoryListBeanList);
            recyclerClass.setAdapter(classNameAdapter);
            classNameAdapter.setOnItemClickListener((adapter, view, position) -> {
                for (int i = 0; i < categoryListBeanList.size(); i++) {
                    if (i == position) {
                        categoryListBeanList.get(i).setSelect(true);
                        refresh(categoryListBeanList.get(i).getChild());
                    } else {
                        categoryListBeanList.get(i).setSelect(false);
                    }
                }
                classNameAdapter.notifyDataSetChanged();
            });
        });
    }

    private void refresh(List<CategoryListBean.ChildBean> childBeans) {
        goodsView.removeAllViews();
        for (int j = 0; j < childBeans.size(); j++) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_goods_class_child, null);

            TextView tvName = itemView.findViewById(R.id.tvName);
            RecyclerView recyclerGoods = itemView.findViewById(R.id.recyclerGoods);
            recyclerGoods.setLayoutManager(new GridLayoutManager(this, 3));
            recyclerGoods.addItemDecoration(new GridSpacingItemDecoration(3, SizeUtils.dp2px(10), false));

            tvName.setText(childBeans.get(j).getName());

            GoodsClassChildAdapter childAdapter = new GoodsClassChildAdapter(childBeans.get(j).getChild());
            recyclerGoods.setAdapter(childAdapter);

            goodsView.addView(itemView);
        }
    }

    private void refreshUI(List<JDSecondCategoryDto.DataDTO> jdSecondCategorry) {

        goodsView.removeAllViews();
        for (int j = 0; j < jdSecondCategorry.size(); j++) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_goods_class_child, null);

            TextView tvName = itemView.findViewById(R.id.tvName);
            RecyclerView recyclerGoods = itemView.findViewById(R.id.recyclerGoods);
            recyclerGoods.setLayoutManager(new GridLayoutManager(this, 3));
            recyclerGoods.addItemDecoration(new GridSpacingItemDecoration(3, SizeUtils.dp2px(10), false));

            tvName.setText(jdSecondCategorry.get(j).getName());

//            GoodsClassChildAdapter childAdapter = new GoodsClassChildAdapter(jdSecondCategorry.get(j).getChild());
            JDGoodsClassChildAdapter jdChildAdapter = new JDGoodsClassChildAdapter(jdSecondCategorry.get(j).getClassArr());
            recyclerGoods.setAdapter(jdChildAdapter);

            goodsView.addView(itemView);
        }
    }

    /**
     * banner数据
     */
    private void getBannerList() {
        HttpsUtil.getInstance(this).positionList(1, Constants.LNG, Constants.LAT, object -> {
            List<BannerBean> bannerBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), BannerBean.class);
            banner.setAdapter(new HomeBannerAdapter(bannerBeanList), true)
                    .setIndicator(new CircleIndicator(this));
        });
    }
}