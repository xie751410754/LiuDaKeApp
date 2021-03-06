package com.cdxz.liudake.ui.my;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.my.ZhiTuiRankAdapter;
import com.cdxz.liudake.adapter.my.service.ToPromoteAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.BaseObserver;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.GoodsBean;
import com.cdxz.liudake.bean.ToPromoteBean;
import com.cdxz.liudake.bean.ZhiTuiRankDto;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZhiTuiRankActivity extends BaseActivity {


    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;


    @BindView(R.id.rv_rank)
    RecyclerView recyclerToPromote;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_achievement)
    TextView tv_achievement;
    private int type = 0;

    private ZhiTuiRankAdapter mAdapter;
    private List<ZhiTuiRankDto> listBeanList = new ArrayList<>();
    private int page = 1;


    public static void startZhiTuiRankActivity(Context context,int type) {
        Intent intent = new Intent(context, ZhiTuiRankActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_zhitui_rank;
    }


    @Override
    protected void initViews() {
       type =  getIntent().getIntExtra("type",0);
       if (type==1){
           tv_title.setText("直推排行榜");
           tv_achievement.setText("直推数");
       }else if (type ==2){
           tv_title.setText("特惠专区排行榜");
           tv_achievement.setText("直推销量");

       }else {
           tv_title.setText("奖励排行榜");
           tv_achievement.setText("有效直推数");

       }
        recyclerToPromote.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void initDatas() {
        mAdapter = new ZhiTuiRankAdapter(listBeanList);
        recyclerToPromote.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.public_no_data);
        getData();
    }

    @Override
    protected void initListener() {
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
    }

    private void getData() {

        if (type ==1){

            HttpsUtil.getInstance(this).zhituiRank(page, object -> {
                List<ZhiTuiRankDto> beanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), ZhiTuiRankDto.class);

                if (beanList == null) return;
                if (CollectionUtils.isEmpty(beanList)) {
                    if (page == 1) {
                        refresh.finishRefreshWithNoMoreData();
                    } else {
                        refresh.finishLoadMoreWithNoMoreData();
                    }
//                listBeanList.clear();
                } else {
                    if (page == 1) {
                        listBeanList.clear();
                        if (beanList.size() < Constants.LIST_SIZE) {
                            refresh.finishLoadMoreWithNoMoreData();
                        } else {
                            refresh.finishRefresh();
                        }
                    } else {
                        refresh.finishLoadMore();
                    }
                    listBeanList.addAll(beanList);
                }
                mAdapter.notifyDataSetChanged();
            });
        }else if(type ==2){//特惠榜
            HttpsUtil.getInstance(this).activityRank(page, object -> {
                List<ZhiTuiRankDto> beanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), ZhiTuiRankDto.class);

                if (beanList == null) return;
                if (CollectionUtils.isEmpty(beanList)) {
                    if (page == 1) {
                        refresh.finishRefreshWithNoMoreData();
                    } else {
                        refresh.finishLoadMoreWithNoMoreData();
                    }
//                listBeanList.clear();
                } else {
                    if (page == 1) {
                        listBeanList.clear();
                        if (beanList.size() < Constants.LIST_SIZE) {
                            refresh.finishLoadMoreWithNoMoreData();
                        } else {
                            refresh.finishRefresh();
                        }
                    } else {
                        refresh.finishLoadMore();
                    }
                    listBeanList.addAll(beanList);
                }
                mAdapter.notifyDataSetChanged();
            });
        }else {
            HttpsUtil.getInstance(this).rewardRank(page, object -> {
                List<ZhiTuiRankDto> beanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), ZhiTuiRankDto.class);

                if (beanList == null) return;
                if (CollectionUtils.isEmpty(beanList)) {
                    if (page == 1) {
                        refresh.finishRefreshWithNoMoreData();
                    } else {
                        refresh.finishLoadMoreWithNoMoreData();
                    }
//                listBeanList.clear();
                } else {
                    if (page == 1) {
                        listBeanList.clear();
                        if (beanList.size() < Constants.LIST_SIZE) {
                            refresh.finishLoadMoreWithNoMoreData();
                        } else {
                            refresh.finishRefresh();
                        }
                    } else {
                        refresh.finishLoadMore();
                    }
                    listBeanList.addAll(beanList);
                }
                mAdapter.notifyDataSetChanged();
            });
        }

    }

    @Override
    public Resources getResources() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 750);
        } else {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 750);
        }
    }

}