package com.cdxz.liudake.ui.store_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.MessageListAdapter;
import com.cdxz.liudake.adapter.store_manager.StoreMessageListAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.BaseObserver;
import com.cdxz.liudake.bean.MessageListBean;
import com.cdxz.liudake.bean.StoreMessageListBean;
import com.cdxz.liudake.databinding.ActivityStoreMessageBinding;
import com.cdxz.liudake.ui.base.BaseTitleActivity;
import com.cdxz.liudake.ui.shop_mall.MessageDetailActivity;
import com.cdxz.liudake.view.SpacesItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StoreMessageActivity extends BaseTitleActivity<ActivityStoreMessageBinding> {

    String shopId;
    StoreMessageListAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store_message;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        initToolbar(binding.toolbar);

        shopId  = getIntent().getStringExtra("shopId");

        binding.refresh.setEnableLoadMore(false);
        binding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMessage(true);
                refreshLayout.finishRefresh();
            }
        });
        binding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getMessage(false);
                refreshLayout.finishLoadMore();
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(12), true));
        adapter = new StoreMessageListAdapter(new ArrayList<>());
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter1, @NonNull View view, int position) {
                MessageDetailActivity.startMessageDetailActivity(StoreMessageActivity.this,adapter.getItem(position).getId());
            }
        });

        getMessage(true);

    }
    int page = 1;
    private void getMessage(boolean refresh){
        if (refresh){
            page = 1;
        }else {
            page++;
        }
        HttpsUtil.getInstance(this).getStoreMessage(2, shopId,page, new BaseObserver<BaseBean<StoreMessageListBean>>(this,true) {
            @Override
            public void onSuccess(BaseBean<StoreMessageListBean> listBaseBean) {
                if (listBaseBean.getData() == null || listBaseBean.getData().getList().size() == 0)return;
                if (refresh){
                    adapter.setNewData(listBaseBean.getData().getList());

                }else {
                    adapter.addData(listBaseBean.getData().getList());
                }
            }
        });
    }

}
