package com.cdxz.liudake.ui.store_manager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.adapter.shop_mall.HomeBannerAdapter;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.BaseObserver;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.BannerBean;
import com.cdxz.liudake.bean.IndexAllInfoBean;
import com.cdxz.liudake.bean.LoginBean;
import com.cdxz.liudake.bean.ShopBalance;
import com.cdxz.liudake.bean.StoreOpenStatus;
import com.cdxz.liudake.bean.StoreUnderMsgResult;
import com.cdxz.liudake.databinding.ActivityStoreHomeBinding;
import com.cdxz.liudake.databinding.ActivityStoreHomeNewBinding;
import com.cdxz.liudake.ui.ScanQRCodeActivity;
import com.cdxz.liudake.ui.WebActivity;
import com.cdxz.liudake.ui.base.BaseTitleActivity;
import com.cdxz.liudake.ui.main.MainActivity;
import com.cdxz.liudake.ui.my.service.FAQActivity;
import com.cdxz.liudake.ui.my.service.RedmiBillActivity;
import com.cdxz.liudake.ui.my.service.ToPromoteActivity;
import com.cdxz.liudake.ui.my.service.WithdrawalActivity;
import com.cdxz.liudake.util.ParseUtils;
import com.cdxz.liudake.util.UserInfoUtil;
import com.lxj.xpopup.XPopup;
//import com.uuzuche.lib_zxing.activity.CaptureActivity;
//import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.youth.banner.indicator.CircleIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class StoreHomeActivity extends BaseTitleActivity<ActivityStoreHomeNewBinding> implements View.OnClickListener {
    Disposable disposable;

    private LoginBean.ShopBean shopList;
    ShopBalance shopBalance;

    public static void startStoreHomeActivity(Context context, LoginBean.ShopBean shopList) {
        Intent intent = new Intent(context, StoreHomeActivity.class);
        intent.putExtra("shopList", shopList);
        context.startActivity(intent);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store_home_new;
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

//        initToolbar(binding.toolbar);
        shopList = (LoginBean.ShopBean) getIntent().getSerializableExtra("shopList");

        Log.d(this.getClass().getCanonicalName(), "initViewObservable: " + shopList.getId());

//        binding.tvQr.setOnClickListener(this);
        binding.tvShoukuan.setOnClickListener(this);
        binding.tvTixian.setOnClickListener(this);
        binding.tvMessage.setOnClickListener(this);
//        binding.llAllGet1.setOnClickListener(this);
//        binding.llAllGet2.setOnClickListener(this);
        binding.tv5.setOnClickListener(this);
        binding.tv6.setOnClickListener(this);

        binding.tv8.setOnClickListener(this);
        binding.tv9.setOnClickListener(this);
//        binding.tv10.setOnClickListener(this);
        binding.tv11.setOnClickListener(this);
        binding.tv12.setOnClickListener(this);

        binding.imgBack.setOnClickListener(this);


        getIndexData();
        getBannerList();


//        disposable = Observable.interval(0, 10, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        getUndermsg();
//                    }
//                });

        changeOpenStatus(2, openStatusResult);

        binding.swOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeOpenStatus(binding.swOff.isChecked() ? 1 : 0, openStatusResult);
            }
        });

//        binding.swOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                changeOpenStatus(b ? 1 : 0, new BaseObserver<BaseBean<StoreOpenStatus>>(StoreHomeActivity.this, true) {
//                    @Override
//                    public void onSuccess(BaseBean<StoreOpenStatus> storeOpenStatusBaseBean) {
//
//                    }
//                });
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
//            case R.id.tv_qr://扫一扫
//                ScanQRCodeActivity.startScanQRCodeActivity(this, ScanQRCodeActivity.TYPE_STORE_HOME);
////                Intent intent = new Intent(StoreHomeActivity.this, CaptureActivity.class);
////                startActivityForResult(intent, 0);
//                break;
            case R.id.tv_shoukuan://收款码
                Bundle bundle1 = new Bundle();
                bundle1.putString("shopId", shopList.getId());
                startActivity(StoreQRActivity.class, bundle1);
                break;
            case R.id.tv_tixian://提现
//                startActivity(StoreQuXianActivity.class);//不是我注释的

//                Intent intent1 = new Intent(StoreHomeActivity.this, WithdrawalActivity.class);
//                if (shopBalance == null) {
//                    intent1.putExtra("redmi", "0");
//                } else {
//                    intent1.putExtra("redmi", (Serializable) shopBalance.getMy_balance().getShop_balance());
//                }
//                intent1.putExtra("shopId",shopList.getId());
//                startActivity(intent1);

                RedmiBillActivity.startRedmiBillActivity(this,shopList.getId());
                break;
            case R.id.tv_message://新消息
                Bundle bundle2 = new Bundle();
                bundle2.putString("shopId", shopList.getId());
                startActivity(StoreMessageActivity.class, bundle2);
                break;
//            case R.id.ll_all_get1://推广收益
//                break;
//            case R.id.ll_all_get2://结算收益
//                break;
            case R.id.tv_5://店铺管理
                Bundle bundle = new Bundle();
                bundle.putString("shopId", shopList.getId());
                startActivity(StoreManagerActivity.class, bundle);
                break;
            case R.id.tv_6://钱包攻略
//                startActivity(StoreWalletActivity.class);
                //
                HttpsUtil.getInstance(this).withdrawFeeValue(object -> {
                    try {
                        JSONArray jsonArray = new JSONArray(GsonUtils.toJson(object));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String key = jsonObject.getString("key");
                            String value = jsonObject.getString("value");
                            String content = jsonObject.getString("content");
                            if (key.equals("balance_info")) {
                                WebActivity.startWebActivity(this, WebActivity.WALLET_STRATEGY, value);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                break;

            case R.id.tv_8://每日账单
                Bundle b = new Bundle();
                b.putString("shopId",shopList.getId());
                startActivity(BillActivity.class,b);
                break;
            case R.id.tv_9://商户推广
                ToPromoteActivity.startToPromoteActivity(StoreHomeActivity.this);
                break;
            case R.id.tv_10://商户中心
                Bundle bundle10 = new Bundle();
                bundle10.putSerializable("shopList", shopList);
                startActivity(StoreCoreActivity.class, bundle10);
                break;
            case R.id.tv_11://开店指南

                HttpsUtil.getInstance(this).withdrawFeeValue(object -> {
                    try {
                        JSONArray jsonArray = new JSONArray(GsonUtils.toJson(object));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String key = jsonObject.getString("key");
                            String value = jsonObject.getString("value");
                            String content = jsonObject.getString("content");
                            if (key.equals("shop_start_info")) {
                                WebActivity.startWebActivity(this, WebActivity.SHOP_START, value);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
//                startActivity(StoreGuideActivity.class);
                break;
            case R.id.tv_12://联系平台
                HttpsUtil.getInstance(this).getSysPhone(object -> {
                    String json = GsonUtils.toJson(object);
                    try {
                        JSONArray jsonArray = new JSONArray(json);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id_ = jsonObject.getString("id");
                            String phone = jsonObject.getString("phone");
                            //
                            new XPopup.Builder(this)
                                    .asConfirm("拨打电话", "是否拨打" + phone, () -> {
                                        Intent intent_ = new Intent(Intent.ACTION_CALL);
                                        Uri data = Uri.parse("tel:" + phone);
                                        intent_.setData(data);
                                        this.startActivity(intent_);
                                    }).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void getIndexData() {
        HttpsUtil.getInstance(this).storeManagerIndex(shopList.getId(), new BaseObserver<BaseBean<IndexAllInfoBean>>(this, false) {
            @Override
            public void onSuccess(BaseBean<IndexAllInfoBean> indexAllInfoBeanBaseBean) {
                if (!indexAllInfoBeanBaseBean.isSuccess())return;

//                binding.tvTodayHm.setText(indexAllInfoBeanBaseBean.getData().getHongmiToday() + "");//今日红米
////                binding.tvTodayNew.setText(indexAllInfoBeanBaseBean.getData().getInviterToday() + "");//今日推广
//                binding.tvTodayNeworder.setText(indexAllInfoBeanBaseBean.getData().getOrderNumToday() + "");//今日结算订单
//                binding.tvMoney.setText(indexAllInfoBeanBaseBean.getData().getOrderMoneyToday() + "");//余额
                Log.d("StoreHomeActivity", "onSuccess: " + "getIndexData");

            }
        });
    }

    private void getUndermsg() {
        HttpsUtil.getInstance(this).storeIndexUnderMsg(shopList.getId(), new BaseObserver<BaseBean<StoreUnderMsgResult>>(this, false) {
            @Override
            public void onSuccess(BaseBean<StoreUnderMsgResult> storeUnderMsgResultBaseBean) {
                binding.tvUnderMsg.setText(storeUnderMsgResultBaseBean.getData().getTotalMessageToday() + "");
            }
        });
    }

    private void changeOpenStatus(int status, BaseObserver<BaseBean<StoreOpenStatus>> baseBeanBaseObserver) {
        if (status == 2) {
            HttpsUtil.getInstance(this).changeOpenStatus(shopList.getId(), baseBeanBaseObserver);
        } else {
            HttpsUtil.getInstance(this).changeOpenStatus(shopList.getId(), status, baseBeanBaseObserver);
        }
    }

    BaseObserver<BaseBean<StoreOpenStatus>> openStatusResult = new BaseObserver<BaseBean<StoreOpenStatus>>(this, false) {
        @Override
        public void onSuccess(BaseBean<StoreOpenStatus> storeOpenStatusBaseBean) {
            if (storeOpenStatusBaseBean.getData() == null) return;
            binding.swOff.setChecked(storeOpenStatusBaseBean.getData().getOpen_status().equals("1"));
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 0) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(StoreHomeActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
    }

    @Override
    public Resources getResources() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 750);
        } else {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 750);
        }
    }

    private void getBannerList() {
        HttpsUtil.getInstance(this).positionList(2, Constants.LNG, Constants.LAT, object -> {
            List<BannerBean> bannerBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), BannerBean.class);
            binding.bannerMy.setAdapter(new HomeBannerAdapter(bannerBeanList), true)
                    .setIndicator(new CircleIndicator(this));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStoreMoney();
    }

    private void getStoreMoney() {
        HttpsUtil.getInstance(this).getStoreMoney(shopList.getId(), new BaseObserver<BaseBean<ShopBalance>>(this,false) {
            @Override
            public void onSuccess(BaseBean<ShopBalance> shopBalanceBaseBean) {
                Log.e("TAG", "onSuccess: ");
                shopBalance = shopBalanceBaseBean.getData();
                binding.tvMoney.setText("￥"+shopBalance.getMy_balance().getToday_receive());
                binding.tvTodayNeworder.setText(shopBalance.getMy_balance().getToday_ordernums()+"");
                binding.tvStoreMoney.setText(shopBalance.getMy_balance().getShop_balance()+"");
            }
        });
    }



}

