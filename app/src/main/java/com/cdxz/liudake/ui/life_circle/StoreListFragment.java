package com.cdxz.liudake.ui.life_circle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.life_circle.LifeCircleAdapter;
import com.cdxz.liudake.bean.LifeCircleBean;
import com.cdxz.liudake.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreListFragment extends BaseFragment {
    @BindView(R.id.recyclerStore)
    RecyclerView recyclerStore;

    private LifeCircleAdapter mAdapter;

    public StoreListFragment() {
        // Required empty public constructor
    }

    public static StoreListFragment newInstance() {
        StoreListFragment fragment = new StoreListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected int getResource() {
        return R.layout.fragment_store_list;
    }

    @Override
    protected void initView() {
        recyclerStore.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initData() {
        List<LifeCircleBean> objects = new ArrayList<>();

        mAdapter = new LifeCircleAdapter(objects);
        recyclerStore.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }
}