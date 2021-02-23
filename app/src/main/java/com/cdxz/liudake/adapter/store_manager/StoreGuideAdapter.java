package com.cdxz.liudake.adapter.store_manager;

import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.StoreGuideList;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class StoreGuideAdapter extends BaseQuickAdapter<StoreGuideList, BaseViewHolder> {
    public StoreGuideAdapter() {
        super(R.layout.item_faq);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, StoreGuideList storeGuideList) {
        baseViewHolder.setText(R.id.tvItemFAQ,storeGuideList.getContent());
    }
}
