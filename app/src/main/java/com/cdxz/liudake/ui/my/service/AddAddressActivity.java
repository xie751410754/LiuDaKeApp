package com.cdxz.liudake.ui.my.service;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.AddressListBean;
import com.cdxz.liudake.bean.ProvinceBean;
import com.cdxz.liudake.bean.RegionBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.AssetsUtil;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.DrawableTextView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddAddressActivity extends BaseActivity {

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.tvAddress)
    DrawableTextView tvAddress;

    @BindView(R.id.etAddress)
    EditText etAddress;

    @BindView(R.id.ivdef)
    ImageView ivdef;

    @BindView(R.id.tvDeleteAddress)
    TextView tvDeleteAddress;

    private int selected = 0;
    private AddressListBean addressListBean;
    private String province, city, district;
    //
    private List<RegionBean> levelList1 = new ArrayList<>();
    private List<RegionBean> levelList2 = new ArrayList<>();
    private List<RegionBean> levelList3 = new ArrayList<>();

    private List<List<RegionBean>> cityList = new ArrayList<>();
    private List<List<List<RegionBean>>> areaList = new ArrayList<>();

    public static void startAddAddressActivity(Context context, AddressListBean listBean) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        intent.putExtra("listBean", listBean);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initViews() {
        setTitleRightTextColor(R.color.appColor);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDatas() {
        addressListBean = (AddressListBean) getIntent().getSerializableExtra("listBean");
        if (ObjectUtils.isEmpty(addressListBean)) {
            setTitleText("添加地址");
            setTitleRightText("确认添加");
            tvDeleteAddress.setVisibility(View.GONE);
        } else {
            setTitleText("编辑地址");
            setTitleRightText("确认编辑");
            tvDeleteAddress.setVisibility(View.VISIBLE);
            etName.setText(addressListBean.getName());
            etPhone.setText(addressListBean.getPhone());
            tvAddress.setText(addressListBean.getProvince() + " " + addressListBean.getCity() + " " + addressListBean.getDistrict());
            etAddress.setText(addressListBean.getDetail());
            //
            province = addressListBean.getProvince();
            city = addressListBean.getCity();
            district = addressListBean.getDistrict();
            //
            if (addressListBean.getSelected().equals("1")) {
                ivdef.setTag("y");
                ivdef.setImageResource(R.mipmap.icon_open_y);
                selected = 1;
            } else {
                ivdef.setTag("n");
                ivdef.setImageResource(R.mipmap.icon_open_n);
                selected = 0;
            }
        }
        getRegion();
    }

    @Override
    protected void initListener() {
        tvAddress.setOnClickListener(v -> {
            hideKeyboard();
            showPickerView();
        });
        ivdef.setOnClickListener(v -> {
            if (ivdef.getTag().equals("y")) {
                ivdef.setTag("n");
                ivdef.setImageResource(R.mipmap.icon_open_n);
                selected = 0;
            } else {
                ivdef.setTag("y");
                ivdef.setImageResource(R.mipmap.icon_open_y);
                selected = 1;
            }
        });
        tvDeleteAddress.setOnClickListener(v -> {
            new XPopup.Builder(this)
                    .asConfirm("删除", "是否删除改收货地址？", () -> deleteAddress()).show();
        });
    }

    @Override
    protected void onRightListener() {
        super.onRightListener();
        if (ObjectUtils.isEmpty(addressListBean)) {
            addAddress();
        } else {
            editAddress(addressListBean);
        }
    }

    private void addAddress() {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String detail = etAddress.getText().toString();
        if (StringUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入收货人姓名");
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.showShort("请输入收货人联系电话");
            return;
        }
        if (StringUtils.isEmpty(tvAddress.getText().toString())) {
            ToastUtils.showShort("请选择收货地址");
            return;
        }
        if (StringUtils.isEmpty(detail)) {
            ToastUtils.showShort("请输入收货详细地址");
            return;
        }
        HttpsUtil.getInstance(this).addAddress(name, phone, detail, Constants.LAT,
                Constants.LNG, selected, province, city, district, object -> {
                    updateList();
                });
    }

    private void editAddress(AddressListBean addressListBean) {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String detail = etAddress.getText().toString();
        if (StringUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入收货人姓名");
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.showShort("请输入收货人联系电话");
            return;
        }
        if (StringUtils.isEmpty(tvAddress.getText().toString())) {
            ToastUtils.showShort("请选择收货地址");
            return;
        }
        if (StringUtils.isEmpty(detail)) {
            ToastUtils.showShort("请输入收货详细地址");
            return;
        }
        HttpsUtil.getInstance(this).editAddress(addressListBean.getId(), name, phone, detail, Constants.LAT,
                Constants.LNG, selected, province, city, district, object -> {
                    updateList();
                });
    }

    /**
     * 删除收货地址
     */
    private void deleteAddress() {
        HttpsUtil.getInstance(this).deleteAddress(addressListBean.getId(), object -> {
            updateList();
        });
    }

    /**
     * 获取省市区
     */
    private void getRegion() {
        HttpsUtil.getInstance(this).getRegion(object -> {
            List<RegionBean> regionBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), RegionBean.class);
            for (RegionBean regionBean1 : regionBeanList) {
                if (!"钓鱼岛".equals(regionBean1.getName())) {
                    if (regionBean1.getLevel().equals("1")) {
                        levelList1.add(regionBean1);
                    } else if (regionBean1.getLevel().equals("2")) {
                        levelList2.add(regionBean1);
                    } else if (regionBean1.getLevel().equals("3")) {
                        levelList3.add(regionBean1);
                    }
                }
            }
            for (RegionBean regionBean1 : levelList1) {
                //
                List<RegionBean> cityListChild = new ArrayList<>();
                List<List<RegionBean>> areaListChild = new ArrayList<>();
                for (RegionBean regionBean2 : levelList2) {
                    if (regionBean2.getPid().equals(regionBean1.getId())) {
                        cityListChild.add(regionBean2);
                        //
                        List<RegionBean> areaListChildChild = new ArrayList<>();
                        for (RegionBean regionBean3 : levelList3) {
                            if (regionBean3.getPid().equals(regionBean2.getId())) {
                                areaListChildChild.add(regionBean3);
                            }
                        }
                        areaListChild.add(areaListChildChild);
                    }
                }
                cityList.add(cityListChild);
                areaList.add(areaListChild);
            }
        });
    }

    /**
     * 更新收货地址列表
     */
    private void updateList() {
        BusUtils.post(BusTag.UPDATE_ADDRESS_LIST);
        finish();
    }

    private void showPickerView() {
        if (levelList1.size() == 0 || cityList.size() == 0 || areaList.size() == 0) {
            ToastUtils.showShort("省市区获取失败，请返回重试");
            return;
        }
        OptionsPickerView pickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            province = levelList1.get(options1).getPickerViewText();
            city = cityList.get(options1).get(options2).getPickerViewText();
            district = areaList.get(options1).get(options2).get(options3).getPickerViewText();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    .append(province)
                    .append(" ")
                    .append(city)
                    .append(" ")
                    .append(district);
            tvAddress.setText(stringBuilder);
        })
                .setTitleText("选择地区")
                .setCancelColor(ContextCompat.getColor(this, R.color.appColor))
                .setSubmitColor(ContextCompat.getColor(this, R.color.appColor))
                .build();
        pickerView.setPicker(levelList1, cityList, areaList);
        pickerView.show();
    }
}