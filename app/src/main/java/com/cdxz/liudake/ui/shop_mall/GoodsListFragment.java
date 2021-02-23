package com.cdxz.liudake.ui.shop_mall;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.GoodsAdapter;
import com.cdxz.liudake.ui.base.BaseFragment;
import com.cdxz.liudake.view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoodsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoodsListFragment extends BaseFragment {
    @BindView(R.id.recyclerGoods)
    RecyclerView recyclerGoods;

    private GoodsAdapter mAdapter;

    public GoodsListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GoodsListFragment newInstance(String param1, String param2) {
        GoodsListFragment fragment = new GoodsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected int getResource() {
        return R.layout.fragment_goods_list;
    }

    @Override
    protected void initView() {
        GridLayoutManager goodsLayout = new GridLayoutManager(getContext(), 2);
        recyclerGoods.setLayoutManager(goodsLayout);
        recyclerGoods.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(15), false));
    }

    @Override
    protected void initData() {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            objects.add("");
        }
        mAdapter = new GoodsAdapter(new ArrayList<>());
        recyclerGoods.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }
}