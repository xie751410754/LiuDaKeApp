package com.cdxz.liudake.adapter.shop_mall;

import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.HotSearchBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class SearchHotAdapter extends BaseQuickAdapter<HotSearchBean, BaseViewHolder> {
    public SearchHotAdapter(List<HotSearchBean> data) {
        super(R.layout.item_hot_search, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HotSearchBean searchBean) {
        baseViewHolder.setText(R.id.tvItemHot, searchBean.getKeyword());
    }
}
