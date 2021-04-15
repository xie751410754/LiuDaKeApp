package com.cdxz.liudake.adapter.my.service;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BusUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.bean.AddressListBean;
import com.cdxz.liudake.ui.my.service.AddAddressActivity;
import com.cdxz.liudake.view.DrawableTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;

import java.util.List;

public class AddressListAdapter extends BaseQuickAdapter<AddressListBean, BaseViewHolder> {
    public AddressListAdapter(List<AddressListBean> data) {
        super(R.layout.item_address_new, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AddressListBean listBean) {
        baseViewHolder.setText(R.id.tvAddressInfo, listBean.getProvince() +
                listBean.getCity() + listBean.getDistrict() + listBean.getDetail())
                .setText(R.id.phone, listBean.getPhone())
                .setText(R.id.tvUserInfo, "收货人：" + listBean.getName());
        DrawableTextView tvDefault = baseViewHolder.getView(R.id.tvDefault);
        if (listBean.getSelected().equals("1")) {
            tvDefault.setTextColor(ContextCompat.getColor(getContext(), R.color.appColor));
            tvDefault.setLeftDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_adress_select));
        } else {
            tvDefault.setTextColor(ContextCompat.getColor(getContext(), R.color.color_222222));
            tvDefault.setLeftDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_adress_nomal));
        }
        baseViewHolder.getView(R.id.tvEdit).setOnClickListener(v -> {
            AddAddressActivity.startAddAddressActivity(getContext(), listBean);
        });
        baseViewHolder.getView(R.id.tvDeleteAddress).setOnClickListener(v -> {
            new XPopup.Builder(getContext())
                    .asConfirm("删除", "是否删除该收货地址？", () -> deleteAddress(listBean)).show();
        });


    }

    private void deleteAddress(AddressListBean listBean) {
        HttpsUtil.getInstance(getContext()).deleteAddress(listBean.getId(), object -> {
            BusUtils.post(BusTag.UPDATE_ADDRESS_LIST);
        });
    }

}
