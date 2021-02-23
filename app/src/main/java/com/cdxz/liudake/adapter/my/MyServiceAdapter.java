package com.cdxz.liudake.adapter.my;

import androidx.core.content.ContextCompat;

import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.ServiceBean;
import com.cdxz.liudake.view.DrawableTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class MyServiceAdapter extends BaseQuickAdapter<ServiceBean, BaseViewHolder> {
    public MyServiceAdapter(List<ServiceBean> data) {
        super(R.layout.item_my_service, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ServiceBean serviceBean) {
        DrawableTextView tvService = baseViewHolder.getView(R.id.tvService);
        tvService.setText(serviceBean.getName());
        tvService.setTopDrawable(ContextCompat.getDrawable(getContext(), serviceBean.getResId()));
    }
}
