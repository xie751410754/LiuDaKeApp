package com.cdxz.liudake.ui.my.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsCallback;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.bean.BankInfoDto;
import com.cdxz.liudake.bean.ShopInfoDto;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.view.DrawableTextView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class WithdrawalActivity extends BaseActivity {

    @BindView(R.id.tvRedmi)
    TextView tvRedmi;

    @BindView(R.id.tvRemark)
    TextView tvRemark;

    @BindView(R.id.tvType)
    DrawableTextView tvType;
    @BindView(R.id.dtv_auto)
    DrawableTextView dtv_auto;

    @BindView(R.id.et_input1)
    EditText et_input1;

    @BindView(R.id.et_input2)
    EditText et_input2;

    @BindView(R.id.et_input3)
    EditText et_input3;

    @BindView(R.id.et_input4)
    EditText et_input4;

    @BindView(R.id.etRedmi)
    EditText etRedmi;


    private String redmi;
    private int type = 2;
    private String withdraw_fee_value;
    private String shopId = "";

    public static void startWithdrawalActivity(Context context, String redmi) {
        Intent intent = new Intent(context, WithdrawalActivity.class);
        intent.putExtra("redmi", redmi);
        context.startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_withdrawal_new;
    }

    @Override
    protected void initViews() {
//        setTitleText("提现");
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void initDatas() {
        redmi = getIntent().getStringExtra("redmi");
        if (!ObjectUtils.isEmpty(getIntent().getStringExtra("shopId"))){
            shopId = getIntent().getStringExtra("shopId");
            tvRemark.setVisibility(View.GONE);
        }
        tvRedmi.setText("人民币"+redmi);

        getBankInfo(shopId);

        //
        HttpsUtil.getInstance(context).withdrawFeeValue(object -> {
            try {
                JSONArray jsonArray = new JSONArray(GsonUtils.toJson(object));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String key = jsonObject.getString("key");
                    String value = jsonObject.getString("value");
                    String content = jsonObject.getString("content");


                    if (!isSelector){

                        if (key.equals("withdraw_fee_value")) {
                            withdraw_fee_value = value;
                            tvRemark.setText(String.format("备注：将收取%.2f%%的手续费", Float.parseFloat(withdraw_fee_value) * 100));
                            findViewById(R.id.tvAll).setOnClickListener(v -> {
                                etRedmi.setText(redmi);
                                etRedmi.setSelection(redmi.length());
                            });
                        }
                    }else {
                        if (key.equals("withdraw_auto_fee_value")) {
                            withdraw_fee_value = value;
                            tvRemark.setText(String.format("备注：将收取%.2f%%的手续费", Float.parseFloat(withdraw_fee_value) * 100));
                            findViewById(R.id.tvAll).setOnClickListener(v -> {
                                etRedmi.setText(redmi);
                                etRedmi.setSelection(redmi.length());
                            });
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void getBankInfo(String shopId) {

        HttpsUtil.getInstance(this).getWithdrawalInfo(shopId, new HttpsCallback() {
            @Override
            public void onResult(Object object) {
                BankInfoDto bankInfoDto = ParseUtils.parseJsonObject(GsonUtils.toJson(object), BankInfoDto.class);

                setInfo(bankInfoDto);
            }



        });

    }

    @SuppressLint("SetTextI18n")
    private void setInfo(BankInfoDto bankInfoDto) {
        if (bankInfoDto==null){
            return;
        }
            et_input1.setText(bankInfoDto.getType_name()==null? "":bankInfoDto.getType_name());
            et_input2.setText(bankInfoDto.getUser_real_name()+"");
            et_input3.setText(bankInfoDto.getBank()+"");
            et_input3.setText(bankInfoDto.getSub_bank()+"");



    }


    private boolean isSelector = false;
    private String is_auto_withdraw = "0";

    @Override
    protected void initListener() {

        dtv_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelector = !isSelector;
                if (isSelector){
                    is_auto_withdraw = "1";
                    dtv_auto.setLeftDrawable(ContextCompat.getDrawable(context, R.mipmap.icon_tixian_selector));
                }else {
                    is_auto_withdraw = "0";
                    dtv_auto.setLeftDrawable(ContextCompat.getDrawable(context, R.mipmap.icon_tixian_normal));
                }

                HttpsUtil.getInstance(context).withdrawFeeValue(object -> {
                    try {
                        JSONArray jsonArray = new JSONArray(GsonUtils.toJson(object));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String key = jsonObject.getString("key");
                            String value = jsonObject.getString("value");
                            String content = jsonObject.getString("content");


                            if (!isSelector){

                                if (key.equals("withdraw_fee_value")) {
                                    withdraw_fee_value = value;
                                    if (TextUtils.isEmpty(etRedmi.getText().toString().trim())){

                                        tvRemark.setText(String.format("备注：将收取%.2f%%的手续费", Float.parseFloat(withdraw_fee_value) * 100));
                                    }else {
                                        shiji(etRedmi.getText().toString().trim());
                                    }
                                    findViewById(R.id.tvAll).setOnClickListener(v -> {
                                        etRedmi.setText(redmi);
                                        etRedmi.setSelection(redmi.length());
                                    });
                                }
                            }else {
                                if (key.equals("withdraw_auto_fee_value")) {
                                    withdraw_fee_value = value;
//                                    tvRemark.setText(String.format("备注：将收取%.2f%%的手续费", Float.parseFloat(withdraw_fee_value) * 100));
                                    if (TextUtils.isEmpty(etRedmi.getText().toString().trim())){

                                        tvRemark.setText(String.format("备注：将收取%.2f%%的手续费", Float.parseFloat(withdraw_fee_value) * 100));
                                    }else {
                                        shiji(etRedmi.getText().toString().trim());
                                    }
                                    findViewById(R.id.tvAll).setOnClickListener(v -> {
                                        etRedmi.setText(redmi);
                                        etRedmi.setSelection(redmi.length());
                                    });
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        });


        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        etRedmi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("DefaultLocale")
            @Override
            public void afterTextChanged(Editable s) {
                String input = etRedmi.getText().toString();
                if (StringUtils.isEmpty(input)) {
                    tvRemark.setText(String.format("备注：将收取%.2f%%的手续费", Float.parseFloat(withdraw_fee_value) * 100));
                    return;
                }
                shiji(input);
            }
        });
        findViewById(R.id.tvTixianDetail).setOnClickListener(v -> {
            if (ActivityUtils.isActivityExistsInStack(RedmiBillActivity.class)) {
                ActivityUtils.finishActivity(RedmiBillActivity.class);
            }
            RedmiBillActivity.startRedmiBillActivity(this,shopId);
        });
        findViewById(R.id.tvSubmitTixian).setOnClickListener(v -> {
            redmiTixian();
        });
//        tvType.setOnClickListener(v -> {
//            hideKeyboard();
//            new XPopup.Builder(this)
//                    .asBottomList("", new String[]{"微信", "支付宝", "银行卡"}, (position, text) -> {
//                        tvType.setText(text);
//                        switch (position) {
//                            case 0:
//                                et_input1.setHint("请输入微信号");
//                                et_input2.setVisibility(View.GONE);
//                                type = 2;
//                                break;
//                            case 1:
//                                et_input1.setHint("请输入支付宝账号");
//                                et_input2.setVisibility(View.GONE);
//                                type = 1;
//                                break;
//                            case 2:
//                                et_input1.setHint("请输入银行卡卡号");
//
//                                break;
//                        }
//                    }).show();
//        });
        tvType.setText("银行卡");
        et_input1.setHint("请输入银行卡卡号");
        et_input2.setVisibility(View.VISIBLE);
        type = 3;
    }

    private void redmiTixian() {
        String account_from = etRedmi.getText().toString();
        String type_name = et_input1.getText().toString();
        String user_real_name = et_input2.getText().toString();

        String back = et_input3.getText().toString();
        String sub_back = et_input4.getText().toString();

        if (StringUtils.isEmpty(account_from)) {
            ToastUtils.showShort("请输入提现金额");
            return;
        }
        String account_number = String.valueOf(Float.parseFloat(account_from) -
                (Float.parseFloat(account_from) * Float.parseFloat(withdraw_fee_value)));
        switch (type) {
            case 1:
                if (StringUtils.isEmpty(type_name)) {
                    ToastUtils.showShort("请输入支付宝账号");
                    return;
                }
                break;
            case 2:
                if (StringUtils.isEmpty(type_name)) {
                    ToastUtils.showShort("请输入微信号");
                    return;
                }
                break;
            case 3:
                if (StringUtils.isEmpty(type_name)) {
                    ToastUtils.showShort("请输入银行卡卡号");
                    return;
                }
                if (StringUtils.isEmpty(user_real_name)) {
                    ToastUtils.showShort("请输入真实姓名");
                    return;
                }
                if (StringUtils.isEmpty(back)) {
                    ToastUtils.showShort("请输入银行名称");
                    return;
                }

                if (StringUtils.isEmpty(sub_back)) {
                    ToastUtils.showShort("请输入开户行名称");
                    return;
                }


                break;
        }
        HttpsUtil.getInstance(this).redmiTixian(account_from, account_number, withdraw_fee_value, type,
                type_name, user_real_name,shopId,back,sub_back,is_auto_withdraw, object -> {
            finish();
        });
    }

    @SuppressLint("DefaultLocale")
    private void shiji(String input) {
        tvRemark.setText(String.format(
                "备注：将收取%.2f%%的手续费，实际到账%.2f元",
                Float.parseFloat(withdraw_fee_value) * 100,
                Float.parseFloat(input) - Float.parseFloat(withdraw_fee_value) * Float.parseFloat(input)
                )
        );
    }
}