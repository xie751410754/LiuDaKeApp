package com.cdxz.liudake.adapter.my.service;

import android.view.View;
import android.widget.TextView;

import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.AddressListBean;
import com.cdxz.liudake.ui.my.service.AddAddressActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class AddressListAdapter extends BaseQuickAdapter<AddressListBean, BaseViewHolder> {
    public AddressListAdapter(List<AddressListBean> data) {
        super(R.layout.item_address, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AddressListBean listBean) {
        baseViewHolder.setText(R.id.tvAddressInfo, listBean.getProvince() +
                listBean.getCity() + listBean.getDistrict() + listBean.getDetail())
                .setText(R.id.tvUserInfo, listBean.getName() + " " + listBean.getPhone());
        TextView tvDefault = baseViewHolder.getView(R.id.tvDefault);
        if (listBean.getSelected().equals("1")) {
            tvDefault.setVisibility(View.VISIBLE);
        } else {
            tvDefault.setVisibility(View.INVISIBLE);
        }
        baseViewHolder.getView(R.id.tvEdit).setOnClickListener(v -> {
            AddAddressActivity.startAddAddressActivity(getContext(),listBean);
        });
    }
}
