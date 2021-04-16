package com.cdxz.liudake.ui.store_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.BaseObserver;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.StoreQRBean;
import com.cdxz.liudake.databinding.ActivityStoreQrBinding;
import com.cdxz.liudake.databinding.ActivityStoreQrNewBinding;
import com.cdxz.liudake.ui.base.BaseTitleActivity;
import com.squareup.picasso.Picasso;

import java.io.File;

public class StoreQRActivity extends BaseTitleActivity<ActivityStoreQrNewBinding> {

    String url = "http://www.baidu.com";
    Bitmap qrBitmap;
    String shopId;
    File file;
    private InputFilter lengthFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {


            return null;
        }
    };


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store_qr_new;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

//        initToolbar(binding.toolbar);

        shopId = getIntent().getStringExtra("shopId");

        getData("0");

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.mbShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareSingleImage();
            }
        });
        binding.mbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file = new File(getApplication().getExternalCacheDir().getPath() + "/" + System.currentTimeMillis() + ".png");

                binding.ivQr.setDrawingCacheEnabled(true);
                qrBitmap = Bitmap.createBitmap(binding.ivQr.getDrawingCache());
                binding.ivQr.setDrawingCacheEnabled(false);

                ImageUtils.save(qrBitmap, file, Bitmap.CompressFormat.PNG);
                ToastUtils.showShort("保存成功" + file.getPath());
            }
        });
        binding.etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    binding.tvSetPrice.setTextColor(Color.parseColor("#DADADA"));

                } else {
                    binding.tvSetPrice.setTextColor(getResources().getColor(R.color.appColor));
                }
            }
        });
        binding.tvSetPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(binding.etPrice.getText().toString())) {
                    getData(binding.etPrice.getText().toString());
                }
            }
        });
    }


    public void getData(String money) {

        HttpsUtil.getInstance(this).getStoreQR(shopId, money, new BaseObserver<BaseBean<StoreQRBean>>(this, true) {
            @Override
            public void onSuccess(BaseBean<StoreQRBean> storeQRBeanBaseBean) {


                String baseurl = storeQRBeanBaseBean.getData().getSMQrcode();

                LogUtils.e("StoreQRActivity", "onNext = " + baseurl);
                Picasso.with(StoreQRActivity.this)
                        .load(baseurl)
                        .into(binding.ivQr);

//                qrBitmap =((BitmapDrawable)imageView.getDrawable()).getBitmap();

//                qrBitmap = CodeUtils.createImage(Constants.PICTURE_HTTPS_URL +storeQRBeanBaseBean.getData().getSMQrcode(), 400, 400, null);
//                binding.ivQr.setImageBitmap(qrBitmap);

                if (!TextUtils.isEmpty(money)) {
                    ToastUtils.showShort("设置成功");
                }
            }
        });
    }

    //分享一张图片  
    public void shareSingleImage() {
        if (file == null) {
            file = new File(getApplication().getExternalCacheDir().getPath() + "/" + System.currentTimeMillis() + ".png");

            binding.ivQr.setDrawingCacheEnabled(true);
            qrBitmap = Bitmap.createBitmap(binding.ivQr.getDrawingCache());
            binding.ivQr.setDrawingCacheEnabled(false);

            ImageUtils.save(qrBitmap, file, Bitmap.CompressFormat.PNG);
        }


        Uri imageUri = null; //imagePath--本地的文件路径
        try {
            imageUri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享"));
    }
}
