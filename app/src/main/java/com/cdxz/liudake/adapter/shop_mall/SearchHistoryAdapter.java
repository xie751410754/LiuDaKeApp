package com.cdxz.liudake.adapter.shop_mall;

import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.HotSearchBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class SearchHistoryAdapter extends BaseQuickAdapter<HotSearchBean, BaseViewHolder> {
    public SearchHistoryAdapter(List<HotSearchBean> data) {
        super(R.layout.item_history_search, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HotSearchBean searchBean) {
        baseViewHolder.setText(R.id.tvItemHistory, searchBean.getKeyword());
        baseViewHolder.getView(R.id.ivItemDeleteHistory).setOnClickListener(v -> {
            removeAt(baseViewHolder.getAdapterPosition());
        });
    }

    public void clearList() {
        getData().clear();
        notifyDataSetChanged();
    }
}
