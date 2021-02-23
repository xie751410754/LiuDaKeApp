package com.cdxz.liudake.adapter.my.service;

import com.cdxz.liudake.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CollectStoreAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    public CollectStoreAdapter(@Nullable List<Object> data) {
        super(R.layout.item_collect_store, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Object o) {

    }
}
