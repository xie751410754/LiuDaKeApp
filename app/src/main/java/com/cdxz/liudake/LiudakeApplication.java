package com.cdxz.liudake;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.cdxz.liudake.map.MapHelper;
import com.cdxz.liudake.service.LocationService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
//import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.File;

public class LiudakeApplication extends Application {

    private static Context context;

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new ClassicsHeader(context));
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(context));

    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        MapHelper.initContext(getApplicationContext());
        MapHelper.setMapType(MapHelper.MapType.BAIDU);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
//        ZXingLibrary.initDisplayOpinion(this);

        Utils.init(this);
        File file = getExternalFilesDir(File.separator + AppUtils.getAppPackageName() + File.separator);
        if (FileUtils.createOrExistsDir(file)) {
            CrashUtils.init(file, (crashInfo, e) -> {
                LogUtils.e(crashInfo + "   " + e.getMessage());
            });
        }
    }

    public static Context getContext() {
        return context;
    }
}
