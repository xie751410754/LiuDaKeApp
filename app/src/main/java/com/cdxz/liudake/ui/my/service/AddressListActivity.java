package com.cdxz.liudake.ui.my.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.my.service.AddressListAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.bean.AddressListBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddressListActivity extends BaseActivity {
    @BindView(R.id.recyclerAddress)
    RecyclerView recyclerAddress;

    private AddressListAdapter mAdapter;

    public static void startAddressListActivity(Context context) {
        Intent intent = new Intent(context, AddressListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_address_list_new;
    }

    @Override
    protected void initViews() {
//        setTitleText("收货地址管理");
//        setTitleRightText("添加");
//        setTitleRightTextColor(R.color.color_343434);
        recyclerAddress.setLayoutManager(new LinearLayoutManager(this));
        recyclerAddress.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(12), true));
    }

    @Override
    protected void initDatas() {
        BusUtils.register(this);
        getAddressList();
    }

    @Override
    protected void initListener() {

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.ll_adress_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAddressActivity.startAddAddressActivity(context, null);

            }
        });

    }

    @Override
    protected void onRightListener() {
        super.onRightListener();
        AddAddressActivity.startAddAddressActivity(this, null);
    }

    @BusUtils.Bus(tag = BusTag.UPDATE_ADDRESS_LIST)
    public void onUpdateList() {
        getAddressList();
    }

    private void getAddressList() {
        HttpsUtil.getInstance(this).addressList(object -> {
            List<AddressListBean> addressListBeans = ParseUtils.parseJsonArray(GsonUtils.toJson(object), AddressListBean.class);
            mAdapter = new AddressListAdapter(addressListBeans);
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                BusUtils.post(BusTag.SELECT_ADDRESS, addressListBeans.get(position));
                finish();
            });
            recyclerAddress.setAdapter(mAdapter);
            mAdapter.setEmptyView(R.layout.public_no_data);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
    }
}