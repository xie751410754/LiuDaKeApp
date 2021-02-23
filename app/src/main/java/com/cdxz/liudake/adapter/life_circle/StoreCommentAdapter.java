package com.cdxz.liudake.adapter.life_circle;

import com.cdxz.liudake.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class StoreCommentAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    public StoreCommentAdapter(List<Object> data) {
        super(R.layout.item_store_comment, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Object o) {

    }
}
