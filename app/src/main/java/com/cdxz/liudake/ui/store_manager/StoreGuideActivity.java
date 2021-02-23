package com.cdxz.liudake.ui.store_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.store_manager.StoreGuideAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.BaseObserver;
import com.cdxz.liudake.bean.FAQBean;
import com.cdxz.liudake.bean.StoreGuideList;
import com.cdxz.liudake.databinding.ActivityStoreGuideBinding;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.base.BaseTitleActivity;
import com.cdxz.liudake.ui.my.service.FAQActivity;
import com.cdxz.liudake.ui.my.service.FAQDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.List;

public class StoreGuideActivity extends BaseTitleActivity<ActivityStoreGuideBinding> {
    StoreGuideAdapter adapter;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store_guide;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        initToolbar(binding.toolbar);

        adapter = new StoreGuideAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        binding.refresh.setEnableLoadMore(false);
        binding.refresh.setEnableRefresh(false);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter1, @NonNull View view, int position) {
                FAQBean bean = new FAQBean();
                bean.setTitle(adapter.getItem(position).getContent());
                bean.setContent(adapter.getItem(position).getValue());
                FAQDetailActivity.startFAQDetailActivity(StoreGuideActivity.this, bean);
            }
        });
        getData();
    }

    private void getData(){
        HttpsUtil.getInstance(this).getStoreGuide(new BaseObserver<BaseBean<List<StoreGuideList>>>(this,true) {
            @Override
            public void onSuccess(BaseBean<List<StoreGuideList>> baseBean) {
                if (baseBean.getData()==null&&baseBean.getData().size()==0)return;
                adapter.setNewData(baseBean.getData());
            }
        });
    }
}
