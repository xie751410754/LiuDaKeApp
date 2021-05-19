package com.cdxz.liudake.ui.shop_mall;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.GoodsAdapter;
import com.cdxz.liudake.adapter.shop_mall.JDHomeGoodsAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.GoodsBean;
import com.cdxz.liudake.bean.JDGoodsDto;
import com.cdxz.liudake.pop.PopLifeCirclePrice;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.TtsManager;
import com.cdxz.liudake.view.DrawableTextView;
import com.cdxz.liudake.view.GridSpacingItemDecoration;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JDBaopinGoodsListActivity extends BaseActivity {
    public static final int HOME1 = 1;
    public static final int HOME2 = 2;

    @BindView(R.id.tvZongHe)
    DrawableTextView tvZongHe;

    @BindView(R.id.tvFuJin)
    DrawableTextView tvFuJin;

    @BindView(R.id.tvXiaoLiang)
    DrawableTextView tvXiaoLiang;

    @BindView(R.id.tvPrice)
    DrawableTextView tvPrice;

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @BindView(R.id.recyclerGoods)
    RecyclerView recyclerGoods;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.topLayout)
    RelativeLayout topLayout;
    private LoadingPopupView loadingPopupView;


    private JDHomeGoodsAdapter mAdapter;
    private int page =1 ;
    private int sort = 0;
    private List<JDGoodsDto.DataDTO> goodsBeanList = new ArrayList<>();
    private int homeType;
    private OkHttpClient okHttpClient;

    boolean firstEnter = false;

    String key;
    int pageSize = 30;
    int order;//1:综合推荐 2:价格3：销量
    int asc;//1:升序 2：降序




    public static void startHomeToGoodsListActivity(Context context) {
        Intent intent = new Intent(context, JDBaopinGoodsListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_goods_list;
    }


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    refresh.finishLoadMoreWithNoMoreData();
                    mAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    refresh.finishRefresh();
                    mAdapter.notifyDataSetChanged();

                    break;

                case 3:
                    refresh.finishLoadMore();
                    mAdapter.notifyDataSetChanged();

                    break;

            }
        }
    };

    @Override
    protected void initViews() {
        firstEnter = true;
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        fragmentList.add(new GoodsListFragment());
//        fragmentList.add(new GoodsListFragment());
//        fragmentList.add(new GoodsListFragment());
//        fragmentList.add(new GoodsListFragment());
//        fragmentList.add(new GoodsListFragment());
//        viewPager.setAdapter(new BasePagerAdapter(this, fragmentList));
//
//        TabLayoutMediator layoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
//                tab.setText(tabTitle[position])
//        );
//        layoutMediator.attach();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setTitleText("爆款秒杀");
        topLayout.setVisibility(View.VISIBLE);

        tvFuJin.setVisibility(View.GONE);
        GridLayoutManager goodsLayout = new GridLayoutManager(this, 2);
        recyclerGoods.setLayoutManager(goodsLayout);
        recyclerGoods.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(15), true));

        //
        tvZongHe.setTextColor(ContextCompat.getColor(this, R.color.appColor));
        tvFuJin.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
        tvXiaoLiang.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
        tvPrice.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
    }

    @Override
    protected void initDatas() {



        okHttpClient = new OkHttpClient();


        mAdapter = new JDHomeGoodsAdapter(goodsBeanList);
        mAdapter.setScore(true);
        recyclerGoods.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.public_no_data);

        goodsList("");
    }

    @Override
    protected void initListener() {

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                key = etSearch.getText().toString();
                String keyUrl="&Key=" + key ;
               goodsList(keyUrl);
            }
            return false;
        });

        findViewById(R.id.tvSearch).setOnClickListener(v -> {
            key = etSearch.getText().toString();
            String keyUrl="&Key=" + key ;
            goodsList(keyUrl);
        });
        tvZongHe.setOnClickListener(v -> {
            order = 1;
            tvZongHe.setTextColor(ContextCompat.getColor(this, R.color.appColor));
            tvFuJin.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            tvXiaoLiang.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            tvPrice.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            String saixuanUrl="&order=" + order;
            goodsList(saixuanUrl);
        });
        tvFuJin.setOnClickListener(v -> {
            sort = 5;
            tvZongHe.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            tvFuJin.setTextColor(ContextCompat.getColor(this, R.color.appColor));
            tvXiaoLiang.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            tvPrice.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
//            goodsList();
        });
        tvXiaoLiang.setOnClickListener(v -> {
            order = 3;
            tvZongHe.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            tvFuJin.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            tvXiaoLiang.setTextColor(ContextCompat.getColor(this, R.color.appColor));
            tvPrice.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
            String saixuanUrl="&order=" + order;
            goodsList(saixuanUrl);
        });
        tvPrice.setOnClickListener(v -> {
            new XPopup.Builder(this)
                    .asCustom(new PopLifeCirclePrice(this, (sort, text) -> {
                        tvZongHe.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
                        tvFuJin.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
                        tvXiaoLiang.setTextColor(ContextCompat.getColor(this, R.color.color_343434));
                        tvPrice.setTextColor(ContextCompat.getColor(this, R.color.appColor));
                        if (sort==3){
                            this.asc =1;
                        }else {
                            this.asc =2;
                        }
                        order =2;
                        tvPrice.setText(text);
                        String saixuanUrl="&order=" + order;
                        String jiageUrl =saixuanUrl+"&asc=" + asc;

                        goodsList(jiageUrl);
                    })).show();
        });
        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                goodsList("");
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                goodsList("");
            }
        });
    }


    private void goodsList(String val) {
        String url = "http://liudake.cn/api/pub/get" + "?param=" + "getbaopinprolist" + "&pagesize=" + pageSize + "&page=" + page ;

        if (firstEnter){

            postLoading();
            firstEnter = false;
        }

        LogUtils.e("xzl"+url+val);
        Request request = new Request.Builder()
                .url(url+val)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loadingPopupView !=null&&loadingPopupView.isShow()){
                            loadingPopupView.dismiss();
                            loadingPopupView = null;
                        }
                    }
                });
                ToastUtils.showShort("数据获取失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loadingPopupView.isShow()){
                            loadingPopupView.dismiss();
                        }
                    }
                });
                final String responseBody = response.body().string();
                JDGoodsDto baseBean = ParseUtils.parseJsonObject(responseBody, JDGoodsDto.class);

                if (CollectionUtils.isEmpty(baseBean.getData())) {
                    if (page == 1) {
                        Message message = new Message();
                        message.what = 1;
                        mHandler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.what = 1;
                        mHandler.sendMessage(message);
                    }
//                    jdGoodsList.clear();
                } else {
                    if (page == 1) {
                        goodsBeanList.clear();
                        if (baseBean.getCount() < Constants.LIST_SIZE) {
                            Message message = new Message();
                            message.what = 1;
                            mHandler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.what = 2;
                            mHandler.sendMessage(message);
                        }
                    } else {
                        Message message = new Message();
                        message.what = 3;
                        mHandler.sendMessage(message);
                    }
                    goodsBeanList.addAll(baseBean.getData());
                }

            }
        });


    }

    void postLoading(){
        loadingPopupView = (LoadingPopupView) new XPopup.Builder(context)
                .hasShadowBg(false)
                .dismissOnTouchOutside(false)
                .dismissOnBackPressed(true)
                .asLoading()
                .show();
    }

}