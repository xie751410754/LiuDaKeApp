package com.cdxz.liudake.pop;

import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.CategoryListBean;
import com.cdxz.liudake.bean.CategoryListDto;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class PopBottomSelectAdapter extends BaseQuickAdapter<CategoryListDto, BaseViewHolder> {
    public PopBottomSelectAdapter(List<CategoryListDto> data) {
        super(R.layout.item_store_class_child_child,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CategoryListDto bean) {

        baseViewHolder.setText(R.id.tvName, bean.getName());
    }
}
