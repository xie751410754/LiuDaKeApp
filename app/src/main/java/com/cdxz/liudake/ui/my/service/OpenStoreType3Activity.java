package com.cdxz.liudake.ui.my.service;

import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.api.UploadUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.RegionBean;
import com.cdxz.liudake.databinding.ActivityOpenStoreType3Binding;
import com.cdxz.liudake.ui.base.Base2Activity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.PictureUtil;

import java.util.ArrayList;
import java.util.List;

public class OpenStoreType3Activity extends Base2Activity<ActivityOpenStoreType3Binding> {
    //
    String province_name;//省
    String city_name;//市
    String region_name;//区
    String license;//营业执照副本
    String permit_picture;//开户许可证照
    String shop_picture;//门头合影照
    String shop_env_picture1;//环境照
    String shop_env_picture2;
    //
    private String front_picture;
    private String reverse_picture;

    CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            binding.tvGetCode.setText(String.valueOf((int) l / 1000));
            binding.tvGetCode.setClickable(false);
        }

        @Override
        public void onFinish() {
            binding.tvGetCode.setText("获取验证码");
            binding.tvGetCode.setClickable(true);
        }
    };


    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_open_store_type3;
    }

    @Override
    protected void initViews() {
        setTitleText("企业商户入驻申请");
    }

    @Override
    protected void initDatas() {
        getRegion();
    }

    @Override
    protected void initListener() {
        binding.tvGetCode.setOnClickListener(v -> {
            String phone = binding.etPhone.getText().toString();
            if (StringUtils.isEmpty(phone)) {
                ToastUtils.showShort("请输入手机号");
                return;
            }
            if (!RegexUtils.isMobileExact(phone)) {
                ToastUtils.showShort("请输入正确的手机号");
                return;
            }
            HttpsUtil.getInstance(this).getSmsCode(phone, 5, object -> {
                downTimer.start();
            });
        });
        binding.tvCity.setOnClickListener(v -> {
            hideKeyboard();
            showPickerView();
        });
        binding.ivLicense.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    license = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.BASE_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(binding.ivLicense);
                    });
                });
            });
        });
        binding.ivFront.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    front_picture = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.BASE_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(binding.ivFront);
                    });
                });
            });
        });
        binding.ivReverse.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    reverse_picture = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.BASE_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(binding.ivReverse);
                    });
                });
            });
        });
        binding.ivKaiHuPic.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    permit_picture = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.BASE_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(binding.ivKaiHuPic);
                    });
                });
            });
        });
        binding.ivMenTou.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    shop_picture = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.BASE_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(binding.ivMenTou);
                    });
                });
            });
        });
        binding.ivPicture1.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    shop_env_picture1 = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.BASE_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(binding.ivPicture1);
                    });
                });
            });
        });
        binding.ivPicture2.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    shop_env_picture2 = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.BASE_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(binding.ivPicture2);
                    });
                });
            });
        });

        binding.tvSubmit.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();//商家名称
            String jianjie = binding.etJianjie.getText().toString();//商家简介
            String xukeNum = binding.etLicenseNumber.getText().toString();//开户许可证编号
            String zhengjian = binding.etIDCardNumber.getText().toString();//法人证件号码
            String email = binding.etEmail.getText().toString();//联系人邮箱
            String contact = binding.etCompanyPhone.getText().toString();//联系电话

            String address = binding.etAddress.getText().toString();//详细地址
            String contactperson_number = binding.etBlank.getText().toString();//结算卡
            String kaihu = binding.etBlankCode.getText().toString();//银行账户开户总行编码
            String number = binding.etNumber.getText().toString();//营业执照号
            String contactperson = binding.etContactPerson.getText().toString();//法人姓名

            String zhekou = binding.etZhekou.getText().toString();
            String code = binding.etCode.getText().toString();

            if (StringUtils.isEmpty(name)) {
                ToastUtils.showShort("请输入商店名称");
                return;
            }
            if (StringUtils.isEmpty(jianjie)) {
                ToastUtils.showShort("请输入商店简称");
                return;
            }
            if (StringUtils.isEmpty(xukeNum)) {
                ToastUtils.showShort("请输入开户许可证编号");
                return;
            }
            if (StringUtils.isEmpty(zhengjian)) {
                ToastUtils.showShort("请输入法人证件号码");
                return;
            }
            if (StringUtils.isEmpty(email)) {
                ToastUtils.showShort("请输入联系人邮箱");
                return;
            }
            if (StringUtils.isEmpty(contact)) {
                ToastUtils.showShort("请输入联系电话");
                return;
            }
            if (StringUtils.isEmpty(binding.tvCity.getText().toString())) {
                ToastUtils.showShort("请选择经营省市区");
                return;
            }
            if (StringUtils.isEmpty(address)) {
                ToastUtils.showShort("请输入详细地址");
                return;
            }
            if (StringUtils.isEmpty(contactperson_number)) {
                ToastUtils.showShort("请输入结算卡号");
                return;
            }
            if (StringUtils.isEmpty(kaihu)) {
                ToastUtils.showShort("请输入银行账户开户总行编码");
                return;
            }
            if (StringUtils.isEmpty(number)) {
                ToastUtils.showShort("请输入营业执照上的社会信用码");
                return;
            }
            if (StringUtils.isEmpty(license)) {
                ToastUtils.showShort("请上传营业执照");
                return;
            }
            if (StringUtils.isEmpty(contactperson)) {
                ToastUtils.showShort("请输入法人姓名");
                return;
            }
            if (StringUtils.isEmpty(front_picture)) {
                ToastUtils.showShort("请上传身份证正面照");
                return;
            }
            if (StringUtils.isEmpty(reverse_picture)) {
                ToastUtils.showShort("请上传身份证反面照");
                return;
            }
            if (StringUtils.isEmpty(permit_picture)) {
                ToastUtils.showShort("请上传开户许可证");
                return;
            }
            if (StringUtils.isEmpty(shop_picture)) {
                ToastUtils.showShort("请上传门头合影照");
                return;
            }
            if (StringUtils.isEmpty(shop_env_picture1) || StringUtils.isEmpty(shop_env_picture2)) {
                ToastUtils.showShort("请上传环境照");
                return;
            }
            if (StringUtils.isEmpty(zhekou)) {
                ToastUtils.showShort("请输入折扣比例");
                return;
            }
            if (StringUtils.isEmpty(binding.etPhone.getText().toString())) {
                ToastUtils.showShort("请输入手机号");
                return;
            }
            if (!RegexUtils.isMobileExact(binding.etPhone.getText().toString())) {
                ToastUtils.showShort("请输入正确的手机号");
                return;
            }
            if (StringUtils.isEmpty(code)) {
                ToastUtils.showShort("请输入验证码");
                return;
            }
            LogUtils.e(" ======= " + province_name);
            LogUtils.e(" ======= " + city_name);
            LogUtils.e(" ======= " + region_name);
            HttpsUtil.getInstance(this).regShopCompany(
                    3, name, jianjie, xukeNum, zhengjian, email, contact, province_name, city_name, region_name, address, contactperson_number, kaihu,
                    number, license, contactperson, front_picture + "," + reverse_picture, permit_picture, shop_picture, shop_env_picture1, shop_env_picture2, zhekou, code, object -> {
                        ActivityUtils.finishActivity(OpenStoreTypeActivity.class);
                        finish();
                    });
        });
    }

    private List<RegionBean> levelList1 = new ArrayList<>();
    private List<RegionBean> levelList2 = new ArrayList<>();
    private List<RegionBean> levelList3 = new ArrayList<>();

    private List<List<RegionBean>> cityList = new ArrayList<>();
    private List<List<List<RegionBean>>> areaList = new ArrayList<>();

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

    private void showPickerView() {
        if (levelList1.size() == 0 || cityList.size() == 0 || areaList.size() == 0) {
            ToastUtils.showShort("省市区获取失败，请返回重试");
            return;
        }
        OptionsPickerView pickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            province_name = levelList1.get(options1).getId();
            city_name = cityList.get(options1).get(options2).getId();
            region_name = areaList.get(options1).get(options2).get(options3).getId();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    .append(levelList1.get(options1).getPickerViewText())
                    .append(" ")
                    .append(cityList.get(options1).get(options2).getPickerViewText())
                    .append(" ")
                    .append(areaList.get(options1).get(options2).get(options3).getPickerViewText());
            binding.tvCity.setText(stringBuilder);
        })
                .setTitleText("选择经营地区")
                .setCancelColor(ContextCompat.getColor(this, R.color.appColor))
                .setSubmitColor(ContextCompat.getColor(this, R.color.appColor))
                .build();
        pickerView.setPicker(levelList1, cityList, areaList);
        pickerView.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downTimer.cancel();
        downTimer = null;
    }
}