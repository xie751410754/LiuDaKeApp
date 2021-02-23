package com.cdxz.liudake.adapter.my.service;

import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.FAQBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FAQAdapter extends BaseQuickAdapter<FAQBean, BaseViewHolder> {
    public FAQAdapter(@Nullable List<FAQBean> data) {
        super(R.layout.item_faq, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FAQBean faqBean) {
        baseViewHolder.setText(R.id.tvItemFAQ, faqBean.getTitle());
    }
}
