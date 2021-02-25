package com.cdxz.liudake.adapter.store_manager;

import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.StoreBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MerchantAdapter extends BaseQuickAdapter<StoreBean, BaseViewHolder> {
    public MerchantAdapter(int layoutResId, @Nullable List<StoreBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, StoreBean item) {


        helper.setText(R.id.tv_name,item.getName())
                .setText(R.id.tv_address,"区域："+item.getAddress())
                .setText(R.id.tv_time,"营业时间："+item.getOpen_end_time())
                .setText(R.id.tv_phone,"联系电话："+item.getContact())
                .setGone(R.id.divider,helper.getAdapterPosition()!=getData().size()-1);


    }
}
