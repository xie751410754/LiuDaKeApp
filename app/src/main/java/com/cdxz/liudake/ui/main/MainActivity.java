package com.cdxz.liudake.ui.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.fastjson.JSON;
import com.allenliu.versionchecklib.callback.APKDownloadListener;
import com.allenliu.versionchecklib.callback.OnCancelListener;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsCallback;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.BusTag;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.VersionUpdateBean;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.ui.main.fragment.LifeCircleFragment;
import com.cdxz.liudake.ui.main.fragment.MyFragment;
import com.cdxz.liudake.ui.main.fragment.ShopCarFragment;
import com.cdxz.liudake.ui.main.fragment.ShopMallFragment;
import com.cdxz.liudake.util.ParseUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {

    @BindView(R.id.nav_view)
    BottomNavigationView nav_view;

    private FragmentTransaction ft;
    private int lastIndex;
    private List<Fragment> mFragments;

    private BadgeDrawable badge;

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        nav_view.setItemIconTintList(null);
        badge = nav_view.getOrCreateBadge(R.id.menuShopCar);
        badge.setVisible(false);
        badge.setBackgroundColor(ContextCompat.getColor(this, R.color.appColor));
        badge.setBadgeTextColor(ContextCompat.getColor(this, R.color.white));
        mFragments = new ArrayList<>();
        mFragments.add(new ShopMallFragment());
        mFragments.add(new LifeCircleFragment());
        mFragments.add(new ShopCarFragment());
        mFragments.add(new MyFragment());
        setFragmentPosition(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateVersion();
    }

    @Override
    protected void initDatas() {

        LogUtils.e("xzl"+((Math.sqrt(360*360+640*640))/72));
        BusUtils.register(this);
    }

    @BusUtils.Bus(tag = BusTag.GOODS_DETAIL_TO_CAR)
    public void onGoodsDetailToCar() {
        setFragmentPosition(2);
    }

    @BusUtils.Bus(tag = BusTag.CAR_NUM)
    public void onCarNum(int num) {
        if (num > 0) {
            badge.setVisible(true);
            badge.setNumber(num);
        } else {
            badge.setVisible(false);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void initListener() {
        // 添加监听
        nav_view.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.menuShopMall:
                            setFragmentPosition(0);
                            break;
                        case R.id.menuLifeCircle:
                            setFragmentPosition(1);
                            break;
                        case R.id.menuShopCar:
                            setFragmentPosition(2);
                            break;
                        case R.id.menuMy:
                            setFragmentPosition(3);
                            break;
                        default:
                            break;
                    }
                    // 这里注意返回true,否则点击失效
                    return true;
                });
    }

    private void setFragmentPosition(int position) {
        nav_view.getMenu().getItem(position).setChecked(true);
        ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = mFragments.get(position);
        Fragment lastFragment = mFragments.get(lastIndex);
        LogUtils.e("currentFragment = " + currentFragment.getClass().getSimpleName());
        LogUtils.e("lastFragment = " + lastFragment.getClass().getSimpleName());
        lastIndex = position;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
//            ft.remove(currentFragment).commit();
            ft.add(R.id.nav_fragment, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateVersion() {

        HttpsUtil.getInstance(context).checkUpdate(AppUtils.getAppVersionName(), new HttpsCallback() {
            @Override
            public void onResult(Object result) {
                String json = GsonUtils.toJson(result);
                LogUtils.e("版本升级："+json);
                BaseBean baseBean = ParseUtils.parseJsonObject(json, BaseBean.class);



                if(baseBean.getState().getCode()==0){
                    VersionUpdateBean versionUpdateBean = ParseUtils.parseJsonObject(GsonUtils.toJson(baseBean.getData()), VersionUpdateBean.class);
                    if (versionUpdateBean.getHasNewVersion() == 1){

                        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("发现新版本")
                                .setPositiveButton("升级新版本", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Intent intent= new Intent();
                                        intent.setAction("android.intent.action.VIEW");
                                        Uri content_url = Uri.parse(versionUpdateBean.getUrl());
                                        intent.setData(content_url);
                                        startActivity(intent);
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.dismiss();
                                        if (versionUpdateBean.getUpdateType() == 2){
                                            //2强制升级
                                            AppUtils.exitApp();
                                        }
                                    }
                                })
                                .create();

                        alertDialog.show();

                    }
                }

            }
        });


//        HttpParams httpParams = new HttpParams();
//        httpParams.put("version", AppUtils.getAppVersionName());
//        httpParams.put("type", "android");
//        AllenVersionChecker
//                .getInstance()
//                .requestVersion()
//                .setRequestMethod(HttpRequestMethod.POST)
//                .setRequestParams(httpParams)
//                .setRequestUrl(Constants.BASE_HTTPS_URL + "version/api/update")
//                .request(new RequestVersionListener() {
//                    @Nullable
//                    @Override
//                    public UIData onRequestVersionSuccess(DownloadBuilder downloadBuilder, String result) {
//                        LogUtils.e("版本更新 = " + result);
//                        BaseBean baseBean = ParseUtils.parseJsonObject(result, BaseBean.class);
//                        if (baseBean.getState().getCode() == 0) {
//                            VersionUpdateBean versionUpdateBean = ParseUtils.parseJsonObject(GsonUtils.toJson(baseBean.getData()), VersionUpdateBean.class);
//                            if (versionUpdateBean.getHasNewVersion() == 1) {
//                                UIData uiData = UIData
//                                        .create()
//                                        .setDownloadUrl(versionUpdateBean.getUrl())
//                                        .setTitle("发现新版本")
//                                        .setContent(versionUpdateBean.getDescription());
//                                downloadBuilder.setOnCancelListener(() -> {
//                                    if (versionUpdateBean.getUpdateType() == 2) {
//                                        //2强制升级
//                                        AppUtils.exitApp();
//                                    }
//                                });
//                                return uiData;
//                            } else {
//                                return null;
//                            }
//                        } else {
//                            ToastUtils.showShort(baseBean.getState().getMsg());
//                            return null;
//                        }
//                    }
//
//                    @Override
//                    public void onRequestVersionFailure(String message) {
//
//                    }
//                }).executeMission(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
        AllenVersionChecker.getInstance().cancelAllMission();

    }
}