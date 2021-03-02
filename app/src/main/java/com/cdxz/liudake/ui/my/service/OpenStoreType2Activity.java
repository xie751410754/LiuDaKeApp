package com.cdxz.liudake.ui.my.service;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.api.UploadUtil;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.PictureUtil;

import butterknife.BindView;

public class OpenStoreType2Activity extends BaseActivity {

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etRealName)
    EditText etRealName;

    @BindView(R.id.ivFront)
    ImageView ivFront;

    @BindView(R.id.ivReverse)
    ImageView ivReverse;

    @BindView(R.id.etBank)
    EditText etBank;

    @BindView(R.id.ivMenTou)
    ImageView ivMenTou;

    @BindView(R.id.ivPicture1)
    ImageView ivPicture1;

    @BindView(R.id.ivPicture2)
    ImageView ivPicture2;

    @BindView(R.id.etZhekou)
    EditText etZhekou;

    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.etCode)
    EditText etCode;

    @BindView(R.id.tvGetCode)
    TextView tvGetCode;

    private String front_picture;
    private String reverse_picture;
    private String shop_picture;
    private String shop_env_picture1;
    private String shop_env_picture2;
    CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            tvGetCode.setText(String.valueOf((int) l / 1000));
            tvGetCode.setClickable(false);
        }

        @Override
        public void onFinish() {
            tvGetCode.setText("获取验证码");
            tvGetCode.setClickable(true);
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_open_store_type2;
    }

    @Override
    protected void initViews() {
        setTitleText("个体小微商户入驻申请");
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initListener() {
        tvGetCode.setOnClickListener(v -> {
            String phone = etPhone.getText().toString();
            if (StringUtils.isEmpty(phone)) {
                ToastUtils.showShort("请输入手机号");
                return;
            }
//            if (!RegexUtils.isMobileExact(phone)) {
//                ToastUtils.showShort("请输入正确的手机号");
//                return;
//            }
            HttpsUtil.getInstance(this).getSmsCode(phone, 5, object -> {
                downTimer.start();
            });
        });
        ivFront.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    front_picture = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.BASE_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(ivFront);
                    });
                });
            });
        });
        ivReverse.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    reverse_picture = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.BASE_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(ivReverse);
                    });
                });
            });
        });
        ivMenTou.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    shop_picture = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.BASE_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(ivMenTou);
                    });
                });
            });
        });
        ivPicture1.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    shop_env_picture1 = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.BASE_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(ivPicture1);
                    });
                });
            });
        });
        ivPicture2.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    shop_env_picture2 = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.BASE_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(ivPicture2);
                    });
                });
            });
        });

        findViewById(R.id.tvSubmit).setOnClickListener(v -> {
            String name = etName.getText().toString();
            String contactperson = etRealName.getText().toString();
            String contactperson_number = etBank.getText().toString();
            String zhekou = etZhekou.getText().toString();
            String phone = etPhone.getText().toString();
            String code = etCode.getText().toString();
            if (StringUtils.isEmpty(name)) {
                ToastUtils.showShort("请输入商店名称");
                return;
            }
            if (StringUtils.isEmpty(contactperson)) {
                ToastUtils.showShort("请输入与身份证上一致的姓名");
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
            if (StringUtils.isEmpty(contactperson_number)) {
                ToastUtils.showShort("请输入结算卡号（借记卡）");
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
            if (StringUtils.isEmpty(phone)) {
                ToastUtils.showShort("请输入手机号");
                return;
            }
//            if (!RegexUtils.isMobileExact(phone)) {
//                ToastUtils.showShort("请输入正确的手机号");
//                return;
//            }
            if (StringUtils.isEmpty(code)) {
                ToastUtils.showShort("请输入验证码");
                return;
            }
            shopReg(2,
                    name,
                    contactperson,
                    phone,
                    null,
                    null,
                    front_picture + "," + reverse_picture,
                    2,
                    contactperson_number,
                    shop_picture,
                    shop_env_picture1,
                    shop_env_picture2,
                    null,
                    zhekou,
                    code
            );
        });
    }

    /**
     * @param type                 商家类型，1：商城商家，2：周边商家，3：便利店（默认传2）
     * @param name                 商家名称
     * @param contactperson        联系人
     * @param contact              联系电话
     * @param number               营业执照编号
     * @param license              营业执照图片url
     * @param picture              身份证照片，多张逗号拼接
     * @param shop_type            商家类型 1个体户商户 2个体微小商户 3企业商户
     * @param contactperson_number 法人结算卡
     * @param shop_picture         门头合影照
     * @param shop_env_picture1    环境照1
     * @param shop_env_picture2    环境照2
     * @param permit_picture       开户许可证
     * @param zhekou               折扣比例
     * @param code
     */
    private void shopReg(int type, String name, String contactperson, String contact,
                         String number, String license, String picture, int shop_type, String contactperson_number,
                         String shop_picture, String shop_env_picture1, String shop_env_picture2, String permit_picture,
                         String zhekou, String code) {
        HttpsUtil.getInstance(this).regShopNew(type, name, contactperson, contact, number, license, picture, shop_type, contactperson_number, shop_picture, shop_env_picture1, shop_env_picture2, permit_picture, zhekou, code, object -> {
            ActivityUtils.finishActivity(OpenStoreTypeActivity.class);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downTimer.cancel();
        downTimer = null;
    }
}