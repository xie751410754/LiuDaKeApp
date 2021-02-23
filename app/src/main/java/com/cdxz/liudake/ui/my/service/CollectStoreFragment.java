package com.cdxz.liudake.ui.my.service;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.my.service.CollectStoreAdapter;
import com.cdxz.liudake.ui.base.BaseFragment;
import com.cdxz.liudake.view.SpacesItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectStoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectStoreFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private CollectStoreAdapter mAdapter;

    public CollectStoreFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CollectStoreFragment newInstance() {
        CollectStoreFragment fragment = new CollectStoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected int getResource() {
        return R.layout.fragment_collect_store;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(12), true));
    }

    @Override
    protected void initData() {
        mAdapter = new CollectStoreAdapter(new ArrayList<>());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.public_no_data);
    }

    @Override
    protected void initListener() {

    }
}