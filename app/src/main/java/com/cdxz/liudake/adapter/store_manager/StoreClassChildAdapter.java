package com.cdxz.liudake.adapter.store_manager;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.CategoryListBean;
import com.cdxz.liudake.ui.SearchActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class StoreClassChildAdapter extends BaseQuickAdapter<CategoryListBean.ChildBean, BaseViewHolder> {
    public StoreClassChildAdapter() {
        super(R.layout.item_store_class_child_child);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CategoryListBean.ChildBean bean) {

        baseViewHolder.setText(R.id.tvName, bean.getName());
    }
}
