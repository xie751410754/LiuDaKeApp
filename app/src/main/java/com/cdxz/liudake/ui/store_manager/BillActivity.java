package com.cdxz.liudake.ui.store_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.store_manager.StoreBillAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.BaseObserver;
import com.cdxz.liudake.bean.IndexAllInfoBean;
import com.cdxz.liudake.bean.StoreTodayInviteBean;
import com.cdxz.liudake.bean.StoreTodaySettlementBean;
import com.cdxz.liudake.bean.StoreTodaySettlementCashBean;
import com.cdxz.liudake.databinding.ActivityBillBinding;
import com.cdxz.liudake.ui.base.BaseTitleActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import io.reactivex.Observable;

public class BillActivity extends BaseTitleActivity<ActivityBillBinding> {

    String shopId;
    StoreBillAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_bill;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        initToolbar(binding.toolbar);

        shopId = getIntent().getStringExtra("shopId");

        binding.tvJiesuan.setTextSize(16);
        binding.tvJiesuan.setTextColor(Color.parseColor("#333333"));
        binding.tvTuiguang.setTextSize(14);
        binding.tvTuiguang.setTextColor(Color.parseColor("#999999"));
        binding.refresh.setEnableLoadMore(false);
        binding.refresh.setEnableRefresh(false);

        adapter = new StoreBillAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        getStoreTodaySettlement();

        binding.tvJiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvJiesuan.setTextSize(16);
                binding.tvJiesuan.setTextColor(Color.parseColor("#333333"));
                binding.tvTuiguang.setTextSize(14);
                binding.tvTuiguang.setTextColor(Color.parseColor("#999999"));
                binding.refresh.setEnableLoadMore(false);
                binding.refresh.setEnableRefresh(false);

                adapter.getData().clear();
                adapter.notifyDataSetChanged();
                getStoreTodaySettlement();
            }
        });

        binding.tvTuiguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.tvTuiguang.setTextSize(16);
                binding.tvTuiguang.setTextColor(Color.parseColor("#333333"));
                binding.tvJiesuan.setTextSize(14);
                binding.tvJiesuan.setTextColor(Color.parseColor("#999999"));
                binding.refresh.setEnableLoadMore(true);
                binding.refresh.setEnableRefresh(true);

                adapter.getData().clear();
                adapter.notifyDataSetChanged();
                getStoreTodayInvite(true);
            }
        });
        binding.btnRedMi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnCash.setTextColor(Color.parseColor("#666666"));
                binding.btnRedMi.setTextColor(Color.parseColor("#E62129"));

                binding.refresh.setEnableLoadMore(true);
                binding.refresh.setEnableRefresh(true);

                adapter.getData().clear();
                adapter.notifyDataSetChanged();
                getStoreTodaySettlement();
                adapter.setType(0);

            }
        });
        binding.btnCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnRedMi.setTextColor(Color.parseColor("#666666"));
                binding.btnCash.setTextColor(Color.parseColor("#E62129"));


                binding.refresh.setEnableLoadMore(true);
                binding.refresh.setEnableRefresh(true);

                adapter.getData().clear();
                adapter.notifyDataSetChanged();
                getStoreTodaySettlementCash();
                adapter.setType(1);

            }
        });

        binding.refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getStoreTodayInvite(false);
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getStoreTodayInvite(true);
                refreshLayout.finishRefresh();
            }
        });



    }

    public void getStoreTodaySettlement(){
        HttpsUtil.getInstance(this).storeTodaySettlement(shopId, new BaseObserver<BaseBean<List<StoreTodaySettlementBean>>>(this,true) {
            @Override
            public void onSuccess(BaseBean<List<StoreTodaySettlementBean>> listBaseBean) {
                if (listBaseBean.getData()!=null&&listBaseBean.getData().size()>0){
                    adapter.setList(listBaseBean.getData());
                }

            }
        });
    }
    public void getStoreTodaySettlementCash(){
        HttpsUtil.getInstance(this).storeTodaySettlementCash(shopId, new BaseObserver<BaseBean<List<StoreTodaySettlementCashBean>>>(this,true) {
            @Override
            public void onSuccess(BaseBean<List<StoreTodaySettlementCashBean>> listBaseBean) {
                if (listBaseBean.getData()!=null&&listBaseBean.getData().size()>0){
                    adapter.setList(listBaseBean.getData());
                }

            }
        });
    }

    int page =1;
    public void getStoreTodayInvite(boolean isRefresh){
        if (isRefresh){
            page = 1;
        }else {
            page++;
        }
        HttpsUtil.getInstance(this).storeTodayInvite(shopId, String.valueOf(page), new BaseObserver<BaseBean<List<StoreTodayInviteBean>>>(this,true) {
            @Override
            public void onSuccess(BaseBean<List<StoreTodayInviteBean>> listBaseBean) {

                if (listBaseBean.getData()!=null&&listBaseBean.getData().size()>0){
                    if (isRefresh){
                        adapter.setList(listBaseBean.getData());
                    }else {
                        adapter.addData(listBaseBean.getData());
                    }
                }


            }
        });
    }
}
