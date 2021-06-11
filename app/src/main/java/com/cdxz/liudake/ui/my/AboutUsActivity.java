package com.cdxz.liudake.ui.my;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.allenliu.versionchecklib.callback.APKDownloadListener;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.api.HttpsCallback;
import com.cdxz.liudake.api.HttpsUtil;
import com.cdxz.liudake.base.BaseBean;
import com.cdxz.liudake.base.Constants;
import com.cdxz.liudake.bean.GetSetBean;
import com.cdxz.liudake.bean.VersionUpdateBean;
import com.cdxz.liudake.ui.WebActivity;
import com.cdxz.liudake.ui.base.BaseActivity;
import com.cdxz.liudake.util.ParseUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tvVersionName)
    TextView tvVersionName;

    public static void startAboutUsActivity(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initViews() {
        setTitleText("关于我们");
    }

    @Override
    protected void initDatas() {
        tvVersionName.setText(AppUtils.getAppVersionName());
    }

    @Override
    protected void initListener() {
        findViewById(R.id.tvAboutApp).setOnClickListener(v -> {
            get_set(0);
        });
        findViewById(R.id.tvUserXY).setOnClickListener(v -> {
            get_set(1);
        });
        findViewById(R.id.tvYs).setOnClickListener(v -> {
            get_set(2);
        });
        tvVersionName.setOnClickListener(v -> {
            HttpParams httpParams = new HttpParams();
            httpParams.put("version", AppUtils.getAppVersionName());
            httpParams.put("type", "android");
            AllenVersionChecker
                    .getInstance()
                    .requestVersion()
                    .setRequestMethod(HttpRequestMethod.POST)
                    .setRequestParams(httpParams)
                    .setRequestUrl(Constants.BASE_HTTPS_URL + "version/api/update")
                    .request(new RequestVersionListener() {
                        @Nullable
                        @Override
                        public UIData onRequestVersionSuccess(DownloadBuilder downloadBuilder, String result) {
                            BaseBean baseBean = ParseUtils.parseJsonObject(result, BaseBean.class);
                            if (baseBean.getState().getCode() == 0) {
                                VersionUpdateBean versionUpdateBean = ParseUtils.parseJsonObject(GsonUtils.toJson(baseBean.getData()), VersionUpdateBean.class);
                                if (versionUpdateBean.getHasNewVersion() == 1) {
                                    UIData uiData = UIData
                                            .create()
                                            .setDownloadUrl(versionUpdateBean.getUrl())
                                            .setTitle("发现新版本")
                                            .setContent(versionUpdateBean.getDescription());
                                    downloadBuilder.setOnCancelListener(() -> {
                                        if (versionUpdateBean.getUpdateType() == 2) {//2强制升级
                                            AppUtils.exitApp();
                                        }
                                    });
                                    return uiData;
                                } else {
                                    return null;
                                }
                            } else {
                                ToastUtils.showShort(baseBean.getState().getMsg());
                                return null;
                            }
                        }

                        @Override
                        public void onRequestVersionFailure(String message) {

                        }
                    }).executeMission(this);
        });
    }

    private void get_set(int i) {
        HttpsUtil.getInstance(this).get_set(object -> {
            if ("-1".equals(object)) {
                switch (i) {
                    case 0:
                        ToastUtils.showShort("关于溜达客获取失败");
                        break;
                    case 1:
                        ToastUtils.showShort("用户协议获取失败");
                        break;
                    case 2:
                        ToastUtils.showShort("隐私协议获取失败");
                        break;
                }
            } else {
                List<GetSetBean> getSetBeanList = ParseUtils.parseJsonArray(GsonUtils.toJson(object), GetSetBean.class);
                switch (i) {
                    case 0:
                        for (GetSetBean setBean : getSetBeanList) {
                            if ("about".equals(setBean.getKey())) {
                                WebActivity.startWebActivity(AboutUsActivity.this, "关于溜达客", setBean.getValue());
                            }
                        }
                        break;
                    case 1:
                        for (GetSetBean setBean : getSetBeanList) {
                            if ("user_xieyi".equals(setBean.getKey())) {
                                WebActivity.startWebActivity(AboutUsActivity.this,"用户协议", setBean.getValue());
                            }
                        }
                        break;
                    case 2:
                        for (GetSetBean setBean : getSetBeanList) {
                            if ("yishi_xieyi".equals(setBean.getKey())) {
                                WebActivity.startWebActivity(AboutUsActivity.this, "隐私协议", setBean.getValue());
                            }
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AllenVersionChecker.getInstance().cancelAllMission();
    }
}