package com.cdxz.liudake.ui.my.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.api.UploadUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.UploadBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.GlideEngine;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.PictureUtil;
import com.cdxz.liudake.util.UserInfoUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OpenStoreActivity extends BaseActivity {

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etNumber)
    EditText etNumber;

    @BindView(R.id.etContactPerson)
    EditText etContactPerson;

    @BindView(R.id.etZhekou)
    EditText etZhekou;

    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.etCode)
    EditText etCode;

    @BindView(R.id.ivLicense)
    ImageView ivLicense;

    @BindView(R.id.ivFront)
    ImageView ivFront;

    @BindView(R.id.ivReverse)
    ImageView ivReverse;

    @BindView(R.id.tvGetCode)
    TextView tvGetCode;

    private String license;
    private String picture1;
    private String picture2;

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

    public static void startOpenStoreActivity(Context context) {
        Intent intent = new Intent(context, OpenStoreActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_open_store;
    }

    @Override
    protected void initViews() {
        setTitleText("入驻申请");
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
            if (!RegexUtils.isMobileExact(phone)) {
                ToastUtils.showShort("请输入正确的手机号");
                return;
            }
            HttpsUtil.getInstance(this).getSmsCode(phone, 6, object -> {
                downTimer.start();
            });
        });
        findViewById(R.id.tvSubmit).setOnClickListener(v -> {
            regShop();
        });

        ivLicense.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    license = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.PICTURE_HTTPS_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(ivLicense);
                    });
                });
            });
        });
        ivFront.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    picture1 = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.PICTURE_HTTPS_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(ivFront);
                    });
                });
            });
        });
        ivReverse.setOnClickListener(v -> {
            PictureUtil.getInstance(this).openGallerySingle(false, false, path -> {
                UploadUtil.getInstance().postFile(path, uploadBean -> {
                    picture2 = uploadBean.getImage().getUrllarge();
                    runOnUiThread(() -> {
                        Glide.with(this)
                                .load(Constants.PICTURE_HTTPS_URL + uploadBean.getImage().getUrllarge())
                                .placeholder(R.mipmap.img_default)
                                .into(ivReverse);
                    });
                });
            });
        });
    }

    private void regShop() {
        String name = etName.getText().toString();
        String contactperson = etContactPerson.getText().toString();
        String contact = etPhone.getText().toString();
        String zhekou = etZhekou.getText().toString();
        String number = etNumber.getText().toString();
        String code = etCode.getText().toString();
        if (StringUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入商店名称");
            return;
        }
        if (StringUtils.isEmpty(number)) {
            ToastUtils.showShort("请输入营业执照号");
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
        if (StringUtils.isEmpty(picture1)) {
            ToastUtils.showShort("请上传法人正面身份证");
            return;
        }
        if (StringUtils.isEmpty(picture2)) {
            ToastUtils.showShort("请上传法人反面身份证");
            return;
        }
        if (StringUtils.isEmpty(zhekou)) {
            ToastUtils.showShort("请上折扣比例");
            return;
        }
        if (StringUtils.isEmpty(contact)) {
            ToastUtils.showShort("请输入手机号");
            return;
        }
        if (!RegexUtils.isMobileExact(contact)) {
            ToastUtils.showShort("请输入正确的手机号");
            return;
        }
        if (StringUtils.isEmpty(code)) {
            ToastUtils.showShort("请输入验证码");
            return;
        }
        HttpsUtil.getInstance(this).regShop(2, name, contactperson, contact, number, license, picture1 + "," + picture2, code, object -> {
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